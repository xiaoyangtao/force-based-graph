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
	
	
	public List<User> getUsersForAnswer() {
		return usersForAnswer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Answer() {
		
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

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "Answer ( "
	        + "id = " + this.id + TAB
	        + "answer = " + this.answer + TAB
	        + " )";
	
	    return retValue;
	}
	
}
