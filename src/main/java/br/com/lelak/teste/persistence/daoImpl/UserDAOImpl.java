package br.com.lelak.teste.persistence.daoImpl;

import javax.persistence.EntityManager;

import br.com.lelak.teste.model.User;
import br.com.lelak.teste.persistence.dao.UserDAO;

class UserDAOImpl extends DAOImpl<User> implements UserDAO {

	UserDAOImpl(EntityManager entityManager) {
		super(User.class, entityManager);
	}

}
