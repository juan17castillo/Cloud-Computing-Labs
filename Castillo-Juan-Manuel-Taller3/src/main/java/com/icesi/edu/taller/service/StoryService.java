package com.icesi.edu.taller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.repository.GameDAO;
import com.icesi.edu.taller.repository.IGameDAO;
import com.icesi.edu.taller.repository.IStoryDAO;
import com.icesi.edu.taller.repository.StoryDAO;


@Service
@Scope("singleton")
public class StoryService {

private IStoryDAO storyDAO;

private IGameDAO gameDAO;
	
@Autowired
public StoryService(StoryDAO storyDAO, GameDAO gameDAO) {
	
	this.storyDAO = storyDAO;
	this.gameDAO= gameDAO;

}


@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public TsscStory findById(long id) {
	Optional<TsscStory> tOption = storyDAO.findById(id);
	if(tOption.isPresent()) {
	
		return tOption.get();
	}
	return null;
}


@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public TsscStory save(TsscStory story) {
		
	if(story==null) {
		return null;
	}
	if(story.getBusinessValue().intValue() >0 && story.getInitialSprint().intValue()>0 && story.getPriority().intValue()>0) {			
		
		//story.setTsscTopic(gameRepository.findById(story.getTsscGame().getId()).get().getTsscTopic());
		
		if(story.getTsscGame()!=null && gameDAO.findById(story.getTsscGame().getId()).isPresent()) {
			
			//Modified del taller 1. ADAPTADO
			
			story.setTsscTopic(gameDAO.findById(story.getTsscGame().getId()).get().getTsscTopic());
			
			storyDAO.save(story);
			return story;
		}
	}
	
	return null;

}
	

@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public TsscStory modify(TsscStory story) {
		
	if(story==null) {
		return null;
	}
	if(storyDAO.findById(story.getId()).isPresent() && story.getBusinessValue().intValue() >0 && story.getInitialSprint().intValue()>0 && story.getPriority().intValue()>0) {
			
		if(story.getTsscGame()!=null && gameDAO.findById(story.getTsscGame().getId()).isPresent()) {				
				
			storyDAO.update(story);
			return story;
		}
			
	}
	return null;
}


@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public void delete(TsscStory tOption) {
	
	storyDAO.delete(tOption);
}


@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public Iterable<TsscStory> findAll() {
	
	return storyDAO.findAll();
}


//
	
}
