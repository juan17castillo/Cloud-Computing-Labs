package com.icesi.edu.taller.repository;

import java.util.List;
import java.util.Optional;

import com.icesi.edu.taller.model.TsscTopic;


public interface ITopicDAO {

	public TsscTopic save(TsscTopic entity);
	
	public TsscTopic update(TsscTopic entity);
	
	public void delete(TsscTopic entity);
	
	public Optional<TsscTopic> findById(long cod);
	
	public List<TsscTopic> findAll();
	
	
}
