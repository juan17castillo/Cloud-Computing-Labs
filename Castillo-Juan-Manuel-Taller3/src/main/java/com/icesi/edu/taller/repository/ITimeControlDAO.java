package com.icesi.edu.taller.repository;

import java.util.List;
import java.util.Optional;

import com.icesi.edu.taller.model.TsscTimecontrol;


public interface ITimeControlDAO {

	
	public void save(TsscTimecontrol entity);
	
	public void update(TsscTimecontrol entity);
	
	public void delete(TsscTimecontrol entity);
	
	public Optional<TsscTimecontrol> findById(long cod);
	
	public List<TsscTimecontrol> findAll();


//
	
}
