package toy.login.session.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.users.service.UserService;

@Slf4j
@RestController
@RequestMapping("/springsession")
@RequiredArgsConstructor
public class LoginSpringSessionController {

	private final UserService userService;

	@GetMapping
	public void createSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
	}

	@GetMapping("/login")
	public void getSession(@RequestParam("loginId") String loginId,
		@RequestParam("password") String password,
		HttpServletRequest request,
		@CookieValue("toyLoginSession") String cookieName) {

		// 파라미터를 제공하지 않으면 ExceptionHandler 에 의해서 400 에러 발생
		userService.login(loginId, password);

		HttpSession session = request.getSession();
		log.info("session id = {}", session.getId());
		log.info("seesion cookie = {}", cookieName);

		session.setAttribute("toyLoginSession", loginId);

	}

	@GetMapping("/check")
	public void check(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			log.info("session Id = {}", session.getId());
			log.info("session value = {}", session.getAttribute("toyLoginSession"));
		} else {
			log.info("session null");
		}

	}
}
