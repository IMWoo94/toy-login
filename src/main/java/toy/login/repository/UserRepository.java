package toy.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.login.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
