package br.com.lelak.teste.persistence;

import java.lang.reflect.Constructor;

import javax.persistence.EntityManager;

import br.com.lelak.teste.exception.CreateDAORuntimeException;

abstract public class DAOFactory<R> {
	
	private static final String PACKAGE_DAO = "dao";
	private static final String PACKAGE_DAOImpl = "daoImpl";
	
	public static <R> R  createDAO(Class<R> entityBeanClass)  {
		try {
			return create(entityBeanClass);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw new CreateDAORuntimeException();
		}
    }
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <R> R  create(Class<R> entityBeanClass) throws ReflectiveOperationException  {
		String filenameDAO = entityBeanClass.getName() + "Impl"; 
		filenameDAO = filenameDAO.replace(PACKAGE_DAO, PACKAGE_DAOImpl);
		Class classDAO = Class.forName(filenameDAO);
		Constructor constructor = classDAO.getDeclaredConstructor(EntityManager.class);
		constructor.setAccessible(true);
		return (R) constructor.newInstance(ConnectionManager.getEntityManager());
	}
	
	
}
