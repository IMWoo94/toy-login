package toy.login.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/login/oauth2")
public class OAuthClientController {

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

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

}
