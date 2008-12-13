package com.minfo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "t_tag", schema = "public")
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139651201066235897L;

	@Id
	@SequenceGenerator(sequenceName = "seq_tag", name = "seq_tag", allocationSize = 25)
	@GeneratedValue(generator = "seq_tag")
	@Column(name = "id_tag")
	private Long id;
	@Column(name = "name")
	private String name;
	@ManyToMany(
	        targetEntity=Answer.class
	    )
	    @JoinTable(
	        name="t_pool_tag",
	        joinColumns=@JoinColumn(name="id_tag"),
	        inverseJoinColumns=@JoinColumn(name="id_pool")
	    )
	private List<Answer> newsForTag;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Answer> getNewsForTag() {
		return newsForTag;
	}
	public void setNewsForTag(List<Answer> newsForTag) {
		this.newsForTag = newsForTag;
	}
	

	

}
