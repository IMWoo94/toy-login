package toy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import toy.login.cookie.interceptor.LoginCookieCheckInterceptor;
import toy.login.session.interceptor.LoginSessionCheckInterceptor;
import toy.login.session.repository.SessionRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final SessionRepository sessionRepository;

	public WebConfig(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCookieCheckInterceptor())
			.addPathPatterns("/cookie/**")
			.excludePathPatterns(
				"/cookie/login", "/cookie/login/logout",
				"/css/**", "/*.ico", "/error"
			);

		registry.addInterceptor(new LoginSessionCheckInterceptor(sessionRepository))
			.addPathPatterns("/session/**")
			.excludePathPatterns(
				"/session/login", "/session/login/logout",
				"/css/**", "/*.ico", "/error"
			);
	}
}
