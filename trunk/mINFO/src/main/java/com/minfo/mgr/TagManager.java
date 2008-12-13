package com.minfo.mgr;

import java.util.List;

import com.minfo.dao.TagDAO;
import com.minfo.model.Tag;

public class TagManager {
	private TagDAO tagDAO;

	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	
	public void updateTag(Tag tag) {
		tagDAO.updateTag(tag);
	}
	public void addTag(Tag tag) {
		tagDAO.addTag(tag);
	}
	
	public List<Tag> getTag() {
		return tagDAO.getTag();
	}
	

	public Tag getTag(Long id) {
		Tag u = tagDAO.getTag(id);
	
		return tagDAO.getTag(id);
	}
	
	public void removeTag(Long id) {
		tagDAO.removeTag(id);
	}
	
	
}
