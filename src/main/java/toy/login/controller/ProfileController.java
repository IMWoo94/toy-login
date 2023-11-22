package toy.login.controller;

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
		List<String> profiles = Arrays.asList(environment.getActiveProfiles());
		List<String> useProfiles = Arrays.asList("blue", "green");
		String defaultProfile = profiles.get(0);

		return Arrays.stream(environment.getActiveProfiles())
			.filter(useProfiles::contains)
			.findAny()
			.orElse(defaultProfile);
	}
}
