package com.icesi.edu.taller.integrationTests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.junit.jupiter.api.Assertions.assertAll;


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

import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.GameService;

import com.icesi.edu.taller.service.TopicService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GameBIntegrationTest.class})
@TestInstance(Lifecycle.PER_METHOD)
class GameBIntegrationTest {
	
	
	@Autowired
	private  TopicService topicService;
	@Autowired
	private  GameService gameService;



	private TsscTopic correctTopic;
	
	private TsscGame correctGame;


	
	
	@BeforeEach
	public  void prepareCase() {
		//Topic
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setDescription("Es una bichiyal");
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
		correctTopic.setGroupPrefix("SAN");
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
		
		
	}
	
	

	@Test
	@DisplayName("Save a valid game without having a topic")
	public void saveGameWithoutTopic() {
		
		correctGame = new TsscGame();
		correctGame.setName("Warzone");
		correctGame.setNGroups(9);
		correctGame.setNSprints(10);
		correctGame.setAdminPassword("456");
		correctGame.setGuestPassword("456");
		correctGame.setScheduledTime(LocalTime.NOON);
		correctGame.setScheduledDate(LocalDate.of(2020, 10, 22));
		correctGame.setStartTime(LocalTime.NOON);
		assertAll(() -> assertEquals(gameService.save(correctGame), correctGame),() -> assertNotNull(gameService.findById(correctGame.getId())));
		
	}
	
	
	@Test
	@DisplayName("Save a valid game with an existing topic")
	public void saveGameWithExistingTopic() {
		
		topicService.save(correctTopic);
		correctGame.setTsscTopic(correctTopic);
		correctGame.setName("Resident");
		assertEquals(gameService.save(correctGame), correctGame);
				
	}
	
	
	@Test
	@DisplayName("Save a valid game with a non-existing topic")
	public void saveValidGameWithNonExistingTopic() {
		
		correctGame.setTsscTopic(correctTopic);
		assertAll(() -> assertNull(gameService.save(correctGame)),() -> assertNull(gameService.findById(correctGame.getId())));
		
	}
	
	
	
	@Test
	@DisplayName("Modify an existing game for the correct case")
	public void modifyExistingGame() {
		
		gameService.save(correctGame);
		TsscGame modifiedGame = correctGame;
		modifiedGame.setName("Modified");
		modifiedGame.setNSprints(200);
		modifiedGame.setNGroups(200);
		assertAll(() -> assertEquals(modifiedGame, gameService.modify(modifiedGame)),() -> assertEquals(modifiedGame.getName(),gameService.findById(modifiedGame.getId()).getName()));
		
	}
	
	
	
	@Test
	@DisplayName("Modify an existing game for an existing topic")
	public void modifyGameWithExistingTopic() {
		
		gameService.save(correctGame);
		TsscGame modifiedGame = correctGame;
		topicService.save(correctTopic);
		modifiedGame.setTsscTopic(correctTopic);
		modifiedGame.setName("Modified");
		assertAll(() -> assertEquals(modifiedGame, gameService.modify(modifiedGame)),() -> assertEquals(correctTopic.getId(),gameService.findById(modifiedGame.getId()).getTsscTopic().getId()));		
	
	}
	
	
	
	@Test
	@DisplayName("Modify an existing game with a non-existing topic")
	public void modifyGameWithNonExistingTopic() {
		
		gameService.save(correctGame);
		TsscGame modifiedGame = correctGame;
		modifiedGame.setTsscTopic(correctTopic);
		modifiedGame.setName("Modified");
		assertAll(() -> assertNull(gameService.modify(modifiedGame)),() -> assertNull(topicService.findById(correctTopic.getId())));
		
	}
	
	
	@Test
	@DisplayName("Modify a non-existing game")
	public void modifyNonExistingGame() {
		
		TsscGame modifiedGame = new TsscGame();
		correctGame.setName("Warzone");
		correctGame.setNGroups(9);
		correctGame.setNSprints(10);
		correctGame.setAdminPassword("123");
		correctGame.setGuestPassword("123");
		correctGame.setScheduledTime(LocalTime.NOON);
		correctGame.setScheduledDate(LocalDate.of(2020, 10, 22));
		correctGame.setStartTime(LocalTime.NOON);
		modifiedGame.setName("Modified game doesnt exist");
		assertAll(() -> assertNull(gameService.modify(modifiedGame)),() -> assertNull(gameService.findById(correctGame.getId())));		
	}

	
	//
	
}
