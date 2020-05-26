package com.icesi.edu.taller.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.icesi.edu.taller.model.TsscTopic;


@Repository
@Scope("singleton")
public class TopicDAO implements ITopicDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Optional<TsscTopic> findById(long cod) {
		
		TsscTopic  element = entityManager.find(TsscTopic.class, cod);
		if(element!=null) {
			
			return Optional.of(element);					
		}
		
		return Optional.ofNullable(null);		
	}

	
	@Override
	public List<TsscTopic> findAll() {
		
		String consulta = "SELECT element FROM TsscTopic element";
		
		return 	entityManager.createQuery(consulta).getResultList();	
	}

	
	
	public TsscTopic findByName(String name) {
		
		String consulta = "SELECT element FROM TsscTopic element "
						+ "WHERE element.name = '" +name +"'";
		
		return (TsscTopic) entityManager.createQuery(consulta).getSingleResult();
		
	}
	
	
	
	public TsscTopic findByDescription(String desc) {
		
		String consulta = "SELECT element FROM TsscTopic element "
						+ "WHERE element.description = '" +desc +"'";
		
		return (TsscTopic) entityManager.createQuery(consulta).getSingleResult();
		
	}
	
	
	
	public List<TsscTopic> findTopicBy2A(LocalDate date) {
		
		String consulta = "SELECT element from TsscTopic element WHERE element.id IN (SELECT element2.id FROM TsscTopic element2, TsscGame element3  "
						+ " WHERE element2.id = element3.tsscTopic.id AND element3.scheduledDate = '" +date +"' ORDER BY element3.scheduledTime ASC)";	
		
		return 	entityManager.createQuery(consulta).getResultList();
		
	}
	
	
	@Override
	public TsscTopic save(TsscTopic entity) {
		entityManager.persist(entity);	
		return entity;
		
	}

	@Override
	public TsscTopic update(TsscTopic entity) {
		entityManager.merge(entity);		
		return entity;
	}

	@Override
	public void delete(TsscTopic entity) {
		entityManager.remove(entity);		
		
	}
	
	
	
}
