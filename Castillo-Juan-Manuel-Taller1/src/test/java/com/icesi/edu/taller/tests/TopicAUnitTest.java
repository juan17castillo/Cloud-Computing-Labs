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
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.TopicRepository;
import com.icesi.edu.taller.service.TopicService;


@SpringBootTest
@ContextConfiguration(classes = TopicAUnitTest.class)
@TestInstance(Lifecycle.PER_METHOD)
class TopicAUnitTest {
	
	@Mock
	private  TopicRepository topicRepository;
	
	@InjectMocks
	private  TopicService topicService;
	
	private TsscTopic correctTopic;
	
	
	
	@BeforeEach
	public  void prepareCase() {
		
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setId(1);
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
	}
	


	@Test
	@DisplayName("Save a null topic")
	public void saveNullTopic() {
		
		assertNull(topicService.save(null));
		verifyNoMoreInteractions(topicRepository);

		}
		
		
	@Test
	@DisplayName("Save a valid topic")
	public void saveValidTopic() {
		
		when(topicRepository.save(correctTopic)).thenReturn(correctTopic);
		assertEquals(topicService.save(correctTopic), correctTopic);
		verify(topicRepository).save(correctTopic);
		verifyNoMoreInteractions(topicRepository);
		
	}
		
	@Test
	@DisplayName("Save topic without groups")
	public void saveTopicWithoutGroups() {
		
		TsscTopic invalidTopic = correctTopic;
		invalidTopic.setDefaultGroups(0);
		when(topicRepository.save(invalidTopic)).thenReturn(invalidTopic);
		assertAll(() -> assertNull(topicService.save(invalidTopic)), () -> assertNotEquals(topicService.save(invalidTopic), invalidTopic));
		verifyNoMoreInteractions(topicRepository); 
		
		
	}
		
	@Test
	@DisplayName("Save a topic without sprints")
	public void saveTopicWithoutSprints() {
		
		TsscTopic invalidTopic = correctTopic;
		invalidTopic.setDefaultSprints(0);
		when(topicRepository.save(invalidTopic)).thenReturn(invalidTopic);
		assertAll(() -> assertNull(topicService.save(invalidTopic)), () -> assertNotEquals(topicService.save(invalidTopic), invalidTopic));
			verifyNoMoreInteractions(topicRepository); 
		
	}
		
	@Test
	@DisplayName("Save a topic without groups and sprints")
	public void saveTopicWithoutGroupsAndSprints() {
		
		TsscTopic invalidTopic = correctTopic;
		invalidTopic.setDefaultGroups(0);
		invalidTopic.setDefaultSprints(0);
		when(topicRepository.save(invalidTopic)).thenReturn(invalidTopic);
		assertAll(() -> assertNull(topicService.save(invalidTopic)), () -> assertNotEquals(topicService.save(invalidTopic), invalidTopic));
		verifyNoMoreInteractions(topicRepository); 

	}
		
		
	@Test
	@DisplayName("Modify a topic with for the correct case")
	public void modifyTopic() {
		
		TsscTopic modifiedTopic = correctTopic;
		modifiedTopic.setName("Modified");
		Optional<TsscTopic> tOption = Optional.of(modifiedTopic);
		when(topicRepository.findById(modifiedTopic.getId())).thenReturn(tOption);
		when(topicRepository.save(modifiedTopic)).thenReturn(modifiedTopic);
		assertEquals(modifiedTopic, topicService.modify(modifiedTopic));
		verify(topicRepository).findById(modifiedTopic.getId());
		verify(topicRepository).save(modifiedTopic);
		verifyNoMoreInteractions(topicRepository); 
		
	}
		
	@Test
	@DisplayName("Modify an existing topic with wrong values")
	public void modifyExistingTopicWithWrongtValues() {
		
		TsscTopic modifiedTopic = correctTopic;
		Optional<TsscTopic> tOption = Optional.of(modifiedTopic);
		modifiedTopic.setName("Modified");
		modifiedTopic.setDefaultGroups(0);
		modifiedTopic.setDefaultSprints(0);
		when(topicRepository.findById(modifiedTopic.getId())).thenReturn(tOption);
		assertNull(topicService.modify(modifiedTopic));
		verify(topicRepository).findById(modifiedTopic.getId());
		verifyNoMoreInteractions(topicRepository); 	
		
	}
		
		
	@Test
	@DisplayName("Modify a non-existing topic")
	public void modifyNonExistingTopic() {
		
		TsscTopic modifiedTopic = correctTopic;
		modifiedTopic.setId(8);;
		modifiedTopic.setDefaultGroups(0);
		modifiedTopic.setDefaultSprints(0);
		modifiedTopic.setName("Edited are not existing");
		when(topicRepository.findById(modifiedTopic.getId())).thenReturn(Optional.ofNullable(null));
		assertNull(topicService.modify(modifiedTopic));
		verify(topicRepository).findById(modifiedTopic.getId());
		verifyNoMoreInteractions(topicRepository); 
		
	}

//

}
