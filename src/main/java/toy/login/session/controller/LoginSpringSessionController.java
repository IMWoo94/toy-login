package toy.login.session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/springsession")
public class LoginSpringSessionController {

	@GetMapping
	public void createSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
	}
}
