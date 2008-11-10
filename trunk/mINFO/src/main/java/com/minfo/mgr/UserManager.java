package com.minfo.mgr;

import java.util.List;

import com.minfo.dao.UserDAO;
import com.minfo.model.User;

public class UserManager {
	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}
	public void addUser(User user) {
		userDAO.addUser(user);
	}
	
	public List<User> getUser() {
		return userDAO.getUser();
	}
	

	public User getUser(Long id) {
		return userDAO.getUser(id);
	}
	
	public void removeUser(Long id) {
		userDAO.removeUser(id);
	}
}
