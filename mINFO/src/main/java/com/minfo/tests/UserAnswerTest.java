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

public class UserAnswerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			String prefixPath = "src/main/webapp/WEB-INF/";
			String[] paths = { prefixPath + "applicationContext-ds.xml",
					prefixPath + "applicationContext.xml"
					};
			
			ApplicationContext ctx = new FileSystemXmlApplicationContext(paths);
	
			SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
			HibernateTemplate hibernateTemplate = (HibernateTemplate) ctx.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			// session.setFlushMode(FlushMode.NEVER);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
			//================================================================

			
			PoolManager poolManager = (PoolManager)ctx.getBean("poolManager");
			UserManager userManager = (UserManager)ctx.getBean("userManager");
			
			System.out.println(" ======= POOLS ========");
			List<Pool> pools = poolManager.getPool();
			System.out.println(pools);
			
			Answer answer = poolManager.getAnswer(new Long(29));
			
			System.out.println(" ======= USERS ========");
//			List<User> users = userManager.getUser();
//			System.out.println(users);
			
			User user = userManager.getUser(new Long(2));
			System.out.println(user);
			poolManager.makeAnswer(user, answer);
			
			//System.out.println("Answers:"+user.getUserAnswers());
			userManager.updateUser(user);
			//================================================================
			SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
			// sessionHolder.getSession().getTransaction().commit();
			SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);

	}

}
