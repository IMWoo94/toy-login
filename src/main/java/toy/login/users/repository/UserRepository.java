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
}
