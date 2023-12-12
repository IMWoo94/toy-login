package toy.login.users.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import toy.login.users.domain.Address;
import toy.login.users.domain.User;
import toy.login.users.service.UserService;

@SpringBootTest
@Transactional
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@BeforeEach
	void setUp() {
		User user1 = new User("test1", LocalDate.of(1994, 6, 14), "test1", "test", new Address("서울", "영등포", "12345"));
		User user2 = new User("test2", LocalDate.now(), "test2", "test", new Address("서울", "영등포", "12345"));
		User user3 = new User("test3", LocalDate.now(), "test3", "test", new Address("서울", "영등포", "12345"));
		User user4 = new User("test4", LocalDate.now(), "test4", "test", new Address("서울", "영등포", "12345"));
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
	}

	@Test
	void userJoin() {
		User user1 = new User("newUser", LocalDate.now(), "newUser", "newUser", new Address("서울", "영등포", "12345"));
		User save = userRepository.save(user1);
		assertThat(user1).isEqualTo(save);
	}

	@Test
	void userFindAllTest() {
		List<User> all = userRepository.findAll();
		assertThat(all.size()).isGreaterThan(0);
	}

	@Test
	void userFindName() {
		Optional<User> find = userRepository.findByName("test1");
		assertThat(find.isPresent()).isTrue();
	}

	@Test
	void findUserIdAndBirthDate() {
		String findId = userRepository.findByNameAndBirthDate("test1", LocalDate.of(1994, 6, 14));
		assertThat(findId).isNotNull();
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