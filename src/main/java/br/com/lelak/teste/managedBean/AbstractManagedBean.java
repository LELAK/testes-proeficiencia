package br.com.lelak.teste.managedBean;

import java.io.Serializable;
import java.util.List;

import org.primefaces.context.RequestContext;

import br.com.lelak.teste.persistence.DAOFactory;
import br.com.lelak.teste.persistence.dao.InstrumentDAO;
import br.com.lelak.teste.persistence.dao.UserDAO;

abstract public class AbstractManagedBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	abstract public void toSave();
	abstract public void toReset();
	abstract  public List<T> getList();

	protected InstrumentDAO instrumentDAO() {
		return DAOFactory.createDAO(InstrumentDAO.class);
	}

	protected UserDAO userDAO() {
		return DAOFactory.createDAO(UserDAO.class);
	}
	
	protected void updateInstrumentView() {
		RequestContext.getCurrentInstance().update("tabView:formInstrument");
		RequestContext.getCurrentInstance().update("tabView:tableInstrument");
	}
}
