package com.icesi.edu.taller.tests;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.icesi.edu.taller.app.Taller1Application;
import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;
import com.icesi.edu.taller.service.TimecontrolService;
import com.icesi.edu.taller.service.TopicService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Taller1Application.class) 
@TestInstance(Lifecycle.PER_METHOD)
class SaveGameDIntegrationTest {
	
@Autowired
private  TopicService topicService;

@Autowired
private  GameService gameService;

@Autowired
private  StoryService storyService;

@Autowired
private  TimecontrolService timecontrolService;



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
	gameService.save(correctGame);
	//Story
	correctStory = new TsscStory();
	correctStory.setId(3);
	correctStory.setTsscGame(correctGame);
	correctStory.setBusinessValue(BigDecimal.valueOf(50000));
	correctStory.setPriority(BigDecimal.TEN);
	correctStory.setInitialSprint(BigDecimal.ONE);
	storyService.save(correctStory);
	//Topic
	correctTopic = new TsscTopic();
	correctTopic.setName("RPG");
	correctTopic.setId(1);
	correctTopic.setDefaultGroups(2);
	correctTopic.setDefaultSprints(2);
	topicService.save(correctTopic);
		
	}


@Test
@DisplayName("Save a game implementing a business logic alternative")
public void guardarJuego2() {
		
	
	//Time Controls
		
	TsscTimecontrol time1 = new TsscTimecontrol();
	TsscTimecontrol time2 = new TsscTimecontrol();
		
	//Setting times
		
	time1.setTsscTopic(correctTopic); time2.setTsscTopic(correctTopic);
	time1.setId(1); time2.setId(2);
	timecontrolService.save(time1);
	timecontrolService.save(time2);
	correctStory.setTsscTopic(correctTopic);
	storyService.save(correctStory);
		
	//Preparing ArrayLists/ adding to topicService
	
	ArrayList<TsscTimecontrol> times = new ArrayList<>(); times.add(time1); times.add(time2);
	ArrayList<TsscStory> stories = new ArrayList<>(); stories.add(correctStory);
	correctTopic.setTsscTimecontrols(times);
	correctTopic.setTsscStories(stories);
	topicService.save(correctTopic);
	correctGame.setTsscTopic(correctTopic);
		
	//Testing case
		
	TsscGame savGame = gameService.save(correctGame);
	assertEquals(correctGame, savGame);
	assertAll(() -> assertEquals(correctGame, savGame), () -> assertTrue(savGame.getTsscStories().size()==1),() -> assertTrue(savGame.getTsscTimecontrols().size()==2),
			() -> assertEquals(correctStory.getId(),savGame.getTsscStories().get(0).getId()), () -> assertEquals(time1.getId(), savGame.getTsscTimecontrols().get(0).getId()),
			() -> assertEquals(time2.getId(), savGame.getTsscTimecontrols().get(1).getId()));


	}


//

}


