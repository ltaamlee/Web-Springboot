package vn.iostar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("appName", "Spring Boot Demo CRUD");
		return "index";
	}
}

