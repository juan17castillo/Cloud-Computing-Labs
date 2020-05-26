package com.icesi.edu.taller.integrationTests;


import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;
import com.icesi.edu.taller.service.TimeControlService;
import com.icesi.edu.taller.service.TopicService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SaveGameDIntegrationTest.class})
@TestInstance(Lifecycle.PER_METHOD)
class SaveGameDIntegrationTest {
	
	
	@Autowired
	private  TopicService topicService;
	@Autowired
	private  GameService gameService;
	@Autowired
	private  StoryService storyService;
	@Autowired
	private  TimeControlService timecontrolService;

	private TsscTopic correctTopic;
	
	private TsscGame correctGame;
	
	private TsscStory correctStory;

	
	
	@BeforeEach
	public  void prepareCase() {
		
		
		//Game
		correctGame = new TsscGame();
		correctGame.setName("Assasins");
		correctGame.setNGroups(9);
		correctGame.setNSprints(10);
		correctGame.setAdminPassword("123");
		correctGame.setGuestPassword("123");
		correctGame.setScheduledTime(LocalTime.NOON);
		correctGame.setScheduledDate(LocalDate.of(2020, 10, 22));
		correctGame.setStartTime(LocalTime.NOON);
		//Topic
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setDescription("ferxxo");
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
		correctTopic.setGroupPrefix("SAN");
		//Topic
		correctStory = new TsscStory();
		correctStory.setTsscGame(correctGame);
		correctStory.setDescription("Es una bihciyal");
		correctStory.setShortDescription("bichiyal");
		correctStory.setBusinessValue(BigDecimal.valueOf(50000));
		correctStory.setPriority(BigDecimal.TEN);
		correctStory.setInitialSprint(BigDecimal.ONE);
	}
	


	@Test
	@DisplayName("Save a game implementing a business logic alternative")
	public void guardarJuego2() {
		
		//prepareCase
		
		topicService.save(correctTopic);
		gameService.save(correctGame);
		correctStory.setTsscTopic(correctTopic);
		storyService.save(correctStory);
		
		//TimeControls
		
		TsscTimecontrol time1 = new TsscTimecontrol();
		TsscTimecontrol time2 = new TsscTimecontrol();
		
		//Setting times
		
		time1.setName("time1"); 
		time1.setTsscTopic(correctTopic);
		timecontrolService.save(time1);
		time2.setTsscTopic(correctTopic);
		time2.setName("time2");
		timecontrolService.save(time2);
		
		//Preparing ArrayLists/ adding to topicService
		
		TsscGame target = new TsscGame();
		target.setName("Assasins");
		target.setNGroups(9);
		target.setNSprints(10);
		target.setAdminPassword("123");
		target.setGuestPassword("123");
		target.setScheduledTime(LocalTime.NOON);
		target.setScheduledDate(LocalDate.of(2020, 10, 22));
		target.setStartTime(LocalTime.NOON);
	
		//Testing case
		
		target.setTsscTopic(correctTopic);
		TsscGame savGame = gameService.save(target);
		assertEquals(target, savGame);
		assertTrue(savGame.getTsscTimecontrols().size()>=2);
		
	}

	

}
