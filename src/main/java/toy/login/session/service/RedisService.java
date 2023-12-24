package toy.login.session.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.session.domain.LoginInfo;
import toy.login.session.repository.LoginInfoRedisRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

	private final LoginInfoRedisRepository loginInfoRedisRepository;

	public void save(LoginInfo loginInfo) {

		// loginInfo 저장
		loginInfoRedisRepository.save(loginInfo);

		// keyspace:id 의 값을 찾는다.
		// loginInfoRedisRepository.findById(loginInfo.getSessionId());

		// Entity 에 정의되어 있는 @RedisHash 에 정의되어 있는 keyspace 에 속하는 키의 갯수를 구합니다.
		// loginInfoRedisRepository.count();

		// 삭제
		// loginInfoRedisRepository.delete(save);
	}

	public LoginInfo findSessionId(String sessionId) {
		return loginInfoRedisRepository.findById(sessionId).orElse(null);
	}

	public void delete(LoginInfo loginInfo) {
		loginInfoRedisRepository.delete(loginInfo);
	}

}
