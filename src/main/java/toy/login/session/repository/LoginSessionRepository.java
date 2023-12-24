package toy.login.session.repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class LoginSessionRepository {

	private static final Map<String, Object> STORAGE = new ConcurrentHashMap<>();

	public String createSession(Object value) {
		String sessionId = UUID.randomUUID().toString();
		STORAGE.put(sessionId, value);

		return sessionId;
	}

	public Object getSession(String sessionId) {
		return STORAGE.getOrDefault(sessionId, null);
	}

	public void removeSession(String sessionId) {
		STORAGE.remove(sessionId);
	}

}
