package toy.login.session.repository;

import org.springframework.data.repository.CrudRepository;

import toy.login.session.domain.LoginInfo;

public interface LoginInfoRedisRepository extends CrudRepository<LoginInfo, String> {

}
