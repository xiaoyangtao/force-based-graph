package com.minfo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "t_pool", schema = "public")
public class Pool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139651201066235897L;

	@Id
	@SequenceGenerator(sequenceName = "seq_pool", name = "seq_pool", allocationSize = 25)
	@GeneratedValue(generator = "seq_pool")
	@Column(name = "id_pool")
	private Long id;
	@Column(name = "question")
	private String question;

	@OneToMany(mappedBy="pool",fetch=FetchType.EAGER)
	@JoinColumn(name="id_pool")
	@OrderBy("id")
	@Cascade({org.hibernate.annotations.CascadeType.ALL,
          org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private List<Answer> answers;
	
	public Long getId() {
		return id;
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

	public Pool() {

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nPool[");
		sb.append("id=" + id + ";");
		sb.append("question=" + question+";");
		sb.append("Answers:"+answers+"]");
		return sb.toString();

	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	

}
