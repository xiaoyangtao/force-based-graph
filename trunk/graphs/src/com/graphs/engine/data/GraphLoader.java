package com.graphs.engine.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class GraphLoader extends DefaultHandler{

	
	static GraphContener graphContener;
	
	
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		
		if(name.equals("graph")){
			System.out.println("Creating graph " + attributes.getValue("name"));
			graphContener = new GraphContener(attributes.getValue("name"));
		}
		else if(name.equals("v")){
			try {
				graphContener.addVertex(attributes.getValue("id"));
			} catch (GraphException e) {
				e.printStackTrace();
			}
		}
		else if(name.equals("e")){
			try {
				graphContener.addEdge(graphContener.getVertex(attributes.getValue("from")), 
						graphContener.getVertex(attributes.getValue("to")));
			} catch (GraphException e) {
				e.printStackTrace();
			}
			
		}
		else if(name.equals("coord")){
			try {
				graphContener.getVertex(attributes.getValue("id")).setX(Integer.parseInt(attributes.getValue("x")));
				graphContener.getVertex(attributes.getValue("id")).setY(Integer.parseInt(attributes.getValue("y")));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Unknown tag : " + name);
		}
		
	}
	
	public static GraphContener load(){
		try {
			System.out.println("XML File exists : " + (new File(Settings.XMLFileName).exists()));
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			
			parser.parse(new File(Settings.XMLFileName), new GraphLoader());
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		
		return graphContener;
	}
}
