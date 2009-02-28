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
	    
	    retValue = "Tag ( "
	        + "id = " + this.id + TAB
	        + "name = " + this.name + TAB
	        + " )";
	
	    return retValue;
	}
	

}
