package toy.login.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

public class AuditingConfig {
	@Bean
	public AuditorAware<String> auditorProvider() {
		// 랜덤 입력 값 제공
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
