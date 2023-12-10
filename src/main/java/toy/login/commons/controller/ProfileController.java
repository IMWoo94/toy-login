package toy.login.commons.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {
	private final Environment environment;

	@GetMapping
	public String profile() {
		// 현재 실행 중인 ActiveProfile 을 모두 가져 온다.
		List<String> profiles = Arrays.asList(environment.getActiveProfiles());
		List<String> useProfiles = Arrays.asList("blue", "green");
		String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

		return Arrays.stream(environment.getActiveProfiles())
			.filter(useProfiles::contains)
			.findAny()
			.orElse(defaultProfile);
	}
}
