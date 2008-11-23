package com.minfo.services.module;


import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.log4j.Logger;

import com.minfo.dao.hibernate.PoolHibernateDAO;

public class TransformHandler extends AbstractHandler implements Handler {
	private static transient Logger log = Logger.getLogger(TransformHandler.class);
	private String name;
	
	public TransformHandler() {
		log.debug("TransformHandler()");
	}
	public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		// TODO Auto-generated method stub
		 log.debug("Transforming:"+msgContext.getEnvelope().toString());
		 System.out.println("invoke!!! "+msgContext.getEnvelope().toString());
		 return InvocationResponse.CONTINUE;
	}
	public void cleanup() {
		super.cleanup();
		log.debug("cleanup()");
	}
	public void flowComplete(MessageContext arg0) {
		super.flowComplete(arg0);
		log.debug("flowComplete()");
		
	}
	public HandlerDescription getHandlerDesc() {
		log.debug("getHandlerDesc()");
		return super.getHandlerDesc();
	}
	public String getName() {
		log.debug("getName()");
		return name;
	}
	public Parameter getParameter(String arg0) {
		log.debug("getParameter()");
		return super.getParameter(arg0);
	}
	public void init(HandlerDescription arg0) {
		log.debug("init()");
		super.init(arg0);
		
	}
	public void setName(String name) {
		log.debug("setName()");
		this.name = name;
	}
	
  
}
