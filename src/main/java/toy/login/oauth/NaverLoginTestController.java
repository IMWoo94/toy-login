package toy.login.oauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

// @RestController
// @RequestMapping("/auth/naver")
@Slf4j
public class NaverLoginTestController {

	// @GetMapping("/callback")
	public ResponseEntity loginCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
		log.info("code : {}", code);
		log.info("state : {}", state);
		return new ResponseEntity("Naver 인증 성공", HttpStatus.OK);
	}
}
