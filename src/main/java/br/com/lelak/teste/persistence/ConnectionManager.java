package br.com.lelak.teste.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ConnectionManager {
	
	private static final String PERSISTENCE_UNIT = "lelakPersistenceUnit";
	private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();

	public static EntityManager getEntityManager(){
		EntityManager entityManager = threadLocal.get();
		if(entityManager == null || !entityManager.isOpen()){
			entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
			threadLocal.set(entityManager);
		}
		return entityManager;
	}
	
	public static void beginTransaction(){
		getEntityManager().getTransaction().begin();
		System.out.println("beginTransaction");
	}
	
	public static void close(){
		EntityManager entityManager = threadLocal.get();
		if(entityManager == null){
			return;
		}
		if(entityManager.isOpen()){
			entityManager.close();
		}
		threadLocal.remove();
		System.out.println("close");
	}

	public static void commit() {
		if(hasActivatedTransaction()){
			getEntityManager().getTransaction().commit();
		}
		System.out.println("commit");
	}

	public static void rollback() {
		if(hasActivatedTransaction()){
			getEntityManager().getTransaction().rollback();
		}
		System.out.println("rollback");
	}
	
	private static boolean hasActivatedTransaction(){
		return getEntityManager().getTransaction().isActive();
	}
}
