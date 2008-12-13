package com.minfo.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.minfo.dao.TagDAO;
import com.minfo.model.Tag;

public class TagHibernateDAO extends HibernateDaoSupport implements TagDAO {
	private static Logger log = Logger.getLogger(TagHibernateDAO.class);
	
	public Tag getTag(Long id) {
		log.debug("enter getTag, id="+id);
		return (Tag)getHibernateTemplate().get(Tag.class, id);
	}
	
	public void addTag(Tag tag) {
		log.debug("enter addTag, tag="+tag);
		getHibernateTemplate().save(tag);
		log.debug("leave addTag,tag="+tag);
	}

	public void updateTag(Tag tag) {
		log.debug("enter updateTag, tag="+tag);
		getHibernateTemplate().merge(tag);
		log.debug("leave updateTag,tag="+tag);
	}
	
	public void removeTag(Tag tag) {
		log.debug("enter removeTag, tag="+tag);
		getHibernateTemplate().delete(tag);
	}
	
	public void removeTag(Long id) {
		log.debug("enter removeTag, tagId="+id);
		Tag p = getTag(id);
		removeTag(p);
	}

	public List<Tag> getTag() {
		log.debug("enter getTag");
		List<Tag> tagList = getHibernateTemplate().find("from Tag");
		log.debug("tagList = "+tagList);
		
		return tagList;
	}

}
