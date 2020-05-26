package com.icesi.edu.taller.repository;


import java.util.List;
import java.util.Optional;

import com.icesi.edu.taller.model.TsscAdmin;



public interface IAdminDAO {

	public void save(TsscAdmin entity);
	
	public void update(TsscAdmin entity);
	
	public void delete(TsscAdmin entity);
	
	public Optional<TsscAdmin> findById(long cod);
	
	public List<TsscAdmin> findAll();

	
	//
}
