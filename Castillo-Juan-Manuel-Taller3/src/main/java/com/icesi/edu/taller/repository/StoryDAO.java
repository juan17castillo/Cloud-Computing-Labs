package com.icesi.edu.taller.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.icesi.edu.taller.model.TsscStory;


@Repository
@Scope("singleton")
public class StoryDAO implements IStoryDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Override
	public Optional<TsscStory> findById(long cod) {
		
		TsscStory  element = entityManager.find(TsscStory.class, cod);
		if(element!=null) {
			
			return Optional.of(element);					
		}
		
		return Optional.ofNullable(null);		
	}

	
	
	@Override
	public List<TsscStory> findAll() {
		
		String consulta = "SELECT element FROM TsscStory element";
		
		return 	entityManager.createQuery(consulta).getResultList();	
	}

	
	
	public TsscStory findByDescription(String desc) {
		
		String consulta = "SELECT element FROM TsscStory element "
						+ "WHERE element.description = '" +desc +"'";
		
		return (TsscStory) entityManager.createQuery(consulta).getSingleResult();
		
	}
	
	
	
	@Override
	public TsscStory save(TsscStory entity) {
		entityManager.persist(entity);	
		return entity;
		
	}

	@Override
	public TsscStory update(TsscStory entity) {
		entityManager.merge(entity);	
		return entity;
		
	}

	@Override
	public void delete(TsscStory entity) {
		entityManager.remove(entity);		
		
	}
	
}
