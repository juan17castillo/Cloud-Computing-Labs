package com.icesi.edu.taller.controller;

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
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.TopicService;

@Controller
public class TopicController {
	
	@Autowired
	TopicService topServ;

	@GetMapping("/topics/")
	public String index(Model model) {
		
		model.addAttribute("topics", topServ.findAll());
		
		return "topics/index";
	}
		
	@GetMapping("/topics/add")
	public String add(Model model) {
		
		model.addAttribute("tsscTopic", new TsscTopic());
		
		return "topics/add";
	}
	
	@PostMapping("/topics/add")
	public String add(@Validated TsscTopic topic, BindingResult bd, @RequestParam(value = "action", required = true) String action, Model model) {
			
		if(bd.hasErrors()&&!action.equalsIgnoreCase("Cancel")) {
			
			model.addAttribute("tsscTopic", topic);
			return "topics/add";
		}
		if(!action.equalsIgnoreCase("Cancel")) {
			
			topServ.save(topic);
		}
		return "redirect:/topics/";
	}
	
	@GetMapping("/topics/modify/{id}")
	public String modify(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("tsscTopic", topServ.findById(id));
		
		return "topics/modify";
	}
	
	@PostMapping("/topics/modify/{id}")
	public String modify(@Validated TsscTopic topic, BindingResult bd, @RequestParam(value = "action", required = true) String action, @PathVariable("id") long id,  Model model) {
		if(bd.hasErrors()&&!action.equalsIgnoreCase("Cancel")) {
			
			model.addAttribute("tsscTopic", topic);
			
			return "topics/modify";
		}
		if(!action.equalsIgnoreCase("Cancel")) {
			
			TsscTopic z = topServ.findById(id);
			z.setName(topic.getName());
			z.setDefaultGroups(topic.getDefaultGroups());
			z.setDefaultSprints(topic.getDefaultSprints());
			z.setGroupPrefix(topic.getGroupPrefix());
			topServ.save(z);
		}
		return "redirect:/topics/";
	}
		
	@GetMapping("/topics/showStories/{id}")
	public String showStories(Model model, @PathVariable("id") long id) {
		
		TsscTopic gam = topServ.findById(id); 
		model.addAttribute("stories", gam.getTsscStories());
		model.addAttribute("gameName", gam.getName());
		
		return "topics/storyTopic";
	}
	
	@GetMapping("/topics/showGames/{id}")
	public String showGames(Model model, @PathVariable("id") long id) {
		
		TsscTopic gam = topServ.findById(id); 
		model.addAttribute("games", gam.getTsscGames());
		model.addAttribute("gameName", gam.getName());
		return "topics/gameTopic";
		
		
	}
	
	
	//
	
}
