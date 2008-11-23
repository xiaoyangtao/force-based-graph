package com.minfo.dao;

import java.util.List;

import com.minfo.model.User;

public interface UserDAO {
	public void addUser(User user);
	public void updateUser(User user);
	public void removeUser(User user);
	public void removeUser(Long id);
	public User getUser(Long id);
	public List<User> getUser();
}
