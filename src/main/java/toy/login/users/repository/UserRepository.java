package toy.login.users.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import toy.login.users.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);

	@Query("select u.loginId from User u where u.name = :name and u.birthDate = :birthDate")
	String findByNameAndBirthDate(@Param("name") String name, @Param("birthDate") LocalDate birthDate);

	@Query("select u.password from User u where u.name = :name and u.birthDate = :birthDate and u.loginId = :loginId")
	String findByNameAndBirthDateAndLoginId(@Param("name") String name,
		@Param("birthDate") LocalDate birthDate, @Param("loginId") String loginId);

	@Query("select count(u) from User u where u.loginId = :loginId and u.password = :password")
	int findByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);
}
