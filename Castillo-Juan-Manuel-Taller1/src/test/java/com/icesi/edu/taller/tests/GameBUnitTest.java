package com.icesi.edu.taller.tests;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
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
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.GameRepository;
import com.icesi.edu.taller.repository.TopicRepository;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.TopicService;


@SpringBootTest
@ContextConfiguration(classes = GameBUnitTest.class)
@TestInstance(Lifecycle.PER_METHOD)
class GameBUnitTest {
	
	@Mock
	private  TopicRepository topicRepository;
	@Mock
	private  GameRepository gameRepository;

	
	@InjectMocks
	private  TopicService topicService;
	@InjectMocks
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
		//Topic
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setId(1);
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
	}



	@Test
	@DisplayName("Save a null game")
	public void saveNullGame() {
		
		assertNull(gameService.save(null));
		verifyNoMoreInteractions(gameRepository);
		
	}
		
		
	@Test
	@DisplayName("Save a game without a topic")
	public void saveGameWithoutTopic() {
		
		when(gameRepository.save(correctGame)).thenReturn(correctGame);
		assertEquals(gameService.save(correctGame), correctGame);
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
		assertEquals(gameService.save(correctGame), correctGame);
		verify(gameRepository).save(correctGame);
		verify(topicRepository).findById(correctTopic.getId());
		verifyNoMoreInteractions(gameRepository);
		
	}
		
	@Test
	@DisplayName("Save a game with a non-existing topic")
	public void saveGameWithNonExistingTopic() {
		
		correctTopic.setId(60);
		correctGame.setTsscTopic(correctTopic);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(Optional.ofNullable(null));
		when(gameRepository.save(correctGame)).thenReturn(correctGame);
		assertNull(gameService.save(correctGame));
		verify(topicRepository).findById(correctTopic.getId());
		verifyNoMoreInteractions(gameRepository);
		
	}
		
		
	@Test
	@DisplayName("Save an invalid game without a topic")
	public void saveInvalidGameWithoutTopic() {
		
		TsscGame notValidGame = correctGame;
		notValidGame.setId(8);
		notValidGame.setNGroups(0);
		notValidGame.setNSprints(0);
		when(gameRepository.save(notValidGame)).thenReturn(notValidGame);
		assertAll(() -> assertNull(gameService.save(notValidGame)), () -> assertNotEquals(gameService.save(notValidGame), notValidGame));
		verifyNoMoreInteractions(gameRepository); 
		
	}
		

	@Test
	@DisplayName("Save an invalid game with a topic")
	public void saveInvalidGameWithExistingTopic() {
		
		TsscGame notValidGame = correctGame;
		notValidGame.setId(8);
		notValidGame.setTsscTopic(correctTopic);
		notValidGame.setNGroups(0);
		notValidGame.setNSprints(0);
		Optional<TsscTopic> tOption = Optional.of(correctTopic);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(tOption);
		when(gameRepository.save(notValidGame)).thenReturn(notValidGame);
		assertAll(() -> assertNull(gameService.save(notValidGame)), () -> assertNotEquals(gameService.save(notValidGame), notValidGame));
		verifyNoMoreInteractions(gameRepository); 

	}
		
	@Test
	@DisplayName("Save an invalid game with a non-existing topic")
	public void saveInvalidGameWithNonExistingTopic() {
			
		TsscGame notValidGame = correctGame;
		notValidGame.setId(8);
		notValidGame.setTsscTopic(correctTopic);
		notValidGame.setNGroups(0);
		notValidGame.setNSprints(0);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(Optional.ofNullable(null));
		when(gameRepository.save(notValidGame)).thenReturn(notValidGame);
		assertAll(() -> assertNull(gameService.save(notValidGame)), () -> assertNotEquals(gameService.save(notValidGame), notValidGame));
		verifyNoMoreInteractions(gameRepository); 
		
	}
		
	@Test
	@DisplayName("Modify an existing game")
	public void modifyExistingGames() {
		
		TsscGame modifiedGame = correctGame;
		modifiedGame.setName("Modified");
		Optional<TsscGame> tOption = Optional.of(modifiedGame);
		when(gameRepository.findById(modifiedGame.getId())).thenReturn(tOption);
		when(gameRepository.save(modifiedGame)).thenReturn(modifiedGame);
		assertEquals(modifiedGame, gameService.modify(modifiedGame));
		verify(gameRepository).findById(modifiedGame.getId());
		verify(gameRepository).save(modifiedGame);
		verifyNoMoreInteractions(gameRepository); 
		
	}
		
	@Test
	@DisplayName("Modify a game with wrong values")
	public void modifyGameWithWrongValues() {
		
		TsscGame modifiedGame = correctGame;
		Optional<TsscGame> tOption = Optional.of(modifiedGame);
		modifiedGame.setName("Modified");
		modifiedGame.setNGroups(0);
		modifiedGame.setNSprints(0);
		when(gameRepository.findById(modifiedGame.getId())).thenReturn(tOption);
		assertNull(gameService.modify(modifiedGame));
		verify(gameRepository).findById(modifiedGame.getId());
		verifyNoMoreInteractions(gameRepository); 	
		
	}
		
	@Test
	@DisplayName("Modify a game with  an existing topic")
	public void modifyGameWithExistingTopic() {
		
		TsscGame modifiedGame = correctGame;
		modifiedGame.setTsscTopic(correctTopic);
		modifiedGame.setName("Modified");
		Optional<TsscGame> tOption = Optional.of(modifiedGame);
		Optional<TsscTopic> tOption2 = Optional.of(correctTopic);
		when(gameRepository.findById(modifiedGame.getId())).thenReturn(tOption);
		when(topicRepository.findById(correctTopic.getId())).thenReturn(tOption2);
		when(gameRepository.save(modifiedGame)).thenReturn(modifiedGame);
		assertEquals(modifiedGame, gameService.modify(modifiedGame));
		verify(gameRepository).findById(modifiedGame.getId());
		verify(topicRepository).findById(correctTopic.getId());
		verify(gameRepository).save(modifiedGame);
		verifyNoMoreInteractions(gameRepository); 

	}
		
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
		assertNull(gameService.modify(modifiedGame));
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
		assertNull(gameService.modify(modifiedGame));
		verify(gameRepository).findById(modifiedGame.getId());
		verifyNoMoreInteractions(gameRepository); 
		
	}

	
//
	
}