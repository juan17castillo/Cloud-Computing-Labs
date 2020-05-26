package com.icesi.edu.taller.tests;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.TopicService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Taller1Application.class) 
@TestInstance(Lifecycle.PER_METHOD)
class GameBIntegrationTest {
	
@Autowired
private  TopicService topicService;

@Autowired
private  GameService gameService;


private TsscGame correctGame;

private TsscTopic correctTopic;
	
	
@BeforeEach
public  void prepareCase() {
	//Game 
	correctGame = new TsscGame();
	correctGame.setId(2);
	correctGame.setName("Assasins");
	correctGame.setNGroups(9);
	correctGame.setNSprints(10);
	gameService.save(correctGame);

	//Topic
	correctTopic = new TsscTopic();
	correctTopic.setName("RPG");
	correctTopic.setId(1);
	correctTopic.setDefaultGroups(2);
	correctTopic.setDefaultSprints(2);
	topicService.save(correctTopic);
		
	}



@Test
@DisplayName("Save a valid game without having a topic")
public void saveGameWithoutTopic() {
	
	correctGame.setId(3);
	assertAll(() -> assertEquals(gameService.save(correctGame), correctGame),
				() -> assertNotNull(gameService.findById(correctGame.getId())));
		
	}
	
@Test
@DisplayName("Save a valid game with an existing topic")
public void saveGameWithExistingTopic() {
	
	correctGame.setId(4);
	correctGame.setTsscTopic(correctTopic);
	correctGame.setName("Resident");
	assertEquals(gameService.save(correctGame), correctGame);
				
}
	
@Test
@DisplayName("Save a valid game with a non-existing topic")
public void saveGameWithNotExistingTopic() {
	
	correctTopic.setId(8);
	correctGame.setId(80);
	correctGame.setTsscTopic(correctTopic);
	assertAll(() -> assertNull(gameService.save(correctGame)),
			() -> assertNull(gameService.findById(correctGame.getId())));
		
	}
	
	
@Test
@DisplayName("Modify an existing game for the correct case")
public void modifyExistingGame() {
	
	TsscGame modifiedGame = correctGame;
	modifiedGame.setName("Modified");
	modifiedGame.setNSprints(200);
	modifiedGame.setNGroups(200);
	assertAll(() -> assertEquals(modifiedGame, gameService.modify(modifiedGame)),
			() -> assertEquals(modifiedGame.getName(),gameService.findById(modifiedGame.getId()).getName()));
		
	}
	
	
@Test
@DisplayName("Modify an existing game for an existing topic")
public void modifyGameWithExistingTopic() {
	
	TsscGame modifiedGame = correctGame;
	modifiedGame.setTsscTopic(correctTopic);
	modifiedGame.setName("Modified");
	assertAll(() -> assertEquals(modifiedGame, gameService.modify(modifiedGame)),
			() -> assertEquals(correctTopic.getId(),gameService.findById(modifiedGame.getId()).getTsscTopic().getId()));		
	
	}
	
@Test
@DisplayName("Modify an existing game with a non-existing topic")
public void modifyGameWithNonExistingTopic() {
	
	correctTopic.setId(8);
	TsscGame modifiedGame = correctGame;
	modifiedGame.setTsscTopic(correctTopic);
	modifiedGame.setName("Modified");
	assertAll(() -> assertNull(gameService.modify(modifiedGame)),
			() -> assertNull(topicService.findById(correctTopic.getId())));
	
	}
	
	
@Test
@DisplayName("Modify a non-existing game")
public void modifyNonExistingGame() {
	
	TsscGame modifiedGame = correctGame;
	modifiedGame.setId(5);	
	modifiedGame.setName("The modified doesnt exist");
	assertAll(() -> assertNull(gameService.modify(modifiedGame)),
			() -> assertNull(gameService.findById(correctGame.getId())));		
	
	}

//

}
	