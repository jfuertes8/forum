package com.ite.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	/*Este controlador simplemente muestra la página de inicio*/
	@GetMapping("/")
	public String inicio() {
		return "index";
	}
	
}
