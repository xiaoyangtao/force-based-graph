package com.minfo.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.minfo.dao.UserDAO;
import com.minfo.model.User;
import com.minfo.model.UserPrefs;
import com.minfo.model.UserStats;

public class UserHibernateDAO extends HibernateDaoSupport implements UserDAO {
	private static Logger log = Logger.getLogger(UserHibernateDAO.class);

	public User getUser(Long id) {
		log.debug("enter getUser, id=" + id);
		return (User) getHibernateTemplate().get(User.class, id);
	}

	public void addUser(User user) {
		log.debug("enter addUser, user=" + user);
		getHibernateTemplate().save(user);
		log.debug("leave addUser,user=" + user);
	}

	public void updateUser(User user) {
		log.debug("enter updateUser, user=" + user);
		getHibernateTemplate().merge(user);
		log.debug("leave updateUser,user=" + user);
	}

	public void removeUser(User user) {
		log.debug("enter removeUser, user=" + user);
		getHibernateTemplate().delete(user);
	}

	public void removeUser(Long id) {
		log.debug("enter removeUser, userId=" + id);
		User p = getUser(id);
		removeUser(p);
	}

	public List<User> getUser() {
		log.debug("enter getUser");
		List<User> userList = getHibernateTemplate().find("from User");
		log.debug("userList = " + userList);
		return userList;
	}

	public List<UserPrefs> getPrefs(Long userId) {
		log.debug("enter getPrefs");
		List<UserPrefs> prefs = getHibernateTemplate().find("from UserPrefs u where u.id_user=?", userId);
		log.debug("prefs = " + prefs);
		return prefs;
	}

	public void removeStats(Long userId) {
		log.debug("enter removeStats");
		getHibernateTemplate().find("delete UserStats u where u.id_user=?",	userId);
		log.debug("leave removeStats");
	}

	public void setPrefs(List<UserPrefs> prefs) {
		getHibernateTemplate().saveOrUpdateAll(prefs);
	}

}
