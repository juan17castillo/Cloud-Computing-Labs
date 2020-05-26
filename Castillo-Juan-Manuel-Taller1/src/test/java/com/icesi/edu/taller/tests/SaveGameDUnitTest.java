package com.icesi.edu.taller.tests;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import com.icesi.edu.taller.repository.GameRepository;
import com.icesi.edu.taller.repository.StoryRepository;
import com.icesi.edu.taller.repository.TopicRepository;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;
import com.icesi.edu.taller.service.TopicService;


@SpringBootTest
@ContextConfiguration(classes = SaveGameDUnitTest.class)
@TestInstance(Lifecycle.PER_METHOD)
class SaveGameDUnitTest {
	
	@Mock
	private  TopicRepository topicRepository;
	@Mock
	private  GameRepository gameRepository;
	@Mock
	private  StoryRepository storyRepository;
	
	@InjectMocks
	private  TopicService topicService;
	@InjectMocks
	private  GameService gameService;
	@InjectMocks
	private  StoryService storyService;

	
	private TsscGame correctGame;
	
	private TsscStory correctStory;
	
	private TsscTopic correctTopic;
	
	
	
	@BeforeEach
	public  void prepareCases() {
		//Game
		correctGame = new TsscGame();
		correctGame.setId(2);
		correctGame.setName("Assasins");
		correctGame.setNGroups(9);
		correctGame.setNSprints(10);
		//Story
		correctStory = new TsscStory();
		correctStory.setId(3);
		correctStory.setDescription("Es una Bichiyal");
		correctStory.setTsscGame(correctGame);
		correctStory.setBusinessValue(BigDecimal.valueOf(50000));
		correctStory.setPriority(BigDecimal.TEN);
		correctStory.setInitialSprint(BigDecimal.ONE);
		//Topic
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setId(1);
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
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

		
//
	
}