package com.minfo.tests;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.minfo.mgr.PoolManager;
import com.minfo.mgr.UserManager;
import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.model.User;
import com.minfo.services.MobileService;

public class XMLTransformTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			String prefixPath = "src/main/webapp/WEB-INF/";
			String[] paths = { prefixPath + "applicationContext-ds.xml",
					prefixPath + "applicationContext.xml",
					prefixPath + "applicationContext-services.xml"
					};
			
			ApplicationContext ctx = new FileSystemXmlApplicationContext(paths);
	
			SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
			HibernateTemplate hibernateTemplate = (HibernateTemplate) ctx.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			// session.setFlushMode(FlushMode.NEVER);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
			//================================================================
			MobileService mobileService = (MobileService)ctx.getBean("mobileService");
			
		//	String xml = mobileService.getPoolScreen(new Long(70));
		//	System.out.println(xml);
			
			
			//================================================================
			SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
			// sessionHolder.getSession().getTransaction().commit();
			SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);

	}

}
