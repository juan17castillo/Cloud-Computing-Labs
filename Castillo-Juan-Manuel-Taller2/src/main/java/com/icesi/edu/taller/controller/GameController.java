package com.icesi.edu.taller.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;
import com.icesi.edu.taller.service.TopicService;

@Controller
public class GameController {
	
	@Autowired
	StoryService admServ;
	
	@Autowired
	GameService gameServ;
	
	@Autowired
	TopicService topServ;
	
	
    @GetMapping("/login")
    public String login() {
    	
	   return "login";
    }

   @GetMapping("/access-denied")
    public String loginError(Model model) {
	   
	   return "/security/access-denied";
    }

	@GetMapping("/games/")
	public String index(Model model) {
		
		model.addAttribute("games", gameServ.findAll());
		
		return "games/index";
	}
		
	@GetMapping("/games/add")
	public String add(Model model) {
		
		model.addAttribute("tsscGame", new TsscGame());
		List<TsscTopic> topics = (List<TsscTopic>) topServ.findAll();
		TsscTopic top = new TsscTopic();
		top.setName("No Topic");
		topics.add(top);
		model.addAttribute("topics", topics);
		return "games/add";
		
		
	}
	
	
	@PostMapping("/games/add")
	public String add(@Validated TsscGame game, BindingResult bd, @RequestParam(value = "action", required = true) String action, Model model) {
			
		if(bd.hasErrors()&&!action.equalsIgnoreCase("Cancel")) {
			
			model.addAttribute("tsscGame", game);
			model.addAttribute("topics", topServ.findAll());
			
			return "games/add";
		}
		if(!action.equalsIgnoreCase("Cancel")) {
			
			gameServ.save(game);
		}
		return "redirect:/games/";
		
		
	}
	
	@GetMapping("/games/modify/{id}")
	public String modify(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("tsscGame", gameServ.findById(id));
		model.addAttribute("topics", topServ.findAll());
		
		return "games/modify";
		
		
	}
	
	@PostMapping("/games/modify/{id}")
	public String modify(@Validated TsscGame game, BindingResult bd, @RequestParam(value = "action", required = true) String action, @PathVariable("id") long id,  Model model) {
		if(bd.hasErrors()&&!action.equalsIgnoreCase("Cancel")) {
			
			model.addAttribute("tsscGame", game);
			model.addAttribute("topics", topServ.findAll());
			return "games/modify";
		}
		if(!action.equalsIgnoreCase("Cancel")) {
			
			gameServ.save(game);
		}
		return "redirect:/games/";
		
		
	}
		

	@GetMapping("/games/showStories/{id}")
	public String showStories(Model model, @PathVariable("id") long id) {
		
		TsscGame gam = gameServ.findById(id); 
		model.addAttribute("stories", gam.getTsscStories());
		model.addAttribute("gameName", gam.getName());
		
		return "games/storyGame";
	}
	
	@GetMapping("/games/showTopic/{id}")
	public String showTopic(Model model, @PathVariable("id") long id) {
		
		TsscGame gam = gameServ.findById(id);
		if(gam.getTsscTopic()!=null) {
			
			List<TsscTopic> z = new ArrayList<>();
			z.add(gam.getTsscTopic());
			model.addAttribute("topics", z);
		}
		
		model.addAttribute("gameName", gam.getName());
		return "stories/topicGame";
		
		
	}
	
	
	
	//
}
