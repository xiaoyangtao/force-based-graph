package com.minfo.dao;

import java.util.List;

import com.minfo.model.Answer;
import com.minfo.model.Pool;

public interface PoolDAO {
	public void addPool(Pool pool);
	public void updatePool(Pool pool);
	public void removePool(Pool pool);
	public void removePool(Long id);
	public Pool getPool(Long id);
	public List<Pool> getPool();
	public Answer getAnswer(Long id);
}
