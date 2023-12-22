package toy.login.cookie.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Cookie 정보로 login 여부를 Interceptor 에서 확인
 */
@Slf4j
public class LoginCookieCheckInterceptor implements HandlerInterceptor {

	// 디스패처 서블릿에서 핸들러 어댑터에 넘어가기 전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		log.info("LoginCookieCheckInterceptor preHandle");
		String requestURI = request.getRequestURI();

		log.info("인증 체크 인터셉터 실행 {}", requestURI);

		Cookie[] cookies = request.getCookies();
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		if (cookies == null || sessionId == null) {
			log.info("미 로그인");
			return false;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("loginCookieSessionId") && sessionId.equals(cookie.getValue())) {
				log.info("로그인");
				return true;
			}
		}
		log.info("미 로그인");
		return false;
	}

	// 컨트롤러를 다녀오고 나서 view 를 찾기 전 -> 예외 발생 시 작동 x
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		log.info("LoginCookieCheckInterceptor postHandle");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	// 사용자에게 넘어가기 전 마지막 예외 시에도 발생
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		log.info("LoginCookieCheckInterceptor afterCompletion");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
