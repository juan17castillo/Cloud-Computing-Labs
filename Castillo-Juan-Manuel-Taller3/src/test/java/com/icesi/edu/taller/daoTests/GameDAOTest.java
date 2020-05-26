package com.icesi.edu.taller.daoTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
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

import com.icesi.edu.taller.Taller3Application;
import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.GameDAO;
import com.icesi.edu.taller.repository.StoryDAO;
import com.icesi.edu.taller.repository.TimeControlDAO;
import com.icesi.edu.taller.repository.TopicDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Taller3Application.class})
@Rollback(false)
@TestInstance(Lifecycle.PER_METHOD)
public class GameDAOTest {
	
	@Autowired
	private GameDAO gameDAO;
	@Autowired
	private TopicDAO topicDAO;
	@Autowired
	private TimeControlDAO timeControlDAO;
	@Autowired
	private StoryDAO storyDAO;
	
	private TsscGame game;
	
	private TsscTopic topic;

	@BeforeEach
	public void prepareCase() {
		
		for (TsscGame tOption : gameDAO.findAll()) {
			
			gameDAO.delete(tOption);
			
		}	
		
		for(TsscTopic tOption: topicDAO.findAll()) {
			
			topicDAO.delete(tOption);;
		}
		
		
		//Topic
		topic= new TsscTopic();
		topic.setName("RPG");
		topic.setDescription("Es una bihciyal");
		topic.setDefaultGroups(2);
		topic.setDefaultSprints(2);
		topic.setGroupPrefix("SAN");
		topicDAO.save(topic);
		//Game
		game = new TsscGame();
		game.setName("Assasins");
		game.setNGroups(9);
		game.setNSprints(10);
		game.setAdminPassword("ferxxo");
		game.setGuestPassword("ferxxo");
		game.setScheduledTime(LocalTime.NOON);
		game.setScheduledDate(LocalDate.of(2020, 10, 22));
		game.setStartTime(LocalTime.NOON);
		game.setTsscTopic(topic);
		gameDAO.save(game);
		
	}
	
	@Test
	@DisplayName("Save a new game")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save() {
		assertNotNull(gameDAO);
		TsscGame target = new TsscGame();
		target.setName("Resident");
		target.setNGroups(9);
		target.setNSprints(9);
		target.setAdminPassword("pass");
		target.setGuestPassword("pass");
		target.setScheduledTime(LocalTime.NOON);
		target.setScheduledDate(LocalDate.of(2020, 10, 22));
		target.setStartTime(LocalTime.NOON);
		
		gameDAO.save(target);
		assertNotNull(gameDAO.findById(target.getId()));
		assertEquals(target, gameDAO.findById(target.getId()).get());
		
	}

	
	@Test
	@DisplayName("Update a game already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update() {
		
		assertNotNull(gameDAO);
		game.setName("Sekiro updated");
		gameDAO.update(game);
		assertNotNull(gameDAO.findById(game.getId()).get());
		assertEquals(game.getName(), gameDAO.findById(game.getId()).get().getName());
		
		game.setName("Valhalla");
		gameDAO.update(game);
		assertEquals(game.getName(), gameDAO.findById(game.getId()).get().getName());
		
	}
	
	@Test
	@DisplayName("Delete a game already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete() {
		
		assertNotNull(gameDAO);
		gameDAO.delete(game);	
		assertFalse(gameDAO.findById(game.getId()).isPresent());
		
	}
	
	@Test
	@DisplayName("Find a game by the id")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findById() {
		
		assertNotNull(gameDAO);
		assertNotNull(gameDAO.findById(game.getId()));
		assertEquals(game.getName(), gameDAO.findById(game.getId()).get().getName());
		
	}
	
	@Test
	@DisplayName("Find all the games in the list")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAll() {
		
		assertNotNull(gameDAO);
		List<TsscGame> games =  gameDAO.findAll();
		assertEquals(1, games.size());
		assertEquals(game.getName(), games.get(0).getName());
		
	}
	
	@Test
	@DisplayName("Find a game of the system by name")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByName() {
		
		assertNotNull(gameDAO);
		assertNotNull(gameDAO.findByName(game.getName()));
		assertEquals(game.getScheduledDate(), gameDAO.findByName(game.getName()).getScheduledDate());
		
	}
	
	
	@Test
	@DisplayName("Find games linked to topics in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByLinkedTopic() {
		
		assertNotNull(gameDAO);
		List<TsscGame> linkedTopics = gameDAO.findByLinkedTopic(topic.getId());
		assertNotNull(linkedTopics);
		assertNotNull(topicDAO.findById(topic.getId()).get());
		assertNotNull(gameDAO.findById(game.getId()).get());
		assertNotNull(gameDAO.findById(game.getId()).get().getTsscTopic());
		assertEquals(1, gameDAO.findByLinkedTopic(topic.getId()).size());
		
	}
	

	@Test
	@DisplayName("Find the topics and the amount of programmed games filtered by date")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByRangeofDate() {
		
		assertNotNull(gameDAO);
		assertNotNull(gameDAO.findById(game.getId()).get());
		List<TsscGame> linkedTopics = gameDAO.findByRangeofDate(LocalDate.of(2010, 10, 22), LocalDate.of(2022, 10, 22));
		assertNotNull(linkedTopics);
		assertNotNull(gameDAO.findById(game.getId()).get());
		assertEquals(game.getName(), linkedTopics.get(0).getName());
		linkedTopics = gameDAO.findByRangeofDate(LocalDate.of(2010, 10, 22), LocalDate.of(2015, 10, 22));
		assertNotNull(linkedTopics);
		assertEquals(0, linkedTopics.size());
		
	}
	
	@Test
	@DisplayName("Find the topics and the amount of programmed games filtered by date and time")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByRangeofTimeandDate() {
		
		assertNotNull(gameDAO);
		assertNotNull(gameDAO.findById(game.getId()).get());
		List<TsscGame> linkedTopics = gameDAO.findByRangeofTimeandDate(LocalDate.of(2020, 10, 22), LocalTime.MIN, LocalTime.MAX);
		assertNotNull(linkedTopics);
		assertEquals(game.getName(), linkedTopics.get(0).getName());
		//MIN = 00:00h
		//MAX = 23:59h
		linkedTopics = gameDAO.findByRangeofTimeandDate(LocalDate.of(2008, 10, 22), LocalTime.MIN, LocalTime.MAX);		
		assertNotNull(linkedTopics);
		assertEquals(0, linkedTopics.size());
		
	}
	
	@Test
	@DisplayName("Find games by date with a maximum of 9 stories or no timecontrols")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findGamesBy2B(){
		
		//Asserts
		assertNotNull(gameDAO);
		assertNotNull(gameDAO.findById(game.getId()));			
		assertNotNull(gameDAO.findGamesBy2B(LocalDate.of(2010, 10, 22)));
		assertEquals(1, gameDAO.findGamesBy2B(LocalDate.of(2010, 10, 22)).size() );	
		//New TimeControl
		TsscTimecontrol timeControl = new TsscTimecontrol();
		timeControl.setName("timeControl");
		timeControl.setTsscGame(game);
		timeControlDAO.save(timeControl);	
		//New Topic
		TsscStory story = new TsscStory();
		story.setDescription("Las que no iban a salir");;
		story.setInitialSprint(BigDecimal.TEN);
		story.setBusinessValue(BigDecimal.TEN);
		story.setPriority(BigDecimal.TEN);
		story.setShortDescription("YHLQMDLG");
		story.setTsscGame(game);
		storyDAO.save(story);
		//Asserts
		assertNotNull(timeControlDAO.findById(timeControl.getId()).get());
		assertNotNull(timeControlDAO.findById(timeControl.getId()).get().getTsscGame());
		assertEquals(1, gameDAO.findGamesBy2B(LocalDate.of(2020, 10, 22)).size() );	
	}


}
