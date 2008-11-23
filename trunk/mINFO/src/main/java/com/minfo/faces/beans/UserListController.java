package com.minfo.faces.beans;

import java.util.List;

import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

import com.minfo.common.StringUtil;
import com.minfo.mgr.UserManager;
import com.minfo.model.User;

public class UserListController {
	private static Logger log = Logger.getLogger(UserListController.class);
	
    UserManager userManager;
    User currentUser;
    /**
     * default empty constructor
     */
    public UserListController(){
    }
    
  
    public List<User> getUserList() {
    	log.debug("enter getUserList");
    	return userManager.getUser();
    }
    
    public int getUserCount() {
    	log.debug("enter getUserCount");
    	return userManager.getUser().size();
    }
    
    public String deleteUser() {
    	log.debug("enter deleteUser");
    	
    	FacesContext context = FacesContext.getCurrentInstance();
		String userId = (String) context.getExternalContext().getRequestParameterMap().get("userId");
		log.debug("userId=" + userId);
		userManager.removeUser(new Long(userId));
		
    	return "userList";
    }
    
    public String performUser() {
		log.debug("enter performUser");
		log.debug("user="+currentUser);
		if(currentUser.getId()==null) {
			userManager.addUser(currentUser);
		} else
		{
			userManager.updateUser(currentUser);
		}
		
		return "success";
	}
	
	
	
	public User getCurrentUser() {
		return currentUser;
	}

	public String newUser() {
		currentUser = new User();
		return "newUser";
	}
	
    public String editUser() {
    	log.debug("enter editUser");
    	Long userId=null;
    	FacesContext context = FacesContext.getCurrentInstance();
		String userIdStr = (String) context.getExternalContext().getRequestParameterMap().get("userId");
		log.debug("userId=" + userIdStr);
    	if(!StringUtil.emptyString(userIdStr)){
			userId = new Long(userIdStr);
		}
		currentUser = userManager.getUser(new Long(userId));
		log.debug("Got user:"+currentUser);
		
    	return "editUser";
    }
    
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}