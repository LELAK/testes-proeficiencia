package br.com.lelak.teste.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.lelak.teste.exception.CloneNotSupportedRuntimeException;

@ManagedBean
@ViewScoped
@Entity
public class User implements Cloneable, Serializable, EntityBean {

	private static final long serialVersionUID = -5430366449139440985L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 50)
	private String name;
	
	@Column(length = 50)
	private String lastName;
	
	@Column(length = 50)
	private String email;
	
	@Column(length = 14)
	private String phone;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private List<Instrument> instruments;
	
	public User() {
	}

	public User(Long id, String name, String lastName, String mail, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = mail;
		this.phone = phone;
	}

	public User(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		this.email = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Instrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public User clone(){
		try {
			return (User) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneNotSupportedRuntimeException();
		}
	}
	
	public void clone(User user){
		this.id = user.id;
		this.name = user.name;
		this.lastName = user.lastName;
		this.email = user.email;
		this.phone = user.phone;
	}
	
	public void reset(){
		id = null;
		name = "";
		lastName = "";
		email = "";
		phone = "";
		instruments = null;
	}

}
