package com.icesi.edu.taller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	
//METODOS DE GameController
	
	@GetMapping("/login")
	public String login() {
		
	  return "login";
		  
	}


	 @GetMapping("/access-denied")
	 public String loginError(Model model) {
		 
		 return "/security/access-denied";
		  
		 
	  }
		  
		  
	 //
	
}

	

