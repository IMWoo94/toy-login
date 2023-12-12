package toy.login.users.exception;

public class NotFoundUserException extends RuntimeException {
	public NotFoundUserException() {
		super();
	}

	public NotFoundUserException(String message) {
		super(message);
	}

	public NotFoundUserException(String message, Throwable cause) {
		super(message, cause);
	}
}
