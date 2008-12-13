package com.minfo.dao;

import java.util.List;

import com.minfo.model.Tag;

public interface TagDAO {
	public void addTag(Tag tag);
	public void updateTag(Tag tag);
	public void removeTag(Tag tag);
	public void removeTag(Long id);
	public Tag getTag(Long id);
	public List<Tag> getTag();
}
