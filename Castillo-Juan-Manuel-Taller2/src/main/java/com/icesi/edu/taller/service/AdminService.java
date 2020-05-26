package com.icesi.edu.taller.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.edu.taller.model.TsscAdmin;
import com.icesi.edu.taller.repository.AdminRepository;

@Service
public class AdminService {

@Autowired
AdminRepository adminRepository;
	

public TsscAdmin save(TsscAdmin admin) {
		
	return adminRepository.save(admin);
	
}


//

}
