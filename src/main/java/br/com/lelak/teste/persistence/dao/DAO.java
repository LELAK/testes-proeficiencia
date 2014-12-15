package br.com.lelak.teste.persistence.dao;

import java.util.List;

public interface DAO<T> {

	List<T> findAll();
	T findById(Long id);
	void save(T entity);
	void update(T entity);
	void remove(T entity);
}
