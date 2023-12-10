package toy.login.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import toy.login.users.domain.User;
import toy.login.users.repository.UserRepository;
import toy.login.users.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserRepository userRepository;
	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity userJoin(@Valid @RequestBody User user) {
		userService.join(user);
		return new ResponseEntity(HttpStatus.OK);
	}
}
