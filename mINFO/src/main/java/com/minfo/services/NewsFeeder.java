package com.minfo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.minfo.faces.beans.PoolListController;
import com.minfo.mgr.PoolManager;
import com.minfo.mgr.TagManager;
import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.model.Tag;

public class NewsFeeder {
	private static transient Logger log = Logger
			.getLogger(PoolListController.class);
	
	private PoolManager poolManager;
	private TagManager tagManager;
	private HibernateTemplate hibernateTemplate;
	private List<Tag> availableTags;
	private boolean init = false;
	public static final HashMap<String,String> channels = new HashMap<String,String>();
	
	static {
		channels.put("polityka", "http://wiadomosci.wp.pl/ver,rss,rss.xml");
		channels.put("biznes", "http://media.wp.pl/rss_biznes.xml");
		channels.put("kultura", "http://kultura.wp.pl/rss.xml");
		channels.put("sport", "http://sport.wp.pl/rss.xml");
		channels.put("technologie", "http://tech.wp.pl/rss.xml");
		channels.put("nauka", "http://nauka.wp.pl/rss.xml");
		channels.put("ciekawostki", "http://wiadomosci.onet.pl/69,kategoria.rss");
	}
	
	public void getNews() {
		for(Entry<String,String> e:channels.entrySet()) {
			String url = e.getValue();
			String channelName = e.getKey();
			List<Tag> tags = hibernateTemplate.find("from Tag t where t.name=?",channelName);
			if(tags.size()==1) {
				
				try {
					URL rssURL = new URL(url);
					InputStream is = rssURL.openStream();
					
					/*InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);

					String line = br.readLine();
					while (line != null) {
						System.out.println(line);
						log.debug(line);
						line = br.readLine();
					}
		*/
					SAXParserFactory factory = SAXParserFactory.newInstance();
					RSSHandler handler = new RSSHandler();
					SAXParser p = factory.newSAXParser();
				    p.parse(is, handler);
				    
				    for(RSSItem item:handler.getItems()) {
				    	Pool pool = new Pool();
				    	pool.setAnswers(new LinkedList<Answer>());
				    	pool.setQuestion(item.getDescription().replaceAll("\\<.*?>",""));
				    	
				    	Answer a = new Answer();
				    	a.setAnswer("TAK");
				    	a.setPool(pool);
				    	pool.getAnswers().add(a);
				    	
				    	a = new Answer();
				    	a.setAnswer("NIE");
				    	a.setPool(pool);
				    	pool.getAnswers().add(a);
				    	
				    	pool.setTags(tags);
				    	poolManager.addPool(pool);
				    	log.debug(item);
				    }

				} catch (Throwable t) {
					t.printStackTrace();
					log.debug(t.getMessage(), t);
				}
				
				
			} else {
				log.error("Tagi w bazie zosta³y b³êdnie okreœlone");
			}
		}
		
		
	

	}

	private class RSSHandler extends DefaultHandler {
		List<RSSItem> items;
		RSSItem item;
		StringBuilder sb;
		
		public RSSHandler() {
			items = new LinkedList<RSSItem>();
			sb = new StringBuilder();
		}
		
		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) {
			if (qName.equals("item")) {
				item = new RSSItem();
			}
			sb.delete(0, sb.length());
		}
		
		public void characters(char[] ch, int start, int length) {
			sb.append(ch,start,length);
		}
		
		public void endElement(String namespaceURI, String localName, String qName) {
			if(item!=null) {
				if (qName.equals("title")) {
					item.title = sb.toString();
				} else if (qName.equals("link")) {
					item.link = sb.toString();
				} else if (qName.equals("description")) {
					item.description = sb.toString();
				} else if (qName.equals("category")) {
					item.category = sb.toString();
				}  else if (qName.equals("pubDate")) {
					item.pubDate = sb.toString();
				}  else if (qName.equals("item")) {
					items.add(item);
				} 
			//	System.out.println("qName="+qName+";sb.toString()="+sb.toString());
			}
		}

		public List<RSSItem> getItems() {
			return items;
		}
		
	}

	private class RSSItem {
		private String title;
		private String link;
		private String description;
		private String category;
		private String pubDate;
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getPubDate() {
			return pubDate;
		}
		public void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}
		
		public String toString() {
			return "RSSItem[title="+title+";link="+link+";description="+description+";category="+category+";pubDate="+pubDate+"]";
		}
	}

	public void setPoolManager(PoolManager poolManager) {
		this.poolManager = poolManager;
	}

	public void setTagManager(TagManager tagManager) {
		this.tagManager = tagManager;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
