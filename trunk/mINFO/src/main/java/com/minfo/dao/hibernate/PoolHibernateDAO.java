package com.minfo.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.minfo.dao.PoolDAO;
import com.minfo.model.Pool;

public class PoolHibernateDAO extends HibernateDaoSupport implements PoolDAO {
	private static Logger log = Logger.getLogger(PoolHibernateDAO.class);
	
	public Pool getPool(Long id) {
		log.debug("enter getPool, id="+id);
		return (Pool)getHibernateTemplate().get(Pool.class, id);
	}
	
	public void addPool(Pool pool) {
		log.debug("enter addPool, pool="+pool);
		getHibernateTemplate().save(pool);
		log.debug("leave addPool,pool="+pool);
	}

	public void updatePool(Pool pool) {
		log.debug("enter updatePool, pool="+pool);
		getHibernateTemplate().merge(pool);
		log.debug("leave updatePool,pool="+pool);
	}
	
	public void removePool(Pool pool) {
		log.debug("enter removePool, pool="+pool);
		getHibernateTemplate().delete(pool);
	}
	
	public void removePool(Long id) {
		log.debug("enter removePool, poolId="+id);
		Pool p = getPool(id);
		removePool(p);
	}

	public List<Pool> getPool() {
		log.debug("enter getPool");
		List<Pool> poolList = getHibernateTemplate().find("from Pool");
		log.debug("poolList = "+poolList);
		
		return poolList;
	}

}
