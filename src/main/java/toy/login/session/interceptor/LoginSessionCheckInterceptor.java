package toy.login.session.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.session.repository.SessionRepository;

@RequiredArgsConstructor
@Slf4j
public class LoginSessionCheckInterceptor implements HandlerInterceptor {

	private final SessionRepository sessionRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		log.info("LoginSessionCheckInterceptor preHandle");
		String mySessionId = "";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("mySessionId")) {
				mySessionId = cookie.getValue();
				break;
			}
		}

		Object sessionId = sessionRepository.getSession(mySessionId);
		HttpSession session = request.getSession(false);
		if (session == null || sessionId == null) {
			return false;
		}
		if (sessionId.equals(session.getId())) {
			return true;
		}

		return false;
	}
}
