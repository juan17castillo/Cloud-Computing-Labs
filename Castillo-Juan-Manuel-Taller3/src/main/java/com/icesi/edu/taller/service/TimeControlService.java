package com.icesi.edu.taller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.repository.ITimeControlDAO;
import com.icesi.edu.taller.repository.TimeControlDAO;


@Service
@Scope("singleton")
public class TimeControlService {

ITimeControlDAO timeControlDAO;

@Autowired
public TimeControlService(TimeControlDAO timeControlDAO) {
	
	super();
	this.timeControlDAO = timeControlDAO;
	
}
	
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)	
public void save(TsscTimecontrol tOption) {

	timeControlDAO.save(tOption);
	
}
	
	
//	
}
