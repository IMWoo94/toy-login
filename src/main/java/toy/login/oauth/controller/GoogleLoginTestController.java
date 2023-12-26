package toy.login.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import toy.login.oauth.domain.Tokens;

@Controller
@RequestMapping("/auth/google")
@Slf4j
public class GoogleLoginTestController {

	/**
	 * 단순히 타 플랫폼에서 제공하는 Resource Server 에 RestTemplate 를 통해서 접근
	 * 즉, 직접 컨트롤러를 생성 해서 OAuth2 인증
	 */

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String CLIENT_ID;
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String CLIENT_SECRET;
	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String REDIRECT_URI;
	@Value("${spring.security.oauth2.client.provider.google.token-uri}")
	private String TOKEN_URL;
	@Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
	private String USER_INFO_URL;

	@GetMapping
	public String home(Model model) {
		log.info("CLIENT_ID : {}", CLIENT_ID);
		log.info("CLIENT_SECRET : {}", CLIENT_SECRET);
		log.info("REDIRECT_URI : {}", REDIRECT_URI);

		model.addAttribute("CLIENT_ID", CLIENT_ID);
		model.addAttribute("REDIRECT_URI", REDIRECT_URI);
		return "/google/GoogleLogin.html";
	}

	@GetMapping("/callback")
	@ResponseBody
	public ResponseEntity loginCallback(@RequestParam("code") String code
	) throws Exception {

		log.info("code = {}", code);

		// 발급 받은 인가 코드를 통해서 토큰을 발행합니다.
		log.info("Token create start");
		ResponseEntity<Tokens> tokenResponse = createTokenRequest(code);
		log.info("Token create end");

		// Access Token 을 통해서 사용자 정보 가져오기
		log.info("User info start");
		userInfoSearch(tokenResponse);
		log.info("User info end");

		return new ResponseEntity("Google 인증 성공", HttpStatus.OK);
	}

	private void userInfoSearch(ResponseEntity<Tokens> tokenResponse) {
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(tokenResponse.getBody().getAccess_token());

		HttpEntity request = new HttpEntity(headers);

		ResponseEntity<String> infoResponse = rt.exchange(
			USER_INFO_URL,
			HttpMethod.GET,
			request,
			String.class
		);

		log.info(infoResponse.getBody().toString());
	}

	private ResponseEntity<Tokens> createTokenRequest(String code) {
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", code);
		params.add("client_id", CLIENT_ID);
		params.add("client_secret", CLIENT_SECRET);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> googleRequest = new HttpEntity<>(params, headers);

		ResponseEntity<Tokens> tokenResponse = rt.exchange(
			TOKEN_URL,
			HttpMethod.POST,
			googleRequest,
			Tokens.class
		);

		log.info(tokenResponse.getBody().toString());

		return tokenResponse;
	}

}
