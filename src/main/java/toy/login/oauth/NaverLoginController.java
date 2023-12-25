package toy.login.oauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth/naver")
@Slf4j
public class NaverLoginController {

	@GetMapping("/callback")
	public ResponseEntity loginCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
		log.info("code : {}", code);
		log.info("state : {}", state);
		return new ResponseEntity("Naver 인증 성공", HttpStatus.OK);
	}
}
