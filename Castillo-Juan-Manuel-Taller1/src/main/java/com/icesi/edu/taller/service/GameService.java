package com.icesi.edu.taller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.GameRepository;
import com.icesi.edu.taller.repository.TopicRepository;
@Service
public class GameService {

private GameRepository gameRepository;
	
private TopicRepository topicRepository;

@Autowired
public GameService(GameRepository gameRepository, TopicRepository topicRepository) {
	
	this.gameRepository = gameRepository;
	this.topicRepository = topicRepository;
	
}
	
public TsscGame findById(long id) {
	
	Optional<TsscGame> gOption = gameRepository.findById(id);
	if(gOption.isPresent()) {
		return gOption.get();
	}
	
	return null;
}
	
public TsscGame save(TsscGame game) {
		
	if(game==null)
		return null;
	if(game.getNGroups()>0 && game.getNSprints()>0 ) {
		
		if(game.getTsscTopic()==null) {	
			
			gameRepository.save(game);
			return game;
		}
		
	else{
		Optional<TsscTopic> xOption =  topicRepository.findById(game.getTsscTopic().getId());
		if(xOption.isPresent()) {
			
				game.setTsscStories(xOption.get().getTsscStories());
				game.setTsscTimecontrol(xOption.get().getTsscTimecontrols());
				for (TsscStory tOption : game.getTsscStories()) {
					tOption.setTsscGame(game);
				}
				for (TsscTimecontrol tOption : game.getTsscTimecontrols()) {
					tOption.setTsscGame(game);
				}
				
			gameRepository.save(game);
			return game;
			}
		}
	}
	return null;
}


public TsscGame modify(TsscGame game) {
	
	if(game==null)
		return null;
	if(gameRepository.findById(game.getId()).isPresent() && game.getNGroups()>0 && game.getNSprints()>0) {
			
		if(game.getTsscTopic()==null) {	
			
			gameRepository.save(game);
			return game;
		}
		else if(topicRepository.findById(game.getTsscTopic().getId()).isPresent()) {
			
			gameRepository.save(game);
			return game;
		}
	}
	return null;
}

//
}
