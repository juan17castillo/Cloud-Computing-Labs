package com.icesi.edu.taller.daoTests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

import com.icesi.edu.taller.model.TsscAdmin;
import com.icesi.edu.taller.repository.AdminDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@TestInstance(Lifecycle.PER_METHOD)
public class AdminDAOTest {
	
	@Autowired
	private AdminDAO adminDAO;
	
	private TsscAdmin admin;
	
	@BeforeEach
	public void prepareCase() {
		
		for (TsscAdmin tOption : adminDAO.findAll()) {
			adminDAO.delete(tOption);
		}
		
		admin = new TsscAdmin();
		admin.setUser("superadmin");
		admin.setPassword("{noop}superadmin1");
		admin.setSuperAdmin("YES");
		adminDAO.save(admin);
		
		}
	
	@Test
	@DisplayName("Saves a new user with admin rol")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save() {
		
		assertNotNull(adminDAO);
		TsscAdmin target = new TsscAdmin();
		target.setSuperAdmin("NO");
		target.setUser("admin");
		target.setPassword("{noop}admin1");
		adminDAO.save(target);
		assertNotNull(adminDAO.findById(target.getId()));
		assertEquals(target, adminDAO.findByUser(target.getUser()));
		
	}

	
	@Test
	@DisplayName("Update an admin user already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update() {
		
		assertNotNull(adminDAO);
		admin.setSuperAdmin("NO");
		adminDAO.update(admin);
		assertNotNull(adminDAO.findById(admin.getId()).get());
		assertEquals(admin.getSuperAdmin(), adminDAO.findById(admin.getId()).get().getSuperAdmin());
		admin.setSuperAdmin("YES");
		adminDAO.update(admin);
		assertEquals(admin.getSuperAdmin(), adminDAO.findById(admin.getId()).get().getSuperAdmin());
		
	}
	
	@Test
	@DisplayName("Delete an user already in the system")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete() {
		
		assertNotNull(adminDAO);
		adminDAO.delete(admin);	
		assertFalse(adminDAO.findById(admin.getId()).isPresent());
		
	}
	
	@Test
	@DisplayName("Find an user (admin) by the id")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findById() {
		
		assertNotNull(adminDAO);
		assertNotNull(adminDAO.findById(admin.getId()));
		assertEquals(admin.getSuperAdmin(), adminDAO.findById(admin.getId()).get().getSuperAdmin());
		
	}
	
	@Test
	@DisplayName("Find all the users (admin) in the list")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAll() {
		
		assertNotNull(adminDAO);
		List<TsscAdmin> admins =  adminDAO.findAll();
		assertEquals(1, admins.size());
		assertEquals(admin.getUser(), admins.get(0).getUser());
		
	}
	
	
	@Test
	@DisplayName("Find an user (admin) of the system")
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByUser() {
		
		assertNotNull(adminDAO);
		assertNotNull(adminDAO.findByUser(admin.getUser()));
		assertEquals(admin.getSuperAdmin(), adminDAO.findByUser(admin.getUser()).getSuperAdmin());
		
	}
	
	
	//

}
