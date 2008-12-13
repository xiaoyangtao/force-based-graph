package com.minfo.faces.beans;

import java.util.List;

import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

import com.minfo.common.StringUtil;
import com.minfo.mgr.TagManager;
import com.minfo.mgr.UserManager;
import com.minfo.model.Tag;
import com.minfo.model.User;

public class TagListController {
	private static Logger log = Logger.getLogger(TagListController.class);
	
    TagManager tagManager;
    Tag currentTag;
    String newTagName;
    /**
     * default empty constructor
     */
    public TagListController(){
    }
    
  
    public List<Tag> getTagList() {
    	log.debug("enter getTagList");
    	return tagManager.getTag();
    }
    
    public int getTagCount() {
    	log.debug("enter getTagCount");
    	return tagManager.getTag().size();
    }
    
    public String deleteTag() {
    	log.debug("enter deleteTag");
    	
    	FacesContext context = FacesContext.getCurrentInstance();
		String tagId = (String) context.getExternalContext().getRequestParameterMap().get("tagId");
		log.debug("tagId=" + tagId);
		tagManager.removeTag(new Long(tagId));
		
    	return "tagList";
    }
    
    public String saveTags() {
		log.debug("enter saveTags");
	//	log.debug("tag="+currentTag);
//		if(currentTag.getId()==null) {
//			tagManager.addTag(currentTag);
//		} else
//		{
//			tagManager.updateTag(currentTag);
//		}
		
		return "success";
	}
	
	
	
	public Tag getCurrentTag() {
		return currentTag;
	}

	public String newTag() {
		Tag t = new Tag();
		t.setName(newTagName);
		tagManager.addTag(t);
		
		return "success";
	}
	
	
    
	public void setTagManager(TagManager tagManager) {
		this.tagManager = tagManager;
	}


	public String getNewTagName() {
		return newTagName;
	}


	public void setNewTagName(String newTagName) {
		this.newTagName = newTagName;
	}
}