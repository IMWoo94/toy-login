package toy.login.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import toy.login.domain.Address;
import toy.login.domain.User;

@SpringBootTest
@Transactional(readOnly = true)
@Rollback(value = false)
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@BeforeEach
	@Transactional
	void setUp() {
		User user1 = new User("이상민1", LocalDate.now(), "lee1", "lee", new Address("서울", "영등포", "12345"));
		User user2 = new User("이상민2", LocalDate.now(), "lee2", "lee", new Address("서울", "영등포", "12345"));
		User user3 = new User("이상민3", LocalDate.now(), "lee3", "lee", new Address("서울", "영등포", "12345"));
		User user4 = new User("이상민4", LocalDate.now(), "lee4", "lee", new Address("서울", "영등포", "12345"));
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
	}

	@Test
	@Transactional
	void userJoin() {
		User user1 = new User("이상민", LocalDate.now(), "lee", "lee", new Address("서울", "영등포", "12345"));
		User save = userRepository.save(user1);
		Assertions.assertThat(user1).isEqualTo(save);
	}

	@Test
	void userFind() {
		Optional<User> findUser = userRepository.findById(2L);
		if (findUser.isPresent()) {
			User user = findUser.get();
			System.out.println("user = " + user);
		}
	}

	@Test
	void jacocoTest() {
		System.out.println("UserRepositoryTest.jacocoTest retest");
	}

	@Test
	void profileTest() {
		System.out.println(System.getProperty("spring.profiles.active"));
	}

}