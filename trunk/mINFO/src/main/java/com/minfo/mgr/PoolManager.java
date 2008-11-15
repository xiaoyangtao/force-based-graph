package com.minfo.mgr;

import java.util.List;

import com.minfo.dao.PoolDAO;
import com.minfo.dao.UserDAO;
import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.model.User;

public class PoolManager {
	private PoolDAO poolDAO;
	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	public void setPoolDAO(PoolDAO poolDAO) {
		this.poolDAO = poolDAO;
	}

	
	public void updatePool(Pool pool) {
		poolDAO.updatePool(pool);
	}
	public void addPool(Pool pool) {
		poolDAO.addPool(pool);
	}
	
	public List<Pool> getPool() {
		return poolDAO.getPool();
	}
	

	public Pool getPool(Long id) {
		return poolDAO.getPool(id);
	}
	
	public Answer getAnswer(Long id) {
		return poolDAO.getAnswer(id);
	}
	
	public void removePool(Long id) {
		poolDAO.removePool(id);
	}
	
	public void makeAnswer(User user,Answer answer) {
		user.getUserAnswers().add(answer);
	}
	
	public void makeAnswer(Long userId,Long answerId) {
		User user = userDAO.getUser(userId);
		Answer answer = poolDAO.getAnswer(answerId);
		user.getUserAnswers().add(answer);
	}
}
