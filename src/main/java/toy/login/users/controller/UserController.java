package toy.login.users.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.users.domain.User;
import toy.login.users.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity userJoin(@Valid @RequestBody User user) {
		userService.join(user);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/findId")
	public ResponseEntity<String> findUserId(@RequestParam("name") String name,
		@RequestParam("birthDate") LocalDate birthDate) {
		return new ResponseEntity<>(userService.findUserId(name, birthDate), HttpStatus.OK);
	}

	@GetMapping("/findPassword")
	public ResponseEntity<String> findUserPassword(@RequestParam("name") String name,
		@RequestParam("birthDate") LocalDate birthDate,
		@RequestParam("loginId") String loginId) {
		return new ResponseEntity<>(userService.findUserPassword(name, birthDate, loginId), HttpStatus.OK);
	}

	@GetMapping("/login/cookie")
	public ResponseEntity loginCookie(@RequestParam String loginId, @RequestParam String password,
		HttpServletResponse response, HttpServletRequest request) {

		// httpRequest 에서 넘어온 로그인 관련 쿠기 정보가 있는지 확인
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
}
