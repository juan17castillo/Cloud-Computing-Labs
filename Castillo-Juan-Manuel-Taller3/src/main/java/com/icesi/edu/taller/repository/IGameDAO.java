package com.icesi.edu.taller.repository;

import java.util.List;
import java.util.Optional;

import com.icesi.edu.taller.model.TsscGame;



public interface IGameDAO {


	public TsscGame save(TsscGame entity);
	
	public TsscGame update(TsscGame entity);
	
	public void delete(TsscGame entity);
	
	public Optional<TsscGame> findById(long cod);
	
	public List<TsscGame> findAll();
	


}




