package com.icesi.edu.taller.tests;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
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
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Taller1Application.class) 
@TestInstance(Lifecycle.PER_METHOD)
class StoryCIntegrationTest {
	

@Autowired
private  GameService gameService;

@Autowired
private  StoryService storyService;


private TsscGame correctGame;

private TsscStory correctStory;

	
	
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
		
	}

@Test
@DisplayName("Save a story with an existing game")
public void saveStoryWithExistingGame() {
	
	correctStory.setId(1);
	assertAll(() -> assertEquals(storyService.save(correctStory), correctStory),
			() -> assertNotNull(storyService.findById(correctStory.getId())));
	
	}
	
	
@Test
@DisplayName("Save a story with a non-existing game")
public void saveStoryWithNonExistingGame() {
	
	correctGame.setId(8);
	correctStory.setTsscGame(correctGame);
	assertAll(() -> assertNull(storyService.save(correctStory)),
			() -> assertNull(gameService.findById(correctGame.getId())));
		
	
	}
	

	
@Test
@DisplayName("Modify a non-existing story")
public void modifyNonExistingStory() {
	
	TsscStory modifiedStory = correctStory;
	modifiedStory.setId(5);	
	modifiedStory.setDescription("The modified story doesnt exist");
	assertAll(() -> assertNull(storyService.modify(modifiedStory)),
			() -> assertNull(storyService.findById(correctStory.getId())));
		
	
	}
	

@Test
@DisplayName("Modify an existing story for the correct case")
public void modifyExistingStory() {

	TsscStory modifiedStory = correctStory;
	modifiedStory.setDescription("Modified");
	modifiedStory.setBusinessValue(BigDecimal.TEN);
	assertAll(() -> assertEquals(modifiedStory, storyService.modify(modifiedStory)),
			() -> assertEquals(modifiedStory.getDescription(),storyService.findById(correctStory.getId()).getDescription()));
		
	}
	
@Test
@DisplayName("Modify an existing story with an existing game")
public void modifyExistingStoryWithExistingGame() {
	
	TsscStory modifiedStory = correctStory;
	modifiedStory.setDescription("Modified");
	TsscGame modifiedGame = correctGame;
	modifiedGame.setName("Modified");
	assertAll(() -> assertEquals(modifiedStory, storyService.modify(modifiedStory)),
			() -> assertEquals(modifiedStory.getDescription(),storyService.findById(correctStory.getId()).getDescription()),
			() -> assertNotNull(gameService.findById(modifiedGame.getId())));
		
	
	}
	
@Test
@DisplayName("Modify an existing story with a non-existing game")
public void modifyExistingStoryWithNonExistingGame() {
	
	TsscStory modifiedStory = correctStory;
	TsscGame modifiedGame = correctGame;
	modifiedGame.setId(8);
	modifiedGame.setName("Modified");
	assertAll(() -> assertNull(storyService.modify(modifiedStory)),
			() -> assertNull(gameService.findById(modifiedGame.getId())));
	
	
	}
	
//
	
}