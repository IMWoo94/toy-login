package toy.login.commons.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

	private final RedisTemplate<String, String> redisTemplate;

	@GetMapping
	public String test() {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		log.info("redisFactory default autoconfig = {}",
			redisTemplate.getConnectionFactory().getClass().getSimpleName());
		operations.set("test", "test");
		return operations.get("test");
	}
}
