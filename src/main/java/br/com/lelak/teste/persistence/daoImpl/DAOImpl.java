package br.com.lelak.teste.persistence.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.lelak.teste.persistence.dao.DAO;

abstract class DAOImpl<T> implements DAO<T> {
	
	protected EntityManager entityManager;
	private Class<T> entityClass;

	DAOImpl(Class<T> entityClass, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		String sql = String.format("Select entity from %s as entity", entityClass.getSimpleName());
		Query query = entityManager.createQuery(sql);
		return query.getResultList();
	}
	
	@Override
	public T findById(Long id){
		return entityManager.find(entityClass, id);
	}
	
	@Override
	public void save(T entity){
		entityManager.persist(entity);
	}
	
	@Override
	public void update(T entity){
		entityManager.merge(entity);
	}
	
	@Override
	public void remove(T entity){
		entityManager.remove(entity);
	}

}
