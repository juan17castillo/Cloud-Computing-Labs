package com.icesi.edu.taller.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.GameDAO;
import com.icesi.edu.taller.repository.IGameDAO;
import com.icesi.edu.taller.repository.IStoryDAO;
import com.icesi.edu.taller.repository.ITimeControlDAO;
import com.icesi.edu.taller.repository.ITopicDAO;
import com.icesi.edu.taller.repository.StoryDAO;
import com.icesi.edu.taller.repository.TimeControlDAO;
import com.icesi.edu.taller.repository.TopicDAO;

@Service
public class GameService {

private IGameDAO gameDAO;
	
private ITopicDAO topicDAO;
	
private IStoryDAO storyDAO;

private ITimeControlDAO timeControlDAO;


@Autowired
public GameService(GameDAO gameDAO, TopicDAO topicDAO, StoryDAO storyDAO, TimeControlDAO timeControlDAO) {
		
	this.gameDAO = gameDAO;
	this.topicDAO = topicDAO;
	this.storyDAO = storyDAO;
	this.timeControlDAO = timeControlDAO;
		
	
}
	

@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public TsscGame findById(long id) {
	
	Optional<TsscGame> gOption = gameDAO.findById(id);
	if(gOption.isPresent()) {
		return gOption.get();
	}
	
	return null;
	
}
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)	
public TsscGame save(TsscGame game) {
		
	if(game==null)
		return null;
	if(game.getNGroups()>0 && game.getNSprints()>0 ) {
		
		if(game.getTsscTopic()==null) {	
			
			gameDAO.save(game);
			return game;
		}
		
		else{
			Optional<TsscTopic> xOption =  topicDAO.findById(game.getTsscTopic().getId());
			if(xOption.isPresent()) {
				
//				game.setTsscStories(xOption.get().getTsscStories());
//				game.setTsscTimecontrol(xOption.get().getTsscTimecontrols());
//				for (TsscStory tOption : game.getTsscStories()) {
//					tOption.setTsscGame(game);
//				}
//				for (TsscTimecontrol tOption : game.getTsscTimecontrols()) {
//					tOption.setTsscGame(game);
//				}
					
				gameDAO.save(game);
				
				ArrayList<TsscStory> stories = new ArrayList<TsscStory>();
				ArrayList<TsscTimecontrol> timecontrols = new ArrayList<TsscTimecontrol>();
				
				if(xOption.get().getTsscStories() != null) {
				
				for (TsscStory tOption : xOption.get().getTsscStories()) {
					
					//Modified del taller 1. ADAPTADO
					
					TsscStory storyOption= new TsscStory();
					
					storyOption.setAltDescripton(tOption.getAltDescripton());
					storyOption.setBusinessValue(tOption.getBusinessValue());
					storyOption.setDescription(tOption.getDescription());
					storyOption.setInitialSprint(tOption.getInitialSprint());
					storyOption.setPriority(tOption.getPriority());
					storyOption.setShortDescription(tOption.getShortDescription());							
					storyOption.setNumber(tOption.getNumber());
					storyOption.setTsscTopic(tOption.getTsscTopic());
					storyOption.setTsscGame(game);
					
					stories.add(storyOption);
					storyDAO.save(storyOption);
			
				}
				
			}
			
				if(xOption.get().getTsscTimecontrols() != null) {
					//Segundo for Timecontrol
					
				for (TsscTimecontrol tOption : xOption.get().getTsscTimecontrols()) {			
						
					TsscTimecontrol storyOption = new TsscTimecontrol();
					
					storyOption.setName(tOption.getName());
					storyOption.setTimeInterval(tOption.getTimeInterval());
					storyOption.setName(tOption.getName());
					storyOption.setAutostart(tOption.getAutostart());
					storyOption.setIntervalRunning(tOption.getIntervalRunning());
					storyOption.setTsscGame(game);
					
					timecontrols.add(storyOption);
					timeControlDAO.save(storyOption);
						

					
					//storyRepository.save(storyOption);
					}
				}
						
			
				
			game.setTsscStories(stories);
			game.setTsscTimecontrols(timecontrols);
				
				return game;
				
			}
		
		}
		
	}
	
	return null;
	
}


@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public TsscGame modify(TsscGame game) {
	if(game==null)
		return null;
	if(gameDAO.findById(game.getId()).isPresent() && game.getNGroups()>0 && game.getNSprints()>0) {
		
		if(game.getTsscTopic()==null) {		
			
			gameDAO.update(game);
			return game;
		}
		else if(topicDAO.findById(game.getTsscTopic().getId()).isPresent()) {
			
			gameDAO.update(game);
			return game;
		}
	}
	return null;
}


@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public void delete(TsscGame tOption) {
	gameDAO.delete(tOption);
}



@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public Iterable<TsscGame> findAll() {
	
	return gameDAO.findAll();

}
	
	
//

}

