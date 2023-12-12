package toy.login.commons.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import toy.login.commons.dto.ErrorDto;
import toy.login.users.exception.DuplicationUserException;
import toy.login.users.exception.NotFoundUserException;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(DuplicationUserException.class)
	public ResponseEntity<ErrorDto> duplicationUserException() {
		return new ResponseEntity<>(new ErrorDto("10001", "이미 가입된 회원 입니다."), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundUserException.class)
	public ResponseEntity<ErrorDto> notFindUserException() {
		return new ResponseEntity<>(new ErrorDto("10002", "일치하는 회원 정보가 없습니다."), HttpStatus.BAD_REQUEST);
	}
}
