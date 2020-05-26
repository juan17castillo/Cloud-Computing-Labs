package com.icesi.edu.taller.service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.TopicRepository;

@Service
public class TopicService {
	
	private TopicRepository topicRepository;
	
	
@Autowired
public TopicService(TopicRepository tOption) {
	
	topicRepository = tOption;
}
	
	
public TsscTopic findById(long id) {
	
	Optional<TsscTopic> tOption = topicRepository.findById(id);
	if(tOption.isPresent()) {
		return tOption.get();
	}
	return null;
}
	

public TsscTopic save(TsscTopic topic) {
		
	if(topic==null)
		return null;
		
	if(topic.getDefaultGroups()>0 && topic.getDefaultSprints()>0 ) {
		topicRepository.save(topic);
		return topic;
			}
	
	return null;
}
	
public TsscTopic modify(TsscTopic topic) {
		
	if(topic==null)
		return null;
		
	if(topicRepository.findById(topic.getId()).isPresent() && topic.getDefaultGroups()>0 && topic.getDefaultSprints()>0) {
		topicRepository.save(topic);
		return topic;
		}
	
	return null;
	
	}
	
//
}
