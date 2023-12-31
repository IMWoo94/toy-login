package toy.login.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.login.jwt.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);
}
