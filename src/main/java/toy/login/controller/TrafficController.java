package toy.login.controller;

import java.util.ArrayList;
import java.util.List;

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

	private List<String> list = new ArrayList<>();

	@GetMapping("/jvm")
	public String jvm() {
		log.info("jvm");
		for (int i = 0; i < 1000000; i++) {
			list.add("hello jvm!" + i);
		}
		return "ok";
	}
}
