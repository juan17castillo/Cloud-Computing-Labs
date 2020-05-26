package com.icesi.edu.taller.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.icesi.edu.taller.model.TsscTimecontrol;



@Repository
@Scope("singleton")
public class TimeControlDAO implements ITimeControlDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Override
	public Optional<TsscTimecontrol> findById(long cod) {
		
		TsscTimecontrol element = entityManager.find(TsscTimecontrol.class, cod);
		if(element!=null) {
			
			return Optional.of(element);
			
		}
		
		return Optional.ofNullable(null);		
	}

	@Override
	public List<TsscTimecontrol> findAll() {
		
		String jpql = "SELECT element FROM TsscTimecontrol element";
		
		return 	entityManager.createQuery(jpql).getResultList();	
	}
	
	
	@Override
	public void save(TsscTimecontrol entity) {
		entityManager.persist(entity);		
		
	}

	@Override
	public void update(TsscTimecontrol entity) {
		entityManager.merge(entity);		
		
	}

	@Override
	public void delete(TsscTimecontrol entity) {
		entityManager.remove(entity);		
		
	}
	
	//

	
}
