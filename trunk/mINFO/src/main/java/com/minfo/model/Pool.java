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

	@OneToMany(mappedBy = "pool", fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pool")
	@OrderBy("id")
	@Cascade( { org.hibernate.annotations.CascadeType.ALL,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Answer> answers;

	@ManyToMany(mappedBy = "userDisplayedPools", fetch = FetchType.LAZY, targetEntity = User.class)
	private List<User> usersDisplayedPools;
	
	@ManyToMany(
	        targetEntity=Tag.class
	    )
	    @JoinTable(
	        name="t_pool_tag",
	        joinColumns=@JoinColumn(name="id_pool"),
	        inverseJoinColumns=@JoinColumn(name="id_tag")
	    )
	private List<Tag> tags;

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

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<User> getUsersDisplayedPools() {
		return usersDisplayedPools;
	}

	public void setUsersDisplayedPools(List<User> usersDisplayedPools) {
		this.usersDisplayedPools = usersDisplayedPools;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public String getQuestionShort() {
		return question.substring(0, 30)+"...";
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
	    
	    retValue = "Pool ( "
	        + "id = " + this.id + TAB
	        + "question = " + this.question + TAB
	        + "tags = " + this.tags + TAB
	        + " )";
	
	    return retValue;
	}


	

}
