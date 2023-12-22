package toy.login.cookie.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.users.service.UserService;

@RestController
@Slf4j
@RequestMapping("/cookie/login")
@RequiredArgsConstructor
public class LoginCookieController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity login(@RequestParam String loginId,
		@RequestParam String password,
		HttpServletRequest request,
		HttpServletResponse response) {

		// 파라미터를 제공하지 않으면 ExceptionHandler 에 의해서 400 에러 발생
		userService.login(loginId, password);

		// 쿠키에 SessionID 정보로 동일한 사용자 인지 확인
		HttpSession session = request.getSession();

		log.info("sessionId = {}", session.getId());
		String sessionId = session.getId();
		if (sessionId == null) {
			// 세션 id 생성
			sessionId = UUID.randomUUID().toString();

		}

		// sessionId 를 Cookie 로 제공
		// 쿠키에 시간 정보를 주지 않으면 세션 쿠키 ( 브라우저 종료 시 모두 종료 )
		Cookie idCookie = new Cookie("loginCookieSessionId", sessionId);
		// 쿠키를 전달해야 하는 특정 경로 지정
		// /cookie/~~ 로 시작되는 모든 Http 요청에 대해서 해당 Cookie 전달
		idCookie.setPath("/cookie");
		response.addCookie(idCookie);

		return new ResponseEntity<>("로그인 되었습니다.", HttpStatus.OK);
	}

	@GetMapping("/v1")
	public ResponseEntity loginCheckV1(HttpServletRequest request) {

		// httpRequest 에서 넘어온 로그인 관련 쿠키 정보가 있는지 확인
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			// 로그인이 된 경우 쿠키의 값은 SessionId 값이여야 한다.
			HttpSession session = request.getSession();
			String sessionId = session.getId();
			if (cookie.getName().equals("loginCookieSessionId") && cookie.getValue().equals(sessionId)) {
				return new ResponseEntity<>("기 로그인되어 자동 로그인 되었습니다.", HttpStatus.OK);
			}
		}

		// 쿠키 정보가 없거나, 쿠키 값이 아니라면 로그인 먼저 진행하도록 제공
		return new ResponseEntity<>("로그인 먼저 진행해주세요.", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/v2")
	public ResponseEntity loginCheckV2(HttpServletRequest request,
		@CookieValue(name = "loginCookieSessionId", required = false) String loginCookieSessionId
	) {

		/**
		 * @CookieValue(name = "", required = false)
		 * Cookie 없을 수도 있기 때문에 required = false 처리
		 */

		/**
		 * httpRequest 를 받아서 하면 request 에 담겨 있는 모든 쿠키에 대해서 원하는 쿠키 정보가 있는지 확인을 해야 한다.
		 * 이를 편하게 하기 위해서 @CookieValue 어노테이션을 사용하면 일치하는 Cookie 에 대해서 Controller 에 넘어오기 전에 값을 가져올 수 있다.
		 */
		// 쿠키 정보가 없거나, 쿠키 값이 아니라면 로그인 성공 여부에 따라 쿠키 정보 등록
		if (loginCookieSessionId == null) {
			return new ResponseEntity<>("로그인 먼저 진행해주세요.", HttpStatus.UNAUTHORIZED);
		}

		HttpSession session = request.getSession();
		String sessionId = session.getId();
		if (sessionId.equals(loginCookieSessionId)) {
			// 쿠키가 존재하면 자동 로그인 처리
			return new ResponseEntity<>("기 로그인되어 자동 로그인 되었습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("로그인 먼저 진행해주세요.", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/logout")
	public ResponseEntity logout(HttpServletResponse response, HttpServletRequest request) {
		Cookie idCookie = new Cookie("loginCookieSessionId", null);
		// 쿠키의 수명 시간을 0으로 선언 시 쿠키가 삭제 처리 됩니다.
		idCookie.setMaxAge(0);
		idCookie.setPath("/cookie");
		response.addCookie(idCookie);

		return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);

	}

}
