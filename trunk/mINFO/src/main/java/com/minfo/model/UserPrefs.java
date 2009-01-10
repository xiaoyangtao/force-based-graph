package com.minfo.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class UserPrefs {
	
	@Id
	@ManyToOne
    @JoinColumn(name="id_user")
	private User user;
	@Id
	@ManyToOne
    @JoinColumn(name="id_tag")
	private Tag tag;
	@Column(name="part")
	private Double part;
	
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
	public Double getPart() {
		return part;
	}
	public void setPart(Double part) {
		this.part = part;
	}
	
	
}
