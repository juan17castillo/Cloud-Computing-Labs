package com.icesi.edu.taller.unitTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
@ContextConfiguration(classes = StoryCUnitTest.class)
@TestInstance(Lifecycle.PER_METHOD)
class StoryCUnitTest {
	
	@Mock
	private  GameDAO gameRepository;
	@Mock
	private  StoryDAO storyRepository;

	
	@InjectMocks
	private  GameService gameServ;
	@InjectMocks
	private  StoryService stoServ;

	
	
	private TsscGame correctGame;
	
	private TsscStory correctStory;


	
	@BeforeEach
	public  void prepareCase() {
		//Game
		correctGame = new TsscGame();
		correctGame.setName("Assasins");
		correctGame.setNGroups(9);
		correctGame.setNSprints(10);
		//Story
		correctStory = new TsscStory();
		correctStory.setDescription("Es una bichiyal");
		correctStory.setTsscGame(correctGame);
		correctStory.setBusinessValue(BigDecimal.valueOf(50000));
		correctStory.setPriority(BigDecimal.TEN);
		correctStory.setInitialSprint(BigDecimal.ONE);
	}
	

	
	@Test
	@DisplayName("Save a null story")
	public void saveNullStory() {
		
		assertNull(stoServ.save(null));
		verifyNoMoreInteractions(storyRepository);
		
	}
	
	
	@Test
	@DisplayName("Save a story with an existing game")
	public void saveStoryWithExistingGame() {
		
		when(storyRepository.save(correctStory)).thenReturn(correctStory);
		when(gameRepository.findById(correctGame.getId())).thenReturn(Optional.ofNullable(null));
		assertNull(stoServ.save(correctStory));
		verify(gameRepository).findById(correctGame.getId());
		verifyNoMoreInteractions(storyRepository);
		
	}
	
	
	@Test
	@DisplayName("Save an invalid story with an existing game")
	public void saveInvalidStoryWithExistingGame() {
		
		correctStory.setInitialSprint(BigDecimal.valueOf(0));
		when(storyRepository.save(correctStory)).thenReturn(correctStory);
		when(gameRepository.findById(correctGame.getId())).thenReturn(Optional.of(correctGame));
		assertNull(stoServ.save(correctStory));
		verifyNoMoreInteractions(storyRepository);
		
	}
	
	@Test
	@DisplayName("Save a story with a non-existing game")
	public void saveStoryWithNonExistingGame() {
		
		correctStory.setInitialSprint(BigDecimal.valueOf(0));
		when(storyRepository.save(correctStory)).thenReturn(correctStory);
		when(gameRepository.findById(correctGame.getId())).thenReturn(Optional.ofNullable(null));
		assertNull(stoServ.save(correctStory));
		verifyNoMoreInteractions(storyRepository);
		
	}
	
	
	@Test
	@DisplayName("Modify a non-existing story")
	public void modifyNonExistingStory() {
		
		TsscStory modifiedStory = correctStory;
		modifiedStory.setDescription("The modified story doesnt exist");
		when(storyRepository.findById(modifiedStory.getId())).thenReturn(Optional.ofNullable(null));
		assertNull(stoServ.modify(modifiedStory));
		verify(storyRepository).findById(modifiedStory.getId());
		verifyNoMoreInteractions(storyRepository); 
		
	}
	
	@Test
	@DisplayName("Modify a story with wrong values")
	public void modifyStoryWithWrongValues() {
		
		TsscStory modifiedStory = correctStory;
		Optional<TsscStory> tOption = Optional.of(modifiedStory);
		modifiedStory.setDescription("Modified");
		modifiedStory.setBusinessValue(BigDecimal.valueOf(0));;
		when(storyRepository.findById(modifiedStory.getId())).thenReturn(tOption);
		assertNull(stoServ.modify(modifiedStory));
		verify(storyRepository).findById(modifiedStory.getId());
		verifyNoMoreInteractions(storyRepository); 	
		
	}

//	@Test
//	@DisplayName("Modify a story for the correct case")
//	public void modifyExistingStory() {
//		
//		TsscStory modifiedStory = correctStory;
//		modifiedStory.setBusinessValue(BigDecimal.valueOf(1350));
//		Optional<TsscStory> tOption = Optional.of(modifiedStory);
//		when(storyRepository.findById(modifiedStory.getId())).thenReturn(tOption);
//		when(gameRepository.findById(correctGame.getId())).thenReturn(Optional.of(correctGame));
//		when(storyRepository.save(modifiedStory)).thenReturn(modifiedStory);
//		assertEquals(modifiedStory, stoServ.modify(modifiedStory));
//		verify(storyRepository).findById(modifiedStory.getId());
//		verify(gameRepository).findById(modifiedStory.getTsscGame().getId());
//		verify(storyRepository).save(modifiedStory);
//		verifyNoMoreInteractions(storyRepository); 	
//		
//	}
	
//	@Test
//	@DisplayName("Modify a story with an existing game")
//	public void modifyStoryWithExistingGame() {
//		
//		TsscStory modifiedStory = correctStory;
//		TsscGame modifiedGame = correctGame;
//		modifiedGame.setName("Modified");
//		Optional<TsscStory> tOption = Optional.of(modifiedStory);
//		when(storyRepository.findById(modifiedStory.getId())).thenReturn(tOption);
//		when(gameRepository.findById(modifiedGame.getId())).thenReturn(Optional.of(modifiedGame));
//		when(storyRepository.save(modifiedStory)).thenReturn(modifiedStory);
//		assertEquals(modifiedStory, stoServ.modify(modifiedStory));
//		verify(storyRepository).findById(modifiedStory.getId());
//		verify(gameRepository).findById(modifiedGame.getId());
//		verify(storyRepository).save(modifiedStory);
//		verifyNoMoreInteractions(storyRepository); 	
//		
//	}
	
	
	@Test
	@DisplayName("Modify a story with a non-existing game")
	public void modifyStoryWithNonExistingGame() {
		
		TsscStory modifiedStory = correctStory;
		TsscGame modifiedGame = correctGame;
		modifiedGame.setName("Modified");
		Optional<TsscStory> tOption = Optional.of(modifiedStory);
		when(storyRepository.findById(modifiedStory.getId())).thenReturn(tOption);
		when(gameRepository.findById(modifiedGame.getId())).thenReturn(Optional.ofNullable(null));
		when(storyRepository.save(modifiedStory)).thenReturn(modifiedStory);
		assertNull(stoServ.modify(modifiedStory));
		verify(gameRepository).findById(modifiedGame.getId());
		verify(storyRepository).findById(modifiedStory.getId());
		verifyNoMoreInteractions(storyRepository); 	
		
	}

}
