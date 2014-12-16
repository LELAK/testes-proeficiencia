package br.com.lelak.teste.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;

import br.com.lelak.teste.model.User;
import br.com.lelak.teste.persistence.dao.UserDAO;
import br.com.lelak.teste.util.Validator;

@ManagedBean
@RequestScoped
public class UserMB extends AbstractManagedBean<User> {

	private static final long serialVersionUID = 924020269470990794L;

	@ManagedProperty(value = "#{user}")
	private User userForm;

	public User getUserForm() {
		return userForm;
	}

	public void setUserForm(User userForm) {
		this.userForm = userForm;
	}
	
	@Override
	public List<User> getList() {
		return userDAO().findAll();
	}

	@Override
	public void toSave() {
		if(Validator.isEmpty(userForm.getName())){
			return;
		}
		
		User clone = userForm.clone();
		if (clone.getId() == null) {
			saveUser(clone);
		} else {
			updateUser(clone);
		}
		toReset();
		updateInstrumentView();
	}

	private void updateUser(User user) {
		userDAO().update(user);
	}

	private void saveUser(User user) {
		userDAO().save(user);
	}

	@Override
	public void toReset() {
		userForm.reset();
		updateUserView();
	}

	public void toDelete(Long userId) {
		UserDAO dao = userDAO();
		User user = dao.findById(userId);
		dao.remove(user);
		if (userId.equals(userForm.getId())) {
			userForm.reset();
		}
		updateUserView();
		updateInstrumentView();
	}

	public void toEdit(Long userId) {
		User user = userDAO().findById(userId);
		userForm.clone(user);
		updateUserView();
	}

	private void updateUserView() {
		RequestContext.getCurrentInstance().update("tabView:formUser");
		RequestContext.getCurrentInstance().update("tabView:tableUser");
	}

}
