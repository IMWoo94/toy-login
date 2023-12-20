package toy.login.users.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import toy.login.users.domain.Address;
import toy.login.users.domain.User;
import toy.login.users.exception.DuplicationUserException;
import toy.login.users.exception.NotFoundUserException;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	UserService userService;

	User data;

	@BeforeEach
	void setUp() {
		data = new User("test1", LocalDate.of(1994, 6, 14), "test1", "test1", new Address("서울", "영등포", "12345"));
		userService.join(data);
	}

	// join 시 중복 입력에 대한 예외 발생
	@Test
	void joinDuplicationException() {
		assertThrows(DuplicationUserException.class, () -> {
			userService.join(data);
		});
	}

	// findId 검색 시 일치 하는 회원 정보 없는 경우 예외 발생
	@Test
	void findUserIdNotFoundException() {
		assertThatThrownBy(() -> {
			userService.findUserId("test", data.getBirthDate());
		}).isInstanceOf(NotFoundUserException.class);
	}

	@Test
	void findUserId() {
		String findId = userService.findUserId(data.getName(), data.getBirthDate());
		assertThat(findId).isEqualTo(data.getLoginId());
		assertThat(findId).isNotNull();
	}

	@Test
	void findUserPassword() {
		String findPassword = userService.findUserPassword(data.getName(), data.getBirthDate(), data.getLoginId());
		assertThat(findPassword).isEqualTo(data.getPassword());
		assertThat(findPassword).isNotNull();
	}

	@Test
	void findUserPasswordNotFoundException() {
		assertThatThrownBy(() -> {
			userService.findUserPassword(data.getName(), data.getBirthDate(), "non");
		}).isInstanceOf(NotFoundUserException.class);
	}

}