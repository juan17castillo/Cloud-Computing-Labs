package com.icesi.edu.taller.daoTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.GameDAO;
import com.icesi.edu.taller.repository.TopicDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@TestInstance(Lifecycle.PER_METHOD)
public class TopicDAOTest {
	
	@Autowired
	private TopicDAO topicDAO;
	@Autowired
	private GameDAO gameDAO;
	
	private TsscGame game;
	
	private TsscTopic topic;
	
	@BeforeEach
	public void prepareCase() {
		
		for (TsscGame tOption : gameDAO.findAll()) {
			
			gameDAO.delete(tOption);
		}
		
		for (TsscTopic tOption : topicDAO.findAll()) {
			
			topicDAO.delete(tOption);
		}		
		
		//Topic
		topic = new TsscTopic();
		topic.setName("RPG");
		topic.setDescription("Es una bichiyal");
		topic.setDefaultGroups(9);
		topic.setDefaultSprints(10);
		topic.setGroupPrefix("SAN");
		topicDAO.save(topic);
		
	}
	
	@Test
	@DisplayName("Save a new topic")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save() {
		
		assertNotNull(topicDAO);
		TsscTopic target = new TsscTopic();
		target.setName("Resident");
		target.setDescription("Survival horror");
		target.setDefaultGroups(9);
		target.setDefaultSprints(10);
		target.setGroupPrefix("NON");
		topicDAO.save(target);
		assertNotNull(topicDAO.findById(target.getId()));
		assertEquals(target, topicDAO.findById(target.getId()).get());
		topicDAO.delete(target);
		
	}

	
	@Test
	@DisplayName("Update a topic already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update() {
		
		assertNotNull(topicDAO);
		topic.setDescription("RPG updated");
		topicDAO.update(topic);
		assertNotNull(topicDAO.findById(topic.getId()).get());
		assertEquals(topic.getName(), topicDAO.findById(topic.getId()).get().getName());
		topic.setDescription("SAN");
		topicDAO.update(topic);
		assertEquals(topic.getName(), topicDAO.findById(topic.getId()).get().getName());
		
	}
	
	@Test
	@DisplayName("Delete a topic already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete() {
		
		assertNotNull(topicDAO);
		topicDAO.delete(topic);	
		assertFalse(topicDAO.findById(topic.getId()).isPresent());
		
	}
	
	@Test
	@DisplayName("Find a topic by the id")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findById() {
		
		assertNotNull(topicDAO);
		assertNotNull(topicDAO.findById(topic.getId()));
		assertEquals(topic.getName(), topicDAO.findById(topic.getId()).get().getName());
		
	}
	
	@Test
	@DisplayName("Find all the topics in the list")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAllTest() {
		
		assertNotNull(topicDAO);
		List<TsscTopic> topics =  topicDAO.findAll();
		assertEquals(1, topics.size());
		assertEquals(topic.getName(), topics.get(0).getName());
		
	}
	
	@Test
	@DisplayName("Find all the timecontrols by the description")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByDescription() {
		
		assertNotNull(topicDAO);
		assertNotNull(topicDAO.findByDescription(topic.getDescription()));
		assertEquals(topic.getName(), topicDAO.findByDescription(topic.getDescription()).getName());
		
	}
	
	@Test
	@DisplayName("Find the games with less than 10 stories associated to a specific date")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findTopicBy2A() {
		
		//Asserts
		assertNotNull(topicDAO);
		assertNotNull(topicDAO.findById(topic.getId()));	
		//Game
		game = new TsscGame();
		game.setName("Assasins");
		game.setNGroups(2);
		game.setNSprints(2);
		game.setAdminPassword("ferxxo");
		game.setGuestPassword("ferxxo");
		game.setScheduledTime(LocalTime.NOON);
		game.setScheduledDate(LocalDate.of(2020, 10, 22));
		game.setStartTime(LocalTime.NOON);
		game.setTsscTopic(topic);
		gameDAO.save(game);
		//Asserts
		assertNotNull(gameDAO.findById(game.getId()));
		assertNotNull(gameDAO.findById(game.getId()).get().getTsscTopic());
		assertEquals(topic.getId(), gameDAO.findById(game.getId()).get().getTsscTopic().getId());
		//Asserts
		assertNotNull(topicDAO.findById(topic.getId()).get());
		assertNotNull(gameDAO.findById(game.getId()).get());
		assertNotNull(topicDAO.findTopicBy2A(LocalDate.of(2020, 10, 22)));
		assertEquals(1, topicDAO.findTopicBy2A(LocalDate.of(2020, 10, 22)).size() );	
		
	}


}
