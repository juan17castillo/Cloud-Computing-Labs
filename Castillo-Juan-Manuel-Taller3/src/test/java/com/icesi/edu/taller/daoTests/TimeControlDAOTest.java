package com.icesi.edu.taller.daoTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
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

import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.repository.TimeControlDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@TestInstance(Lifecycle.PER_METHOD)
public class TimeControlDAOTest {
	
	@Autowired
	private TimeControlDAO timeControlDAO;
	
	private TsscTimecontrol timeControl;
	
	@BeforeEach
	public void prepareCase() {
		
		for (TsscTimecontrol tOption : timeControlDAO.findAll()) {
			timeControlDAO.delete(tOption);
		}		
	
		//TimeControl
		timeControl = new TsscTimecontrol();
		timeControl.setName("controlador");
		timeControl.setIntervalRunning(BigDecimal.TEN);
		timeControl.setLastPlayTime(LocalTime.NOON);
		timeControl.setAutostart("YES");
		timeControlDAO.save(timeControl);
		
		}
	
	@Test
	@DisplayName("Save a new timecontrol")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save() {
		
		assertNotNull(timeControlDAO);
		TsscTimecontrol target = new TsscTimecontrol();
		target.setName("hola");
		target.setIntervalRunning(BigDecimal.TEN);
		target.setLastPlayTime(LocalTime.NOON);
		target.setAutostart("YES");
		timeControlDAO.save(target);
		assertNotNull(timeControlDAO.findById(target.getId()));
		assertEquals(target, timeControlDAO.findById(target.getId()).get());
		
	}

	
	@Test
	@DisplayName("Update a timecontrol already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update() {
		
		assertNotNull(timeControlDAO);
		timeControl.setName("hola updated");
		timeControlDAO.update(timeControl);
		assertNotNull(timeControlDAO.findById(timeControl.getId()).get());
		assertEquals(timeControl.getName(), timeControlDAO.findById(timeControl.getId()).get().getName());
		
		timeControl.setName("original");
		timeControlDAO.update(timeControl);
		assertEquals(timeControl.getName(), timeControlDAO.findById(timeControl.getId()).get().getName());
		
	}
	
	@Test
	@DisplayName("Delete a timecontrol already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete() {
		
		assertNotNull(timeControlDAO);
		timeControlDAO.delete(timeControl);	
		assertFalse(timeControlDAO.findById(timeControl.getId()).isPresent());
		
	}
	
	@Test
	@DisplayName("Find a timecontrol by the id")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findById() {
		
		assertNotNull(timeControlDAO);
		assertNotNull(timeControlDAO.findById(timeControl.getId()));
		assertEquals(timeControl.getName(), timeControlDAO.findById(timeControl.getId()).get().getName());
		
	}
	
	@Test
	@DisplayName("Find all the timecontrols in the list")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAll() {
		
		assertNotNull(timeControlDAO);
		List<TsscTimecontrol> timeControls =  timeControlDAO.findAll();
		assertEquals(1, timeControls.size());
		assertEquals(timeControl.getName(), timeControls.get(0).getName());
		
	}
	

}
