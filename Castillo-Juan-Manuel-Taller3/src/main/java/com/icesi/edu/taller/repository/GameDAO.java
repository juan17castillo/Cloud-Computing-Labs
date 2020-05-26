package com.icesi.edu.taller.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscTopic;

@Repository
@Scope("singleton")
public class GameDAO implements IGameDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<TsscGame> findById(long cod) {
		
		TsscGame element = entityManager.find(TsscGame.class, cod);
		if(element!=null) {
			
			return Optional.of(element);					
		}
		
		return Optional.ofNullable(null);	
	}

	@Override
	public List<TsscGame> findAll() {
		
		String consulta = "SELECT element FROM TsscGame element";
		
		return 	entityManager.createQuery(consulta).getResultList();	
	}

	
	
	public TsscGame findByName(String name) {
		
		String consulta = "SELECT element FROM TsscGame element "
						+ "WHERE element.name = '" +name +"'";
		
		return (TsscGame) entityManager.createQuery(consulta).getSingleResult();
		
	}
		

	
	public List<TsscGame> findByLinkedTopic(long id) {
		String consulta = "SELECT element FROM TsscGame element "
						+ "WHERE element.tsscTopic.id = '"+id +"'";
		
		return entityManager.createQuery(consulta).getResultList();
		
	}
	
	
	
	public List<TsscGame> findByRangeofDate(LocalDate datemin, LocalDate datemax) {
		
		String consulta = "SELECT element FROM TsscGame element "
						+ "WHERE element.scheduledDate >= '" +datemin +"' AND element.scheduledDate <= '" +datemax +"' ";
		
		return entityManager.createQuery(consulta).getResultList();
		
	}
	
	public List<TsscGame> findByRangeofTimeandDate(LocalDate date, LocalTime timemin, LocalTime timemax) {
		
		String consulta = "SELECT element FROM TsscGame element "
						+ "WHERE element.scheduledDate = '" +date +"' AND element.scheduledTime <= '" +timemax +"' " +" "
						+ "AND element.scheduledTime >= '" +timemin +"'";
		
		return entityManager.createQuery(consulta).getResultList();
		
	}
	
//	public List<TsscGame> findGameBy2B(LocalDate date) {
	
//		String consulta = "SELECT element FROM TsscGame element "
//						+ "WHERE element.scheduledDate = '" +date +"'";
	
//		return entityManager.createQuery(consulta).getResultList();
	
//	}
	
	public List<TsscTopic> findGamesBy2B(LocalDate date) {
		
		String consulta = " SELECT element FROM TsscGame element "
						+ " WHERE ( element.scheduledDate = '" +date +"' AND size(element.tsscStories) < 10 ) OR NOT EXISTS ( SELECT tcs FROM TsscTimecontrol tcs WHERE tcs.tsscGame.id = element.id )";
		
		return 	entityManager.createQuery(consulta).getResultList();	
		
	}
	
	@Override
	public TsscGame save(TsscGame entity) {
	
		entityManager.persist(entity);	
		
		return entity;	
	
	}

	@Override
	public TsscGame update(TsscGame entity) {
		
		entityManager.merge(entity);	
		
		return entity;
		
	}

	@Override
	public void delete(TsscGame entity) {
		
		entityManager.remove(entity);		
		
	}

	
	
	//
	
	
}

