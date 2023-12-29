package toy.login.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/login/oauth2")
public class OAuthClientController {

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/oauth_login")
	public String getLoginPage(Model model) {
		log.info("Login Page Get");
		Iterable<ClientRegistration> clientRegistrations = null;
		ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
			.as(Iterable.class);
		if (type != ResolvableType.NONE &&
			ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>)clientRegistrationRepository;
		}

		clientRegistrations.forEach(registration -> log.info("registration = {}", registration.toString()));

		return "/oauth/login";
	}

	@GetMapping("/loginSuccess")
	@ResponseBody
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
		log.info("authentication = {}", authentication.toString());

		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
			authentication.getAuthorizedClientRegistrationId(),
			authentication.getName()
		);
		log.info("client ={}", client.toString());

		String userInfoEndpointUri = client.getClientRegistration()
			.getProviderDetails().getUserInfoEndpoint().getUri();

		String body = "";
		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
				.getTokenValue());
			HttpEntity entity = new HttpEntity(headers);
			ResponseEntity<String> response = restTemplate
				.exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);
			body = response.getBody();
		}

		return "login Success" + body;
	}

}
