package toy.login.session.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import toy.login.session.repository.SessionRepository;
import toy.login.users.service.UserService;

@RestController
@Slf4j
@RequestMapping("/session/login")
@RequiredArgsConstructor
public class LoginSessionController {

	private final UserService userService;
	private final SessionRepository sessionRepository;

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
		response.addCookie(new Cookie("mySessionId", sessionId));

		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity(HttpStatus.OK);
	}

}
