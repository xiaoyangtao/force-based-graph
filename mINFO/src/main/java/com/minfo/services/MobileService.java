package com.minfo.services;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

import com.minfo.dao.hibernate.PoolHibernateDAO;
import com.minfo.mgr.PoolManager;
import com.minfo.mgr.UserManager;
import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.model.ws.WSPool;

public class MobileService {
	private static transient Logger log = Logger
			.getLogger(PoolHibernateDAO.class);

	private transient PoolManager poolManager;
	private transient UserManager userManager;

	public void setPoolManager(PoolManager poolManager) {
		this.poolManager = poolManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getVersion() {
		log.debug("enter getVersion");
		return "MobileService v0.1";
	}
	
	public WSPool getPool(Long id) {
		log.debug("enter getPool");
		Pool p =  poolManager.getPool(id);
		log.debug(p);
		
		WSPool wsPool = new WSPool();
		WSPool.fromPool(p, wsPool);
		return wsPool;
	}
	
	
	
	
	@Deprecated
	public String getNextPool() {
		log.debug("enter getNextQuestion");
		List<Pool> list = poolManager.getPool();

		Pool p = list.get(0);
		
		String ret=null;
		try {
			String mainElement = "pool";

			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(bs);
			if (bos != null) {
				try {
					OutputFormat of = new OutputFormat("XML", "UTF-8", true);
					of.setIndent(1);
					of.setIndenting(true);

					XMLSerializer serializer = new XMLSerializer(bos, of);
					ContentHandler hd = serializer.asContentHandler();
					hd.startDocument();
					AttributesImpl atts = new AttributesImpl();
					atts.addAttribute("", "", "id", "CDATA", p.getId().toString());
					hd.startElement("", "", mainElement, atts);
					atts.clear();
					hd.startElement("", "", "question", atts);
					hd.characters(p.getQuestion().toCharArray(), 0, p.getQuestion().length());
					hd.endElement("", "", "question");
					
					for(Answer a:p.getAnswers()){
						hd.startElement("", "", "answer", atts);
						atts.clear();
						atts.addAttribute("", "", "id", "CDATA", a.getId().toString());
						hd.characters(a.getAnswer().toCharArray(), 0, a.getAnswer().length());
						hd.endElement("", "", "answer");
					}
					
					hd.endElement("", "", mainElement);
					hd.endDocument();
				} finally {
					if (bos != null) {
						bos.close();
					}
				}
			}
			ret = new String(bs.toByteArray());

		} catch (Throwable t) {
			log.error(t.getMessage(), t);
		}

		return ret;
	}
}
