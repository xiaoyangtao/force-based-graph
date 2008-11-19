package com.minfo.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_answer",schema="public")
public class Answer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139651201066235897L;
	
	@Id
	@SequenceGenerator(sequenceName="seq_answer",name="seq_answer", allocationSize=25)
	@GeneratedValue(generator="seq_answer")
    @Column(name="id_answer")
	private Long id;
    @Column(name="answer")
	private String answer;
   
    @ManyToOne
    @JoinColumn(name="id_pool")
	private Pool pool;
	
    @ManyToMany(
            mappedBy = "userAnswers",fetch=FetchType.EAGER,
            targetEntity = User.class
        )
	private List<User> usersForAnswer;
	
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Answer() {
		
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nAnswer[");
		sb.append("id="+id+";");
		sb.append("answer="+answer);
		sb.append("]");
		return sb.toString();
		
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
