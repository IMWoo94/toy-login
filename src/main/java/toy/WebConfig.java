package toy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import toy.login.cookie.interceptor.LoginCookieCheckInterceptor;
import toy.login.session.interceptor.LoginSessionCheckInterceptor;
import toy.login.session.repository.LoginSessionRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final LoginSessionRepository loginSessionRepository;

	public WebConfig(LoginSessionRepository loginSessionRepository) {
		this.loginSessionRepository = loginSessionRepository;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCookieCheckInterceptor())
			.addPathPatterns("/cookie/**")
			.excludePathPatterns(
				"/cookie/login", "/cookie/login/logout",
				"/css/**", "/*.ico", "/error"
			);

		registry.addInterceptor(new LoginSessionCheckInterceptor(loginSessionRepository))
			.addPathPatterns("/session/**")
			.excludePathPatterns(
				"/session/login", "/session/login/logout",
				"/css/**", "/*.ico", "/error"
			);
	}
}
