package com.minfo.dao;

import java.util.List;

import com.minfo.model.User;
import com.minfo.model.UserPrefs;
import com.minfo.model.UserStats;

public interface UserDAO {
	public void addUser(User user);
	public void updateUser(User user);
	public void removeUser(User user);
	public void removeUser(Long id);
	public User getUser(Long id);
	public List<User> getUser();
	public List<UserPrefs> getPrefs(Long userId);
	public void removeStats(Long userId);
	public void setPrefs(List<UserPrefs> prefs);
}
