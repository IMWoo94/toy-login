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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import toy.login.users.domain.User;
import toy.login.users.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
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
}
