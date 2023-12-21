package toy.login.users.controller;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.users.service.UserService;

@RestController
@Slf4j
@RequestMapping("/cookie/login")
@RequiredArgsConstructor
public class LoginCookieController {

	private final UserService userService;

	@GetMapping("/v1")
	public ResponseEntity loginCookieV1(@RequestParam String loginId,
		@RequestParam String password,
		HttpServletResponse response,
		HttpServletRequest request
	) {

		// httpRequest 에서 넘어온 로그인 관련 쿠키 정보가 있는지 확인
		boolean isCookie = false;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			// 로그인 된 경우 쿠키 [userLoginResult] 값이 "Y" 이다.
			if (cookie.getName().equals("userLoginResult") && cookie.getValue().equals("Y")) {
				isCookie = true;
			}
		}

		// 쿠키가 존재하면 자동 로그인 처리
		if (isCookie) {
			return new ResponseEntity<>("기 로그인되어 자동 로그인 되었습니다.", HttpStatus.OK);
		}

		// 쿠키 정보가 없거나, 쿠키 값이 아니라면 로그인 성공 여부에 따라 쿠키 정보 등록

		// 사용자로 부터 ID 와 PW 를 받는다. -> 일단은 HTTP 쿼리 파라미터로 제공
		// 파라미터를 제공하지 않으면 ExceptionHandler 에 의해서 400 에러 발생
		userService.login(loginId, password);

		// 쿠키에 시간 정보를 주지 않으면 세션 쿠키 ( 브라우저 종료 시 모두 종료 )
		Cookie idCookie = new Cookie("userLoginResult", "Y");
		response.addCookie(idCookie);

		return new ResponseEntity<>("로그인 되었습니다.", HttpStatus.OK);
	}

	@GetMapping("/v2")
	public ResponseEntity loginCookieV2(@RequestParam String loginId,
		@RequestParam String password,
		HttpServletResponse response,
		@CookieValue(name = "userLoginResult", required = false) String userLoginResult
	) {

		/**
		 * @CookieValue(name = "userLoginResult", required = false)
		 * Cookie 명이 userLoginResult 것을 찾고 없을 수도 있기 때문에 required = false 처리
		 */

		/**
		 * httpRequest 를 받아서 하면 request 에 담겨 있는 모든 쿠키에 대해서 원하는 쿠키 정보가 있는지 확인을 해야 한다.
		 * 이를 편하게 하기 위해서 @CookieValue 어노테이션을 사용하면 일치하는 Cookie 에 대해서 Controller 에 넘어오기 전에 값을 가져올 수 있다.
		 */
		// 쿠키 정보가 없거나, 쿠키 값이 아니라면 로그인 성공 여부에 따라 쿠키 정보 등록
		if (userLoginResult == null || !"Y".equals(userLoginResult)) {
			// 사용자로 부터 ID 와 PW 를 받는다. -> 일단은 HTTP 쿼리 파라미터로 제공
			// 파라미터를 제공하지 않으면 ExceptionHandler 에 의해서 400 에러 발생
			userService.login(loginId, password);

			// 쿠키에 시간 정보를 주지 않으면 세션 쿠키 ( 브라우저 종료 시 모두 종료 )
			Cookie idCookie = new Cookie("userLoginResult", "Y");
			response.addCookie(idCookie);
		} else {
			// 쿠키가 존재하면 자동 로그인 처리
			return new ResponseEntity<>("기 로그인되어 자동 로그인 되었습니다.", HttpStatus.OK);
		}

		return new ResponseEntity<>("로그인 되었습니다.", HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity logout(HttpServletResponse response) {
		Cookie idCookie = new Cookie("userLoginResult", "");
		// 쿠키의 수명 시간을 0으로 선언 시 쿠키가 삭제 처리 됩니다.
		idCookie.setMaxAge(0);
		response.addCookie(idCookie);

		return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);

	}

}
