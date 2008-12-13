package com.minfo.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.minfo.dao.PoolDAO;
import com.minfo.model.Answer;
import com.minfo.model.Pool;

public class PoolHibernateDAO extends HibernateDaoSupport implements PoolDAO {
	private static Logger log = Logger.getLogger(PoolHibernateDAO.class);
	
	public Pool getPool(Long id) {
		log.debug("enter getPool, id="+id);
		return (Pool)getHibernateTemplate().get(Pool.class, id);
	}
	
	public Answer getAnswer(Long id) {
		log.debug("enter getAnswer, id="+id);
		return (Answer)getHibernateTemplate().get(Answer.class, id);
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

	public List<Pool> getNewPoolsForUser(Long userId) {
	/*	List<Pool> pools = getHibernateTemplate().
		find("from Pool p where p not in (select pool from " +
				"User u join u.userAnswers as answer " +
				"join answer.pool as pool where u.id=?)",userId);
	*/
		List<Pool> pools = getHibernateTemplate().
		find("from Pool p where p not in (select pool from " +
				"User u join u.userDisplayedPools as pool where u.id=?)",userId);
		// TODO Auto-generated method stub
		System.out.println(pools);
		return pools;
	}

}
