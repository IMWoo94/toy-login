package toy.login.users.exception;

public class NotFoundLoginException extends RuntimeException {
	public NotFoundLoginException() {
		super();
	}

	public NotFoundLoginException(String message) {
		super(message);
	}

	public NotFoundLoginException(String message, Throwable cause) {
		super(message, cause);
	}
}
