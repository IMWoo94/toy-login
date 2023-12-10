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
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

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
		assertThat(user1).isEqualTo(save);
	}

	@Test
	void userFindAllTest() {
		List<User> all = userRepository.findAll();
		assertThat(all.size()).isGreaterThan(0);
	}

	@Test
	void userFindName() {
		Optional<User> find = userRepository.findByName("이상민1");
		assertThat(find.isPresent()).isTrue();
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