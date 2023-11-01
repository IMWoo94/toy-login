package toy.login.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import toy.login.domain.User;
import toy.login.repository.UserRepository;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserRepository userRepository;

	@PostMapping("/join")
	public ResponseEntity userJoin(@Valid @RequestBody User user) {
		Optional<User> findUser = userRepository.findById(user.getId());
		if (findUser.isEmpty()) {
			userRepository.save(user);
		} else {
			throw new IllegalStateException("이미 가입된 회원 입니다.");
		}
		return new ResponseEntity(HttpStatus.OK);
	}
}
