package ro.fortech.betting.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.fortech.betting.demonstration.EnvironmentProvider;

@Controller
public class HomeController {

	@Autowired
	private EnvironmentProvider environmentProvider;

	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Welcome to our betting application. " + environmentProvider.provideEnvironmentName();
	}

}
