package toy.login.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DefaultLoginController {

	private final AuthenticationManager authenticationManager;

	@GetMapping("/jwt/page")
	public String loginPage() {
		log.info("login page search");
		return "login";
	}

	@GetMapping("/jwt/login")
	@ResponseBody
	public ResponseEntity<String> login() {
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated("user", "password");
		Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);

		return new ResponseEntity<>(authenticationResponse.toString(), HttpStatus.OK);
	}

}
