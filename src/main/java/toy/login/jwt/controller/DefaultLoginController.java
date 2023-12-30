package toy.login.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DefaultLoginController {

	private final AuthenticationManager authenticationManager;

	@GetMapping("/jwt/login")
	public ResponseEntity<String> login() {
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated("user", "password");
		Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);

		return new ResponseEntity<>(authenticationResponse.toString(), HttpStatus.OK);
	}

}
