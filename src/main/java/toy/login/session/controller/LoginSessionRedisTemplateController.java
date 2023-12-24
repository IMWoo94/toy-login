package toy.login.session.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.users.service.UserService;

@RestController
@Slf4j
@RequestMapping("/session/template")
@RequiredArgsConstructor
public class LoginSessionRedisTemplateController {

	private final RedisTemplate<String, String> redisTemplate;
	private final UserService userService;

	@GetMapping("/login")
	public ResponseEntity login(@RequestParam String loginId,
		@RequestParam String password,
		HttpServletRequest request
	) {

		userService.login(loginId, password);

		HttpSession session = request.getSession();

		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		operations.set(session.getId(), "mySessionId");

		return new ResponseEntity("로그인 되었습니다.", HttpStatus.OK);
	}

	@GetMapping("/check")
	public ResponseEntity check(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String id = session.getId();
			ValueOperations<String, String> operations = redisTemplate.opsForValue();
			String s = operations.get(id);
			if (s.equals("mySessionId")) {
				return new ResponseEntity<>("기 로그인되어 자동 로그인 되었습니다.", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("로그인 먼저 진행해주세요.", HttpStatus.UNAUTHORIZED);
	}
}
