package toy.login.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

// @EnableRedisRepositories
// @Configuration
public class RedisConfig {

	/**
	 * 별도로 선언을 해서 redisConnectionFactory 를 변경할 수 있다.
	 * 하지만 Spring boot 를 사용하면 별도로 지정하지 않아도 autoConfiguration 에 의해서 등록된다.
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}
}
