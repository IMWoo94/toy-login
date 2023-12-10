package toy.login.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.login.users.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);
}
