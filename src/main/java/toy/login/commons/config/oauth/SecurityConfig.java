package toy.login.commons.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import toy.login.oauth.service.CustomOAuth2UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizationRequests ->
			authorizationRequests
				.requestMatchers("/login", "/css/**", "/img/**", "/login/oauth2/**").permitAll()
				.anyRequest().authenticated()
		).oauth2Login(
			login -> login.loginPage("/login/oauth2/oauth_login")
				.defaultSuccessUrl("/api/profiles", true)
		);
		return http.build();
	}

}
