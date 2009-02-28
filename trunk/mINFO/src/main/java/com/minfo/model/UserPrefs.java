package com.minfo.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_prefs_user_tag", schema = "public")
@AssociationOverrides({
	@AssociationOverride(name="primaryKey.user", joinColumns = @JoinColumn(name="id_user")),
	@AssociationOverride(name="primaryKey.tag", joinColumns = @JoinColumn(name="id_tag"))
})
public class UserPrefs implements Serializable {
	
	private static final long serialVersionUID = 6763883789463125634L;

	 @Id 
	private UserPrefsPK primaryKey = new UserPrefsPK();
	
	@Column(name="part")
	private Double part;

    
	public User getUser() {
		return primaryKey.getUser();
	}
	public void setUser(User user) {
		primaryKey.setUser(user);
	}
	public Tag getTag() {
		return primaryKey.getTag();
	}
	public void setTag(Tag tag) {
		primaryKey.setTag(tag);
	}
	public Double getPart() {
		return part;
	}
	public void setPart(Double part) {
		this.part = part;
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
	    
	    retValue = "UserPrefs ( "
	        + "user = " + primaryKey.getUser() + TAB
	        + "tag = " + primaryKey.getTag() + TAB
	        + "part = " + this.part + TAB
	        + " )";
	
	    return retValue;
	}

	
}

@Embeddable
class UserPrefsPK implements Serializable
{
	@ManyToOne
	private User user;

	@ManyToOne
	private Tag tag;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}

