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
@ContextConfiguration(classes = GameBUnitTest.class)
@TestInstance(Lifecycle.PER_METHOD)
class GameBUnitTest {
	
	@Mock
	private  TopicDAO topicRepository;
	@Mock
	private  GameDAO gameRepository;

	
	@InjectMocks
	private  TopicService topServ;
	@InjectMocks
	private  GameService gameServ;


	
	private TsscTopic correctTopic;
	
	private TsscGame correctGame;
	


	
	@BeforeEach
	public  void prepareCase() {
		//Topic
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
		//Game
		correctGame = new TsscGame();
		correctGame.setName("Assasins");
		correctGame.setNGroups(9);
		correctGame.setNSprints(10);
		
		
	}
	
	
	
	


	@Test
	@DisplayName("Save a null game")
	public void saveNullGame() {
		
		assertNull(gameServ.save(null));
		verifyNoMoreInteractions(gameRepository);
		
	}
	
	
	@Test
	@DisplayName("Save a game without a topic")
	public void saveGameWithoutTopic() {
		
		when(gameRepository.save(correctGame)).thenReturn(correctGame);
		assertEquals(gameServ.save(correctGame), correctGame);
		verify(gameRepository).save(correctGame);
		verifyNoMoreInteractions(gameRepository);
		
	}
	
	@Test
	@DisplayName("Save a game with an existing topic")
	public void saveGameWithExistingTopic() {
		
		correctGame.setTsscTopic(correctTopic);
		Optional<TsscTopic> tOption = Optional.of(correctTopic);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(tOption);
		when(gameRepository.save(correctGame)).thenReturn(correctGame);
		assertEquals(gameServ.save(correctGame), correctGame);
		verify(gameRepository).save(correctGame);
		verify(topicRepository).findById(correctTopic.getId());
		verifyNoMoreInteractions(gameRepository);
		
	}
	
	@Test
	@DisplayName("Save a game with a non-existing topic")
	public void saveGameWithNonExistingTopic() {
		
		correctGame.setTsscTopic(correctTopic);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(Optional.ofNullable(null));
		when(gameRepository.save(correctGame)).thenReturn(correctGame);
		assertNull(gameServ.save(correctGame));
		verify(topicRepository).findById(correctTopic.getId());
		verifyNoMoreInteractions(gameRepository);
		
	}
	
	
	@Test
	@DisplayName("Save an invalid game without a topic")
	public void saveInvalidGameWithoutTopic() {
		
		TsscGame invalidGame = correctGame;
		invalidGame.setId(8);
		invalidGame.setNGroups(0);
		invalidGame.setNSprints(0);
		when(gameRepository.save(invalidGame)).thenReturn(invalidGame);
		assertAll(() -> assertNull(gameServ.save(invalidGame)), () -> assertNotEquals(gameServ.save(invalidGame), invalidGame));
		verifyNoMoreInteractions(gameRepository); 
		
	}
	
	@Test
	@DisplayName("Save an invalid game with a topic")
	public void saveInvalidGameWithExistingTopic() {
		
		TsscGame invalidGame = correctGame;
		invalidGame.setId(8);
		invalidGame.setTsscTopic(correctTopic);
		invalidGame.setNGroups(0);
		invalidGame.setNSprints(0);
		Optional<TsscTopic> tOption = Optional.of(correctTopic);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(tOption);
		when(gameRepository.save(invalidGame)).thenReturn(invalidGame);
		assertAll(() -> assertNull(gameServ.save(invalidGame)), () -> assertNotEquals(gameServ.save(invalidGame), invalidGame));
		verifyNoMoreInteractions(gameRepository);
		
	}
	
	@Test
	@DisplayName("Save an invalid game with a non-existing topic")
	public void saveInvalidGameWithNonExistingTopic() {
		
		TsscGame invalidGame = correctGame;
		invalidGame.setId(8);
		invalidGame.setTsscTopic(correctTopic);
		invalidGame.setNGroups(0);
		invalidGame.setNSprints(0);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(Optional.ofNullable(null));
		when(gameRepository.save(invalidGame)).thenReturn(invalidGame);
		assertAll(() -> assertNull(gameServ.save(invalidGame)), () -> assertNotEquals(gameServ.save(invalidGame), invalidGame));
		verifyNoMoreInteractions(gameRepository); 
		
	}
	
//	@Test
//	@DisplayName("Modify an existing game")
//	public void modifyExistingGame() {
//		
//		TsscGame modifiedGame = correctGame;
//		modifiedGame.setName("Modified");
//		Optional<TsscGame> tOption = Optional.of(modifiedGame);
//		when(gameRepository.findById(modifiedGame.getId())).thenReturn(tOption);
//		when(gameRepository.save(modifiedGame)).thenReturn(modifiedGame);
//		assertEquals(modifiedGame, gameServ.modify(modifiedGame));
//		verify(gameRepository).findById(modifiedGame.getId());
//		verify(gameRepository).save(modifiedGame);
//		verifyNoMoreInteractions(gameRepository); 
//		
//	}
	
	@Test
	@DisplayName("Modify a game with wrong values")
	public void modifyGameWithWrongValues() {
		
		TsscGame modifiedGame = correctGame;
		Optional<TsscGame> tOption = Optional.of(modifiedGame);
		modifiedGame.setName("Modified");
		modifiedGame.setNGroups(0);
		modifiedGame.setNSprints(0);
		when(gameRepository.findById(modifiedGame.getId())).thenReturn(tOption);
		assertNull(gameServ.modify(modifiedGame));
		verify(gameRepository).findById(modifiedGame.getId());
		verifyNoMoreInteractions(gameRepository); 	
		
	}
	
//	@Test
//	@DisplayName("Modify a game with  an existing topic")
//	public void modifyGameWithExistingTopic() {
//		
//		TsscGame modifiedGame = correctGame;
//		modifiedGame.setTsscTopic(correctTopic);
//		modifiedGame.setName("Modified");
//		Optional<TsscGame> tOption = Optional.of(modifiedGame);
//		Optional<TsscTopic> tOption2 = Optional.of(correctTopic);
//		when(gameRepository.findById(modifiedGame.getId())).thenReturn(tOption);
//		when(topicRepository.findById(correctTopic.getId())).thenReturn(tOption2);
//		when(gameRepository.save(modifiedGame)).thenReturn(modifiedGame);
//		assertEquals(modifiedGame, gameServ.modify(modifiedGame));
//		verify(gameRepository).findById(modifiedGame.getId());
//		verify(topicRepository).findById(correctTopic.getId());
//		verify(gameRepository).save(modifiedGame);
//		verifyNoMoreInteractions(gameRepository); 
//		
//	}
	
	@Test
	@DisplayName("Modify a game with  a non-existing topic")
	public void modifyGameWithNonExistingTopic() {
		
		TsscGame modifiedGame = correctGame;
		modifiedGame.setTsscTopic(correctTopic);
		modifiedGame.setName("Modified");
		Optional<TsscGame> tOption = Optional.of(modifiedGame);
		when(gameRepository.findById(modifiedGame.getId())).thenReturn(tOption);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(Optional.ofNullable(null));
		when(gameRepository.save(modifiedGame)).thenReturn(modifiedGame);
		assertNull(gameServ.modify(modifiedGame));
		verify(gameRepository).findById(modifiedGame.getId());
		verify(topicRepository).findById(correctTopic.getId());
		verifyNoMoreInteractions(gameRepository); 
		
	}
	
	
	@Test
	@DisplayName("Modify a non-existing game")
	public void modifyNonExistingGame() {
		TsscGame modifiedGame = correctGame;
		modifiedGame.setId(8);	
		modifiedGame.setName("Modified game doesnt exist");
		when(gameRepository.findById(modifiedGame.getId())).thenReturn(Optional.ofNullable(null));
		assertNull(gameServ.modify(modifiedGame));
		verify(gameRepository).findById(modifiedGame.getId());
		verifyNoMoreInteractions(gameRepository);
		
	}

	
//
	

}
