package com.icesi.edu.taller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.ITopicDAO;
import com.icesi.edu.taller.repository.TopicDAO;



@Service
@Scope("singleton")
public class TopicService {
	

private ITopicDAO topicDAO;
	
	
@Autowired
public TopicService(TopicDAO tOption) {
		
	topicDAO = tOption;
}
	

@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)		
public TsscTopic findById(long id) {
		
	Optional<TsscTopic> tOption = topicDAO.findById(id);
	if(tOption.isPresent()) {
		
		return tOption.get();
	}
	return null;
}


@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public TsscTopic save(TsscTopic topic) {
	
	if(topic==null)
		return null;
	
	if(topic.getDefaultGroups()>0 && topic.getDefaultSprints()>0 ) {
		topicDAO.save(topic);
		return topic;
		
	}
	
	return null;

}


@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public TsscTopic modify(TsscTopic topic) {
	
	if(topic==null)
		return null;
	
	if(topicDAO.findById(topic.getId()).isPresent() && topic.getDefaultGroups()>0 && topic.getDefaultSprints()>0) {
		topicDAO.save(topic);
		return topic;
		}
		
	return null;
	
}

@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public void delete(TsscTopic tOption) {
	topicDAO.delete(tOption);
}
	

@Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public Iterable<TsscTopic> findAll(){
		
	return topicDAO.findAll();

}

//
	
}
