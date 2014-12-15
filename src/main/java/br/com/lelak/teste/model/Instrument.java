package br.com.lelak.teste.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Instrument implements Serializable, Cloneable, EntityBean {

	private static final long serialVersionUID = 7582414649100351188L;

	@Id
	@GeneratedValue
	private Long id;

	@Lob
	@Basic(fetch=FetchType.LAZY)
	private byte[] image;
	
	@Column(length = 50)
	private String name;
	
	@ManyToOne
	private User user;
	
	public Instrument() {
	}

	public Instrument(Long id, byte[] image, String name, User user) {
		this.id = id;
		this.image = image;
		this.name = name;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public Instrument clone(){
		Instrument clone = new Instrument();
		clone.id = id;
		clone.name = name;
		clone.image = image;
		clone.user = user;
		return clone;
	}
	
	protected void reset(){
		this.id = null;
		this.image = null;
		this.name = "";
		this.user = null;
	}

}
