package toy.login.commons.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import toy.login.commons.dto.ErrorDto;
import toy.login.users.exception.DuplicationUserException;
import toy.login.users.exception.NotFoundLoginException;
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

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorDto> requiredRequestParameterException() {
		return new ResponseEntity<>(new ErrorDto("10003", "파라미터 정보가 없습니다."), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundLoginException.class)
	public ResponseEntity<ErrorDto> notFoundLoginException() {
		return new ResponseEntity<>(new ErrorDto("10004", " 아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.\n"
			+ "입력하신 내용을 다시 확인해주세요.."), HttpStatus.BAD_REQUEST);
	}

}
