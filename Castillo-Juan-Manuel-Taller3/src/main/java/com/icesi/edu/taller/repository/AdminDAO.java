package com.icesi.edu.taller.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.icesi.edu.taller.model.TsscAdmin;


@Repository
@Scope("singleton")
public class AdminDAO implements IAdminDAO{
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public Optional<TsscAdmin> findById(long cod) {
		
		TsscAdmin element = entityManager.find(TsscAdmin.class, cod);
		if(element!=null) {
			
			return Optional.of(element);		
			
		}
		
		return Optional.ofNullable(null);
	}

	@Override
	public List<TsscAdmin> findAll() {
		
		String consulta = "SELECT element FROM TsscAdmin element";
		
		return 	entityManager.createQuery(consulta).getResultList();	
	}

	
	
	public TsscAdmin findByUser(String username) {
		
		String consulta = "SELECT element FROM TsscAdmin element WHERE element.user = '" +username +"'";
		
		return (TsscAdmin) entityManager.createQuery(consulta).getSingleResult();
		
	}
	
	
	
	
	@Override
	public void save(TsscAdmin entity) {
		
		entityManager.persist(entity);		
		
	}

	@Override
	public void update(TsscAdmin entity) {
		
		entityManager.merge(entity);	
		
	}
	
	@Override
	public void delete(TsscAdmin entity) {
		
		entityManager.remove(entity);		
		
	}


	//
	
}
