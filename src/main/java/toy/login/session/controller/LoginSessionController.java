package toy.login.session.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.session.domain.LoginInfo;
import toy.login.session.repository.SessionRepository;
import toy.login.session.service.RedisService;
import toy.login.users.service.UserService;

@RestController
@Slf4j
@RequestMapping("/session/login")
@RequiredArgsConstructor
public class LoginSessionController {

	private final UserService userService;
	private final SessionRepository sessionRepository;
	private final RedisService redisService;

	@GetMapping
	public ResponseEntity login(@RequestParam("loginId") String loginId,
		@RequestParam("password") String password,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		// 파라미터를 제공하지 않으면 ExceptionHandler 에 의해서 400 에러 발생
		userService.login(loginId, password);

		// jsession 세션 정보 가져오기
		HttpSession session = request.getSession();
		String jSession = session.getId();

		// http 요청 session id 를 value 로 저장하고 key 값을 리턴
		String sessionId = sessionRepository.createSession(jSession);

		// session id 를 찾는 key 값을 cookie 로 전달
		Cookie sessionIdCookie = new Cookie("mySessionId", sessionId);
		sessionIdCookie.setPath("/session");
		response.addCookie(sessionIdCookie);

		return new ResponseEntity("로그인 되었습니다.", HttpStatus.OK);
	}

	@GetMapping("/check")
	public ResponseEntity loginCheckV1(HttpServletRequest request,
		@CookieValue(value = "mySessionId", required = false) String mySessionId) {

		// 셰션 정보 있는지 확인
		// false 를 주는 이유는 세션이 없는 경우 세션을 생성하지 않도록 옵션 제공
		HttpSession session = request.getSession(false);
		if (session == null || mySessionId == null) {
			return new ResponseEntity<>("로그인 먼저 진행해주세요.", HttpStatus.UNAUTHORIZED);
		}

		Object sessionId = sessionRepository.getSession(mySessionId);
		if (sessionId != null && sessionId.equals(session.getId())) {
			return new ResponseEntity<>("기 로그인되어 자동 로그인 되었습니다.", HttpStatus.OK);
		}

		return new ResponseEntity<>("로그인 먼저 진행해주세요.", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("mySessionId")) {
				// sesstion 저장소의 정보 제거
				sessionRepository.removeSession(cookie.getValue());

				// 클라이언트에 쿠키 정보 제거
				cookie.setMaxAge(0);
				cookie.setPath("/session");
				response.addCookie(cookie);
				break;
			}
		}
		return new ResponseEntity("로그아웃 되었습니다.", HttpStatus.OK);
	}

	@GetMapping("/redis")
	public ResponseEntity redisLogin(@RequestParam("loginId") String loginId,
		@RequestParam("password") String password,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		// 파라미터를 제공하지 않으면 ExceptionHandler 에 의해서 400 에러 발생
		userService.login(loginId, password);

		// jsession 세션 정보 가져오기
		HttpSession session = request.getSession();
		String jSession = session.getId();

		// Redis 에 넣을 도메인 객체 생성
		// 만료 시간 -1 무제한
		// LoginInfo loginInfo = new LoginInfo(jSession);
		// 만료 시간 초 단위로 제공 60초 후 만료
		LoginInfo loginInfo = new LoginInfo(UUID.randomUUID().toString(), jSession, 60);

		// RedisRepository 를 통해서 등록
		redisService.save(loginInfo);

		session.setAttribute("mySessionId", loginInfo);

		// session id 를 찾는 key 값을 cookie 로 전달
		Cookie sessionIdCookie = new Cookie("mySessionId", loginInfo.getSessionId());
		sessionIdCookie.setPath("/session");
		response.addCookie(sessionIdCookie);

		return new ResponseEntity("로그인 되었습니다.", HttpStatus.OK);
	}

	@GetMapping("/redis/check")
	public ResponseEntity loginRedisCheckV1(HttpServletRequest request,
		@CookieValue(value = "mySessionId", required = false) String mySessionId,
		@SessionAttribute(value = "mySessionId", required = false) LoginInfo loginInfo) {

		if (loginInfo != null) {
			log.info("{} : {}", loginInfo.getSessionId(), loginInfo.getJSessionId());
		}
		HttpSession session = request.getSession(false);
		LoginInfo loginInfoSession = (LoginInfo)session.getAttribute("mySessionId");

		log.info(" loginInfoSession == loginInfo : {}", loginInfoSession.equals(loginInfo));

		// Cookie 에서 Redis Key 값 확인
		if (mySessionId != null) {
			LoginInfo sessionId = redisService.findSessionId(mySessionId);
			if (sessionId == null) {
				return new ResponseEntity<>("로그인 만료 재 로그인 해주세요.", HttpStatus.NOT_ACCEPTABLE);
			}

			return new ResponseEntity<>("기 로그인되어 자동 로그인 되었습니다.", HttpStatus.OK);
		}

		return new ResponseEntity<>("로그인 먼저 진행해주세요.", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/redis/logout")
	public ResponseEntity redisLogout(@CookieValue(value = "mySessionId", required = false) String mySessionId,
		@SessionAttribute(value = "mySessionId", required = false) LoginInfo loginInfo,
		HttpServletRequest request,
		HttpServletResponse response
	) {

		// 쿠키 제거
		if (mySessionId != null) {
			Cookie cookie = new Cookie("mySessionId", null);
			cookie.setPath("/session");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		// redis 저장소 제거
		if (loginInfo != null) {
			redisService.delete(loginInfo);
		}

		// 세션 정보 제거
		HttpSession requestSession = request.getSession(false);
		if (requestSession != null) {
			requestSession.invalidate();
		}

		return new ResponseEntity("로그아웃 되었습니다.", HttpStatus.OK);
	}

}
