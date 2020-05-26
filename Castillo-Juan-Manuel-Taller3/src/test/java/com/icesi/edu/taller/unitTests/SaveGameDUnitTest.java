package com.icesi.edu.taller.unitTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.GameDAO;
import com.icesi.edu.taller.repository.StoryDAO;
import com.icesi.edu.taller.repository.TimeControlDAO;
import com.icesi.edu.taller.repository.TopicDAO;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;
import com.icesi.edu.taller.service.TopicService;


@SpringBootTest
@ContextConfiguration(classes = SaveGameDUnitTest.class)
@TestInstance(Lifecycle.PER_METHOD)
class SaveGameDUnitTest {
	
	@Mock
	private  TopicDAO topicRepository;
	@Mock
	private  GameDAO gameRepository;
	@Mock
	private  StoryDAO storyRepository;

	
	@InjectMocks
	private  TopicService topServ;
	@InjectMocks
	private  GameService gameServ;
	@InjectMocks
	private  StoryService stoServ;

	
	private TsscTopic correctTopic;
	
	private TsscGame correctGame;
	
	private TsscStory correctStory;


	
	@BeforeEach
	public  void setUp() {
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
		
		correctGame = new TsscGame();
		correctGame.setName("Assasins");
		correctGame.setNGroups(9);
		correctGame.setNSprints(10);
		
		correctStory = new TsscStory();
		correctStory.setDescription("Es una bichiyal");
		correctStory.setTsscGame(correctGame);
		correctStory.setBusinessValue(BigDecimal.valueOf(50000));
		correctStory.setPriority(BigDecimal.TEN);
		correctStory.setInitialSprint(BigDecimal.ONE);
	}
	
		
//	@Test
//	@DisplayName("Save a game implementing a business logic alternative")
//	public void guardarJuego2() {
//			
//		
//		//Time Controls
//		
//		TsscTimecontrol time1 = new TsscTimecontrol();
//		TsscTimecontrol time2 = new TsscTimecontrol();
//		
//		//Setting elements
//		
//		time1.setName("time1"); time2.setName("time2");
//		ArrayList<TsscTimecontrol> times = new ArrayList<>(); times.add(time1); times.add(time2);
//		ArrayList<TsscStory> stories = new ArrayList<>(); stories.add(correctStory);
//		correctTopic.setTsscStories(stories);
//		correctTopic.setTsscTimecontrols(times);
//		correctGame.setTsscTopic(correctTopic);
//		when(gameRepository.save(correctGame)).thenReturn(correctGame);
//		when(topicRepository.findById(correctTopic.getId())).thenReturn(Optional.of(correctTopic));
//		
//
//		//JAMÁS FUNCIONÓ :(
//		
//
//	}

	

}
