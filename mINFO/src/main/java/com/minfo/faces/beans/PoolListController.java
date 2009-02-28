package com.minfo.faces.beans;

import java.util.ArrayList;
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
import com.minfo.mgr.TagManager;
import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.model.Tag;
import com.minfo.services.NewsFeeder;

public class PoolListController {
	private static Logger log = Logger.getLogger(PoolListController.class);

	PoolManager poolManager;
	TagManager tagManager;
	Pool currentPool;
	Answer currentAnswer;
	NewsFeeder newsFeeder;
	List<SelectedTag> selectedTags;

	/**
	 * default empty constructor
	 */
	public PoolListController() {
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
		String poolId = (String) context.getExternalContext()
				.getRequestParameterMap().get("poolId");
		log.debug("poolId=" + poolId);
		try {
			poolManager.removePool(new Long(poolId));
		} catch (DataIntegrityViolationException e) {
			String message = "Cannot delete this record!";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, message,
							message));

			log.debug(e.getMessage(), e);
		}

		return "poolList";
	}

	public String performPool() {
		log.debug("enter performPool");
		log.debug("pool=" + currentPool);
		log.debug("selectedTags=" + selectedTags);
		
		List tags = null;
		if(currentPool.getTags()==null) {
			currentPool.setTags(new ArrayList<Tag>());
		}
		tags = currentPool.getTags();
		tags.clear();
		for(SelectedTag st:selectedTags) {
			if(st.isSelected()) {
				tags.add(st.getTag());
			}
		}
		
		if (currentPool.getId() == null) {
			poolManager.addPool(currentPool);
		} else {
			poolManager.updatePool(currentPool);
		}

		return "success";
	}

	public String addAnswer() {
		log.debug("enter addAnswer");
		Answer a = new Answer();
		a.setPool(currentPool);
		currentPool.getAnswers().add(a);

		return "success";
	}

	public String deleteAnswer() {
		log.debug("enter deleteAnswer");
		FacesContext context = FacesContext.getCurrentInstance();
		String answerIdStr = (String) context.getExternalContext()
				.getRequestParameterMap().get("answerId");
		log.debug("answerId=" + answerIdStr);
		Long answerId = new Long(answerIdStr);
		List<Answer> answers = currentPool.getAnswers();
		for (Answer a : answers) {
			if (a.getId().equals(answerId)) {
				if (a.getUsersForAnswer().size() == 0) {
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
		String answerIdStr = (String) context.getExternalContext()
				.getRequestParameterMap().get("answerId");
		log.debug("answerId=" + answerIdStr);
		Long answerId = new Long(answerIdStr);
		currentAnswer = poolManager.getAnswer(answerId);
		return "showUsersForAnswer";
	}

	public Pool getCurrentPool() {
		log.debug("enter getCurrentPool");
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
		Long poolId = null;
		FacesContext context = FacesContext.getCurrentInstance();
		String poolIdStr = (String) context.getExternalContext()
				.getRequestParameterMap().get("poolId");
		log.debug("poolId=" + poolIdStr);
		if (!StringUtil.emptyString(poolIdStr)) {
			poolId = new Long(poolIdStr);
		}
		currentPool = poolManager.getPool(new Long(poolId));
		
		log.debug("Got pool:" + currentPool);
		
		List<Tag> availableTags = tagManager.getTag();
		selectedTags = new ArrayList<SelectedTag>();
		for (Tag t : availableTags) {
			SelectedTag st = new SelectedTag();
			st.setTag(t);
			if(currentPool.getTags().contains(t)) {
				st.setSelected(true);
			} else {
				st.setSelected(false);
			}
			selectedTags.add(st);
		}
		/*
		 * if(currentPool.getAnswers()==null) { currentPool.setAnswers(new
		 * LinkedList<Answer>()); }
		 */
		return "editPool";
	}

	public List<SelectedTag> getSelectedTags() {
		return selectedTags;
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

	public void setTagManager(TagManager tagManager) {
		this.tagManager = tagManager;
	}

	public static class SelectedTag {
		private Tag tag;
		private boolean selected;

		public Tag getTag() {
			return tag;
		}

		public void setTag(Tag tag) {
			this.tag = tag;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		
		public String toString() {
			return "[SelectedTag: \nTag="+tag+";\nselected="+selected+"]";
		}

	}

	public void setSelectedTags(List<SelectedTag> selectedTags) {
		this.selectedTags = selectedTags;
	}
}