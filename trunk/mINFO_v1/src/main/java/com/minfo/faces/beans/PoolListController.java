package com.minfo.faces.beans;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import com.minfo.common.StringUtil;
import com.minfo.dao.hibernate.PoolHibernateDAO;
import com.minfo.mgr.PoolManager;
import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.services.NewsFeeder;


public class PoolListController {
	private static Logger log = Logger.getLogger(PoolListController.class);
	
    PoolManager poolManager;
    Pool currentPool;
    Answer currentAnswer;
    NewsFeeder newsFeeder;
    
    /**
     * default empty constructor
     */
    public PoolListController(){
    }
    
  
    public List<Pool> getPoolList() {
    	log.debug("enter getPoolList");
    	return poolManager.getPool();
    }
    
    public int getPoolCount() {
    	log.debug("enter getPoolCount");
    	return poolManager.getPool().size();
    }
    
    public String deletePool() {
    	log.debug("enter deletePool");
    	
    	FacesContext context = FacesContext.getCurrentInstance();
		String poolId = (String) context.getExternalContext().getRequestParameterMap().get("poolId");
		log.debug("poolId=" + poolId);
		try {
			poolManager.removePool(new Long(poolId));
		}catch(DataIntegrityViolationException e) {
			String message = "Cannot delete this record!";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO, message, message));

			log.debug(e.getMessage(),e);
		}
		
    	return "poolList";
    }
    
    public String performPool() {
		log.debug("enter performPool");
		log.debug("pool="+currentPool);
		if(currentPool.getId()==null) {
			poolManager.addPool(currentPool);
		} else
		{
			poolManager.updatePool(currentPool);
		}
		
		return "success";
	}
	
	public String addAnswer() {
		log.debug("enter addAnswer");
		Answer a  = new Answer();
		a.setPool(currentPool);
		currentPool.getAnswers().add(a);
		
		return "success";
	}
	
	public String deleteAnswer() {
		log.debug("enter deleteAnswer");
		FacesContext context = FacesContext.getCurrentInstance();
		String answerIdStr = (String) context.getExternalContext().getRequestParameterMap().get("answerId");
		log.debug("answerId=" + answerIdStr);
		Long answerId = new Long(answerIdStr);
		List<Answer> answers = currentPool.getAnswers();
		for(Answer a:answers) {
			if(a.getId().equals(answerId))
			{
				if(a.getUsersForAnswer().size()==0) {
					answers.remove(a);
				} else {
					context.addMessage("Test", new FacesMessage());
				}
				break;
			}
		}
    	
		return "success";
	}
	
	public String showUsersForAnswer() {
		log.debug("enter showUsersForAnswer");
		FacesContext context = FacesContext.getCurrentInstance();
		String answerIdStr = (String) context.getExternalContext().getRequestParameterMap().get("answerId");
		log.debug("answerId=" + answerIdStr);
		Long answerId = new Long(answerIdStr);
		currentAnswer = poolManager.getAnswer(answerId);
		return "showUsersForAnswer";
	}
	
	
	
	public Pool getCurrentPool() {
		return currentPool;
	}

	public String newPool() {
		currentPool = new Pool();
		currentPool.setAnswers(new LinkedList<Answer>());
		return "newPool";
	}
	
	public String feedNews() {
		newsFeeder.getNews();
		return "poolList";
	}
	
    public String editPool() {
    	log.debug("enter editPool");
    	Long poolId=null;
    	FacesContext context = FacesContext.getCurrentInstance();
		String poolIdStr = (String) context.getExternalContext().getRequestParameterMap().get("poolId");
		log.debug("poolId=" + poolIdStr);
    	if(!StringUtil.emptyString(poolIdStr)){
			poolId = new Long(poolIdStr);
		}
		currentPool = poolManager.getPool(new Long(poolId));
		log.debug("Got pool:"+currentPool);
		/*if(currentPool.getAnswers()==null) {
			currentPool.setAnswers(new LinkedList<Answer>());
		}*/
    	return "editPool";
    }
    
	public void setPoolManager(PoolManager poolManager) {
		this.poolManager = poolManager;
	}


	public void setNewsFeeder(NewsFeeder newsFeeder) {
		this.newsFeeder = newsFeeder;
	}


	public Answer getCurrentAnswer() {
		return currentAnswer;
	}
}