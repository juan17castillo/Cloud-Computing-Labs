package com.icesi.edu.taller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.repository.GameRepository;
import com.icesi.edu.taller.repository.StoryRepository;

@Service
public class StoryService {

private StoryRepository storyRepository;

private GameRepository gameRepository;
	
@Autowired
public StoryService(StoryRepository storyRepository, GameRepository gameRepository) {
	
	this.storyRepository = storyRepository;
	this.gameRepository = gameRepository;

}


public TsscStory findById(long id) {
	Optional<TsscStory> tOption = storyRepository.findById(id);
	if(tOption.isPresent()) {
	
		return tOption.get();
	}
	return null;
}
	
public TsscStory save(TsscStory story) {
		
	if(story==null) {
		return null;
	}
	if(story.getBusinessValue().intValue() >0 && story.getInitialSprint().intValue()>0 && story.getPriority().intValue()>0) {			
		
		//story.setTsscTopic(gameRepository.findById(story.getTsscGame().getId()).get().getTsscTopic());
		
		if(story.getTsscGame()!=null && gameRepository.findById(story.getTsscGame().getId()).isPresent()) {
			
			//Modified del taller 1. ADAPTADO
			
			story.setTsscTopic(gameRepository.findById(story.getTsscGame().getId()).get().getTsscTopic());
			
			storyRepository.save(story);
			return story;
		}
	}
	
	return null;

}
	


public TsscStory modify(TsscStory story) {
		
	if(story==null) {
		return null;
	}
	if(storyRepository.findById(story.getId()).isPresent() && story.getBusinessValue().intValue() >0 && story.getInitialSprint().intValue()>0 && story.getPriority().intValue()>0) {
			
		if(story.getTsscGame()!=null && gameRepository.findById(story.getTsscGame().getId()).isPresent()) {				
				
			storyRepository.save(story);
			return story;
		}
			
	}
	return null;
}

public Iterable<TsscStory> findAll() {
	
	return storyRepository.findAll();
}


//
	
}
