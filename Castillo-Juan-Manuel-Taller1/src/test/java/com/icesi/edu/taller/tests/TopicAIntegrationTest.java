package com.icesi.edu.taller.tests;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.TopicService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Taller1Application.class) 
@TestInstance(Lifecycle.PER_METHOD)
class TopicAIntegrationTest {
	
@Autowired
private  TopicService topicService;

private TsscTopic correctTopic;
	
	
@BeforeEach
public  void prepareCase() {
	
	correctTopic = new TsscTopic();
	correctTopic.setName("RPG");
	correctTopic.setId(1);
	correctTopic.setDefaultGroups(2);
	correctTopic.setDefaultSprints(2);
	topicService.save(correctTopic);
		
	}
	
	
@Test
@DisplayName("Save a valid topic")
public void saveTopic() {
	
	correctTopic.setId(2);
	assertAll(() -> assertEquals(topicService.save(correctTopic), correctTopic),
			() -> assertNotNull(topicService.findById(correctTopic.getId())));
	}
	
			
@Test
@DisplayName("Modify an existing topic for the correct case")
public void modifyExistingTopic() {
	
	TsscTopic modifiedTopic = correctTopic;
	modifiedTopic.setName("Modified");
	assertAll(() -> assertEquals(modifiedTopic, topicService.modify(modifiedTopic)),
				() -> assertEquals(modifiedTopic.getName(), topicService.findById(modifiedTopic.getId()).getName()));
		
	}
	
		
@Test
@DisplayName("Modifiy an non-existent topic")
public void modifyNonExistingTopic() {
	
	TsscTopic modifiedTopic = correctTopic;
	modifiedTopic.setId(6);;
	modifiedTopic.setDefaultGroups(0);
	modifiedTopic.setDefaultSprints(23);
	modifiedTopic.setName("The modified topic doesnt exist");
	assertAll(() -> assertNull(topicService.modify(modifiedTopic)),
				() -> assertNull(topicService.findById(modifiedTopic.getId()))
			);
		
	}


//
}