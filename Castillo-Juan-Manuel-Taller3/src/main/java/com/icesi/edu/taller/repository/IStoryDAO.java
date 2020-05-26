package com.icesi.edu.taller.repository;

import java.util.List;
import java.util.Optional;

import com.icesi.edu.taller.model.TsscStory;


public interface IStoryDAO {

	public TsscStory save(TsscStory entity);
	
	public TsscStory update(TsscStory entity);
	
	public void delete(TsscStory entity);
	
	public Optional<TsscStory> findById(long cod);
	
	public List<TsscStory> findAll();
	


//

}
