package com.minfo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "t_user", schema = "public")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139651201066235897L;

	@Id
	@SequenceGenerator(sequenceName = "seq_user", name = "seq_user", allocationSize = 25)
	@GeneratedValue(generator = "seq_user")
	@Column(name = "id_user")
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	
	private String password;
	 
	@ManyToMany(
		        targetEntity=Answer.class
		    )
		    @JoinTable(
		        name="t_user_answer",
		        joinColumns=@JoinColumn(name="id_user"),
		        inverseJoinColumns=@JoinColumn(name="id_answer")
		    )
	private List<Answer> userAnswers;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Answer> getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(List<Answer> userAnswers) {
		this.userAnswers = userAnswers;
	}
	
	
	public String toString() {
		return "[User:id="+id+";username="+username+";password="+password+"]";
	}
	


	

}
