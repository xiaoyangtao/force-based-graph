package com.minfo.model.ws;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import com.minfo.model.Answer;
import com.minfo.model.Pool;


public class WSPool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139651201066235897L;

	private Long id;
	private String question;

	private WSAnswer[] wsAnswers;
	
	public Long getId() {
		return id;
	}

	

	public WSAnswer[] getWsAnswers() {
		return wsAnswers;
	}



	public void setWsAnswers(WSAnswer[] wsAnswers) {
		this.wsAnswers = wsAnswers;
	}



	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WSPool() {

	}

	public static void fromPool(Pool pool, WSPool wsPool) {
		wsPool.setId(pool.getId());
		wsPool.setQuestion(pool.getQuestion());
		WSAnswer[] wsAnswers = new WSAnswer[pool.getAnswers().size()];
		int i=0;
		for(Answer answer:pool.getAnswers()) {
			wsAnswers[i] = new WSAnswer();
			WSAnswer.fromAnswer(answer, wsAnswers[i]);
			i++;
		}
		wsPool.setWsAnswers(wsAnswers);
	}
	

}
