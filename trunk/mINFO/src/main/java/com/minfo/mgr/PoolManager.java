package com.minfo.mgr;

import java.util.List;

import com.minfo.dao.PoolDAO;
import com.minfo.model.Pool;

public class PoolManager {
	private PoolDAO poolDAO;

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
	
	public void removePool(Long id) {
		poolDAO.removePool(id);
	}
}
