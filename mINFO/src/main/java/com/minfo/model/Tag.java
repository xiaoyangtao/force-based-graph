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
	
	@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, targetEntity = Pool.class)
	private List<Answer> poolsForTag;
	
	
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
	public List<Answer> getPoolsForTag() {
		return poolsForTag;
	}
	public void setPoolsForTag(List<Answer> poolsForTag) {
		this.poolsForTag = poolsForTag;
	}

	public String toString() {
		return "[Tag: id="+id+";name="+name+"]";
	}
	

}
