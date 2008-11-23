package com.minfo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.minfo.faces.beans.PoolListController;
import com.minfo.mgr.PoolManager;
import com.minfo.model.Answer;
import com.minfo.model.Pool;

public class NewsFeeder {
	private static transient Logger log = Logger
			.getLogger(PoolListController.class);
	
	private PoolManager poolManager;
	
	public void getNews() {
		String url = "http://wiadomosci.wp.pl/ver,rss,rss.xml";
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
		    	
		    	poolManager.addPool(pool);
		    	log.debug(item);
		    }

		} catch (Throwable e) {
			e.printStackTrace();
			log.debug(e.getMessage(), e);
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
}
