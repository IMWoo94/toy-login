package toy.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/traffics")
public class TrafficController {

	@GetMapping("/cpu")
	public String cpu() {
		log.info("cpu");
		long value = 0;
		for (int i = 0; i < 100000000000L; i++) {
			value++;
		}
		return "ok value = " + value;
	}
}
