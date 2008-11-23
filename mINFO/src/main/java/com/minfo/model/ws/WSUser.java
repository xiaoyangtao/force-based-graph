package com.minfo.model.ws;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import com.minfo.model.Answer;
import com.minfo.model.User;


public class WSUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139651201066235897L;

	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	
	private String password;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
	
	public static void fromUser(User user,WSUser wsUser) {
		wsUser.setId(user.getId());
		wsUser.setUsername(user.getUsername());
		wsUser.setPassword(user.getPassword());
	}

	

}
