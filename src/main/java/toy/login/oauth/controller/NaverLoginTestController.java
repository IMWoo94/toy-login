package toy.login.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/auth/naver")
@Slf4j
public class NaverLoginTestController {

	/**
	 * 단순히 타 플랫폼에서 제공하는 Resource Server 에 RestTemplate 를 통해서 접근
	 * 즉, 직접 컨트롤러를 생성 해서 OAuth2 인증
	 */

	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String CLIENT_ID;
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String CLIENT_SECRET;
	@Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
	private String AUTHORIZATION_GRANT_TYPE;
	@Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
	private String REDIRECT_URI;
	@Value("${spring.security.oauth2.client.provider.naver.token-uri}")
	private String TOKEN_URL;
	@Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
	private String USER_INFO_URL;

	@GetMapping
	public String home(Model model) {
		log.info("CLIENT_ID : {}", CLIENT_ID);
		log.info("CLIENT_SECRET : {}", CLIENT_SECRET);
		log.info("AUTHORIZATION_GRANT_TYPE : {}", AUTHORIZATION_GRANT_TYPE);
		log.info("REDIRECT_URI : {}", REDIRECT_URI);
		log.info("TOKEN_URL : {}", TOKEN_URL);
		log.info("USER_INFO_URL : {}", USER_INFO_URL);

		model.addAttribute("CLIENT_ID", CLIENT_ID);
		model.addAttribute("REDIRECT_URI", REDIRECT_URI);
		return "/naver/NaverLogin.html";
	}

	@GetMapping("/callback")
	@ResponseBody
	public ResponseEntity loginCallback(@RequestParam("code") String code,
		@RequestParam("state") String state
	) throws Exception {
		// Resource Owner 를 통해서 로그인 + 동의를 받은 경우 Callback
		// code : 로그인 인증 요청 API 호출에 성공 하고 리턴 받은 인증 코드 값 ( authorization code )
		// state : 사이트 간 요청 위조 ( CSRF ) 공격을 방지하기 위해서 애플리케이션에서 생성한 상태 토큰 값
		log.info("code : {}, state : {}", code, state);

		// Resource Server 에 접근하기 위해 Token 발급 시작
		log.info("Token create start");
		ResponseEntity<Tokens> tokenResponse = createTokenRequest(code, state);
		log.info("Token create end");

		// 발급된 토큰을 통해서 프로필 정보 조회
		log.info("User info start");
		userInfoSearch(tokenResponse);
		log.info("User info end");

		return new ResponseEntity("Naver 인증 성공", HttpStatus.OK);
	}

	private void userInfoSearch(ResponseEntity<Tokens> tokenResponse) {
		RestTemplate rt = new RestTemplate();

		HttpHeaders userInfoHeader = new HttpHeaders();
		userInfoHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		userInfoHeader.setPragma("no-cache");
		userInfoHeader.setBearerAuth(tokenResponse.getBody().getAccess_token());
		HttpEntity request = new HttpEntity(userInfoHeader);

		ResponseEntity<String> userInfoResponse = rt.exchange(
			USER_INFO_URL,
			HttpMethod.POST,
			request,
			String.class
		);
		log.info(userInfoResponse.getBody().toString());
	}

	private ResponseEntity<Tokens> createTokenRequest(String code, String state) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders accessTokenHeaders = new HttpHeaders();
		accessTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
		accessTokenParams.add("client_id", CLIENT_ID);
		accessTokenParams.add("client_secret", CLIENT_SECRET);
		accessTokenParams.add("grant_type", AUTHORIZATION_GRANT_TYPE);
		accessTokenParams.add("code", code);
		accessTokenParams.add("state", state);

		HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams,
			accessTokenHeaders);

		ResponseEntity<Tokens> accessTokenResponse = rt.exchange(
			TOKEN_URL,
			HttpMethod.POST,
			accessTokenRequest,
			Tokens.class
		);
		log.info(accessTokenResponse.getBody().toString());

		return accessTokenResponse;
	}

}
