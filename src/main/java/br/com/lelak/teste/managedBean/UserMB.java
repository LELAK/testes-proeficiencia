package br.com.lelak.teste.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;

import br.com.lelak.teste.model.User;
import br.com.lelak.teste.persistence.DAOFactory;
import br.com.lelak.teste.persistence.dao.UserDAO;

@ManagedBean
@RequestScoped
public class UserMB implements Serializable {

	private static final long serialVersionUID = 924020269470990794L;
	private List<User> list;

	@ManagedProperty(value = "#{user}")
	private User userForm;
	
	public UserMB() {
		list = userDAO().findAll();
	}

	public List<User> getList() {
		return list;
	}

	public User getUserForm() {
		return userForm;
	}

	public void setUserForm(User userForm) {
		this.userForm = userForm;
	}

	public void toSave() {
		User clone = userForm.clone();
		userDAO().save(clone);
		list.add(clone);
		userForm.reset();
		onPostExecute();
	}

	public void toDelete(Long userId) {
		UserDAO dao = userDAO();
		User user = dao.findById(userId);
		list.remove(user);
		dao.remove(user);
		if(userId.equals(userForm.getId())){
			userForm.reset();
		}
		onPostExecute();
	}

	public void toEdit(Long userId) {
		User user = userDAO().findById(userId);
		userForm.clone(user);
		RequestContext.getCurrentInstance().update("tabView:formUserUpdate");
		RequestContext.getCurrentInstance().update("tabView:tableUser");
	}
	public void toUpdate() {
		userDAO().update(userForm.clone());
		userForm.reset();
		RequestContext.getCurrentInstance().update("tabView:formUserUpdate");
		RequestContext.getCurrentInstance().update("tabView:tableUser");
	}

	private UserDAO userDAO() {
		return DAOFactory.createDAO(UserDAO.class);
	}

	private void onPostExecute() {
		RequestContext.getCurrentInstance().update("tabView:formUser");
		RequestContext.getCurrentInstance().update("tabView:tableUser");
	}

}
