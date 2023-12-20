package toy.login.users.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import toy.login.users.domain.User;
import toy.login.users.exception.DuplicationUserException;
import toy.login.users.exception.NotFoundUserException;
import toy.login.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional
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

	@Transactional(readOnly = true)
	public String findUserId(String name, LocalDate birthDate) {
		String find = userRepository.findByNameAndBirthDate(name, birthDate);
		if (find == null) {
			throw new NotFoundUserException();
		}
		return find;
	}

	@Transactional(readOnly = true)
	public String findUserPassword(String name, LocalDate birthDate, String loginId) {
		String find = userRepository.findByNameAndBirthDateAndLoginId(name, birthDate, loginId);
		if (find == null) {
			throw new NotFoundUserException();
		}
		return find;
	}
}
