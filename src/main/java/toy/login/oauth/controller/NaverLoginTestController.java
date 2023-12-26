package toy.login.oauth.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import toy.login.oauth.domain.Tokens;

@RestController
@RequestMapping("/auth/naver")
@Slf4j
public class NaverLoginTestController {

	@Value
	private final String CLIENT_ID;
	private final String CLIENT_SECRET;

	/**
	 * 단순히 타 플랫폼에서 제공하는 Resource Server 에 RestTemplate 를 통해서 접근
	 * 즉, 직접 컨트롤러를 생성해서 OAuth2 인증
	 */
	@GetMapping("/callback")
	public ResponseEntity loginCallback(@RequestParam("code") String code,
		@RequestParam("state") String state
	) throws Exception {
		log.info("code : {}", code);
		log.info("state : {}", state);
		if (state.equals("test")) {
			log.info("naver login token create start");
			// naver 접근을 위한 token 발행
			RestTemplate restTemplate = new RestTemplate();
			String url = String.format(
				"https://nid.naver.com/oauth2.0/token?client_id=X3TOXUfuQPDsj_XILMLv&client_secret=gLTDPJh48o&grant_type=authorization_code&state=%s&code=%s",
				state, code);
			ResponseEntity<Tokens> response = restTemplate.getForEntity(url, Tokens.class);
			log.info(response.getBody().toString());
			log.info("token create ok");

			// 발급된 토큰을 통해서 프로필 정보 조회
			log.info("naver login token used my profile info start");
			HttpHeaders headers = new HttpHeaders();
			headers.setPragma("no-cache");
			headers.setBearerAuth(response.getBody().getAccess_token());
			HttpEntity request = new HttpEntity(headers);

			// String token = response.getBody().token();
			String findUrl = "https://openapi.naver.com/v1/nid/me";
			ResponseEntity<String> findResponse = restTemplate.postForEntity(findUrl, request, String.class);
			log.info(findResponse.getBody().toString());
			log.info("find my info");

		}

		return new ResponseEntity("Naver 인증 성공", HttpStatus.OK);
	}

}
