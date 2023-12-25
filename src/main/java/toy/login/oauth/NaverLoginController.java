package toy.login.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/naver")
public class NaverLoginController {

	@GetMapping
	public String naverLogin() {
		return "naver/APIExamNaverLogin.html";
	}

	@GetMapping("/callback")
	public String naverLoginCallback() {
		return "naver/callback.html";
	}

}
