package com.icesi.edu.taller.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.edu.taller.model.TsscAdmin;
import com.icesi.edu.taller.repository.IAdminDAO;

@Service
@Scope("singleton")
public class AdminService {

@Autowired
IAdminDAO adminDAO;
	
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public void save(TsscAdmin admin) {
		
	adminDAO.save(admin);
	
}


//

}
