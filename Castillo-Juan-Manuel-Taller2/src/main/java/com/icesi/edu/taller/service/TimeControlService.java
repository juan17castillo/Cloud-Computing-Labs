package com.icesi.edu.taller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.repository.TimeControlRepository;

@Service
public class TimeControlService {

TimeControlRepository timeControlRepository;

@Autowired
public TimeControlService(TimeControlRepository timeControlRepository) {
	
	super();
	this.timeControlRepository = timeControlRepository;
	
}
	
	
public TsscTimecontrol save(TsscTimecontrol tOption) {

	return timeControlRepository.save(tOption);
	
}
	
	
//	
}
