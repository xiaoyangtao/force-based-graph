package com.minfo.model.ws;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.model.User;

public class WSAnswer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139651201066235897L;
	
	private Long id;
	private String answer;
   
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public WSAnswer() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public static void fromAnswer(Answer answer, WSAnswer wsAnswer) {
		wsAnswer.setId(answer.getId());
		wsAnswer.setAnswer(answer.getAnswer());
	}
}