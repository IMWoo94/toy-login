package toy.login.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.users.service.UserService;

@RestController
@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity login(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity(HttpStatus.OK);
	}

}
