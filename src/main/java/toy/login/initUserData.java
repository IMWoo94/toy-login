package toy.login;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.login.users.domain.Address;
import toy.login.users.domain.User;
import toy.login.users.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class initUserData {
	private final UserService userService;

	@PostConstruct
	public void init() {
		log.info("초기 회원 정보 등록");
		userService.join(new User("tester", LocalDate.now(), "admin", "admin", new Address("서울", "영등포", "55555")));
	}

}
