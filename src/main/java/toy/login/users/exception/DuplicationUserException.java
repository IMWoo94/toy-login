package toy.login.users.exception;

public class DuplicationUserException extends RuntimeException {
	public DuplicationUserException() {
		super();
	}

	public DuplicationUserException(String message) {
		super(message);
	}

	public DuplicationUserException(String message, Throwable cause) {
		super(message, cause);
	}
}
