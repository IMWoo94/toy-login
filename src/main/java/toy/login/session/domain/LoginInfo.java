package toy.login.session.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RedisHash(value = "loginInfo")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginInfo {

	@Id
	private String sessionId;

	@Indexed
	private String jSessionId;

	@TimeToLive
	private long ttl;

	public LoginInfo(String jSessionId) {
		this.jSessionId = jSessionId;
	}

	public LoginInfo(String jSessionId, long ttl) {
		this.jSessionId = jSessionId;
		this.ttl = ttl;
	}

	public LoginInfo(String sessionId, String jSessionId, long ttl) {
		this.sessionId = sessionId;
		this.jSessionId = jSessionId;
		this.ttl = ttl;
	}
}
