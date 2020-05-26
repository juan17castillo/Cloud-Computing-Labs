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
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;

@Controller
public class StoryController {
	
	@Autowired
	StoryService stoServ;
	
	
	@Autowired
	GameService gameServ;
	
	

	@GetMapping("/stories/")
	public String index(Model model) {
		
		model.addAttribute("stories", stoServ.findAll());
		
		return "stories/index";
	}
		
	@GetMapping("/stories/add")
	public String add(Model model) {
		
		model.addAttribute("tsscStory", new TsscStory());
		model.addAttribute("games", gameServ.findAll());
		return "stories/add";
		
	}
	
	@PostMapping("/stories/add")
	public String add(@Validated TsscStory story, BindingResult bd, @RequestParam(value = "action", required = true) String action, Model model) {
			
		if(bd.hasErrors()&&!action.equalsIgnoreCase("Cancel")) {
			
			model.addAttribute("tsscStory", story);
			model.addAttribute("games", gameServ.findAll());
			
			return "stories/add";
		}
		if(!action.equalsIgnoreCase("Cancel")) {
			
			stoServ.save(story);
		}
		
		return "redirect:/stories/";
	}
	
	@GetMapping("/stories/modify/{id}")
	public String modify(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("tsscStory", stoServ.findById(id));
		model.addAttribute("games", gameServ.findAll());
		
		return "stories/modify";
	}
	
	@PostMapping("/stories/modify/{id}")
	public String modify(@Validated TsscStory story, BindingResult bd, @RequestParam(value = "action", required = true) String action, @PathVariable("id") long id,  Model model) {
		if(bd.hasErrors()&&!action.equalsIgnoreCase("Cancel")) {
			
			model.addAttribute("tsscStory", story);
			model.addAttribute("games", gameServ.findAll());
			
			return "topics/modify";
		}
		if(!action.equalsIgnoreCase("Cancel")) {
			
			TsscStory z = stoServ.findById(id);
			z.setShortDescription(story.getShortDescription());
			z.setPriority(story.getPriority());
			z.setBusinessValue(story.getBusinessValue());
			z.setInitialSprint(story.getInitialSprint());
			z.setTsscGame(story.getTsscGame());
			stoServ.save(z);
			
		}
		return "redirect:/stories/";
	}
	
	@GetMapping("/stories/showGame/{id}")
	public String showGame(Model model, @PathVariable("id") long id) {
		
		TsscStory z = stoServ.findById(id);
		List<TsscGame> gam = new ArrayList<>();
		gam.add(z.getTsscGame());
		model.addAttribute("games", gam);
		
		return "stories/gameStory";
	
	}
	
		
	//
	
}
