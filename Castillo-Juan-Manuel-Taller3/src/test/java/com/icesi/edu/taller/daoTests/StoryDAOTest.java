package com.icesi.edu.taller.daoTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
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

import com.icesi.edu.taller.model.TsscStory;

import com.icesi.edu.taller.repository.StoryDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@TestInstance(Lifecycle.PER_METHOD)
public class StoryDAOTest {
	
	@Autowired
	private StoryDAO storyDAO;
	
	
	private TsscStory story;
	
	@BeforeEach
	public void prepareCase() {
		
		for (TsscStory tOption : storyDAO.findAll()) {
			storyDAO.delete(tOption);
		}		
		
		//Story
		story = new TsscStory();
		story.setDescription("Es una bichiyal");
		story.setInitialSprint(BigDecimal.TEN);
		story.setBusinessValue(BigDecimal.TEN);
		story.setPriority(BigDecimal.TEN);
		story.setShortDescription("bichiyal");
		storyDAO.save(story);
		
		}
	
	@Test
	@DisplayName("Save a new story")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save() {
		
		assertNotNull(storyDAO);
		TsscStory target = new TsscStory();
		target.setDescription("las que no iban a salir");
		target.setInitialSprint(BigDecimal.TEN);
		target.setBusinessValue(BigDecimal.TEN);
		target.setPriority(BigDecimal.TEN);
		target.setShortDescription("YHLQMDLG");
		storyDAO.save(target);
		assertNotNull(storyDAO.findById(target.getId()));
		assertEquals(target, storyDAO.findById(target.getId()).get());
		
	}

	
	@Test
	@DisplayName("Update a story already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update() {
		
		assertNotNull(storyDAO);
		story.setDescription("OASIS updated");
		storyDAO.update(story);
		assertNotNull(storyDAO.findById(story.getId()).get());
		assertEquals(story.getDescription(), storyDAO.findById(story.getId()).get().getDescription());
		story.setDescription("YHLQMDLG updated");
		storyDAO.update(story);
		assertEquals(story.getDescription(), storyDAO.findById(story.getId()).get().getDescription());
		
	}
	
	@Test
	@DisplayName("Delete a story already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete() {
		
		assertNotNull(storyDAO);
		storyDAO.delete(story);	
		assertFalse(storyDAO.findById(story.getId()).isPresent());
		
	}
	
	
	@Test
	@DisplayName("Find a story by the id")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findById() {
		
		assertNotNull(storyDAO);
		assertNotNull(storyDAO.findById(story.getId()));
		assertEquals(story.getDescription(), storyDAO.findById(story.getId()).get().getDescription());
	}
	
	
	@Test
	@DisplayName("Find all the stories in the list")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAll() {
		
		assertNotNull(storyDAO);
		List<TsscStory> stories =  storyDAO.findAll();
		assertEquals(1, stories.size());
		assertEquals(story.getDescription(), stories.get(0).getDescription());
		
	}
	
	@Test
	@DisplayName("Find a story of the system by description")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByDescription() {
		
		assertNotNull(storyDAO);
		assertNotNull(storyDAO.findByDescription(story.getDescription()));
		assertEquals(story.getShortDescription(), storyDAO.findByDescription(story.getDescription()).getShortDescription());
		
	}

}
