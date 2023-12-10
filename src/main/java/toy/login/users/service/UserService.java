package toy.login.users.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toy.login.users.domain.User;
import toy.login.users.exception.DuplicationUserException;
import toy.login.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public void join(User user) {
		validateDuplicationUser(user);
		userRepository.save(user);
	}

	private void validateDuplicationUser(User user) {
		Optional<User> findUser = userRepository.findByName(user.getName());
		if (findUser.isPresent()) {
			throw new DuplicationUserException();
		}
	}
}
