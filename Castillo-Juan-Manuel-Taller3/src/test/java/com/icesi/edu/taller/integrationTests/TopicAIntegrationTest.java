package com.icesi.edu.taller.integrationTests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.icesi.edu.taller.model.TsscTopic;

import com.icesi.edu.taller.service.TopicService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TopicAIntegrationTest.class})
@TestInstance(Lifecycle.PER_METHOD)
class TopicAIntegrationTest {
	
	
	@Autowired
	private  TopicService topicService;


	private TsscTopic correctTopic;


	
	
	@BeforeEach
	public  void prepareCase() {
		
		correctTopic = new TsscTopic();
		correctTopic.setName("RPG");
		correctTopic.setDescription("ferxxo");
		correctTopic.setDefaultGroups(2);
		correctTopic.setDefaultSprints(2);
		correctTopic.setGroupPrefix("SAN");
		
		
	}
	

	@Test
	@DisplayName("Save a valid topic")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveTopic() {
		
		assertAll(() -> assertEquals(topicService.save(correctTopic), correctTopic),() -> assertNotNull(topicService.findById(correctTopic.getId())));
		
	}
	
			
	@Test
	@DisplayName("Modify an existing topic for the correct case")
	public void modifyExistingTopic() {
		
		topicService.save(correctTopic);
		TsscTopic modifiedTopic = correctTopic;
		modifiedTopic.setName("Modified");
		assertAll(() -> assertEquals(modifiedTopic, topicService.modify(modifiedTopic)),() -> assertEquals(modifiedTopic.getName(), topicService.findById(modifiedTopic.getId()).getName()));
		
	}
	
	
	
	@Test
	@DisplayName("Modifiy an non-existent topic")
	public void modifyNonExistingTopic() {
		
		TsscTopic modifiedTopic = correctTopic;
		modifiedTopic.setDefaultGroups(0);
		modifiedTopic.setDefaultSprints(23);
		modifiedTopic.setName("Modified topic doesnt exist");
		assertAll(() -> assertNull(topicService.modify(modifiedTopic)),() -> assertNull(topicService.findById(modifiedTopic.getId())));
		
	}
	
	
	//
}