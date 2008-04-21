package com.graphs.engine.data;

import java.util.ArrayList;
import java.util.Iterator;

public class GraphContener {
	private String name;
	
	private ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
	private ArrayList<Edge> allEdges = new ArrayList<Edge>();
	
	private int translationX = 0;
	private int translationY = 0;
	
	Vertex selectedVertex = null;
	
	public GraphContener(String name) {
		this.name = name;
	}
	
	public void addVertex(String id) throws GraphException{
		Vertex v = new Vertex(id);
		vertexes.add(v);
	}
	
	public Vertex getVertex(String id){
		for(Iterator<Vertex> iter = getVertexes().iterator(); iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v.getId().equals(id)) {
				return v;
			}
		}
		return null;
	}
	
	public void addEdge(Vertex a, Vertex b) throws GraphException{
		for(Iterator<Edge> iter = getAllEdges().iterator(); iter.hasNext();){
			Edge e = (Edge)iter.next();
			if((e.getA().equals(a) && e.getB().equals(b)) || 
					(e.getA().equals(b) && e.getB().equals(a))){
				throw new GraphException("Edge already exists !");
			}
		}
		if(a.equals(b)) {
			throw new GraphException("Start and end are the same!");
		}
		Edge e = new Edge(a, b);
		a.addEdge(e);
		b.addEdge(e);
		allEdges.add(e);
	}
	
	public String toXML(){
		StringBuilder xml = new StringBuilder();
		// <graph name = "test">
		xml.append("<graph name = \"");
		xml.append(getName());
		xml.append("\">");
		for (Iterator iter = vertexes.iterator(); iter.hasNext();) {
			Vertex v = (Vertex) iter.next();
			// <v id="1"/>
			xml.append("<v id=\"");
		    xml.append(v.getId());
		    xml.append("\"/>");
		}
		for (Iterator iter = allEdges.iterator(); iter.hasNext();) {
			Edge e = (Edge) iter.next();
			// <e from="1" to="2"/>
			xml.append("<e from=\"");
		    xml.append(e.getA().getId());
		    xml.append("\" to=\"");
		    xml.append(e.getB().getId());
		    xml.append("\"/>");
		}
		// </graph>
		xml.append("</graph>");
		return xml.toString();
	}
	
	public ArrayList<Edge> getAllEdges() {
		return allEdges;
	}
	
	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTranslationX() {
		return translationX;
	}
	
	public void setTranslationX(int translationX) {
		this.translationX = translationX;
	}
	
	public void adjustTranslationX(int dx) {
		this.translationX += dx;
	}
	
	public int getTranslationY() {
		return translationY;
	}
	
	public void setTranslationY(int translationY) {
		this.translationY = translationY;
	}
	
	public void adjustTranslationY(int dy) {
		this.translationY += dy;
	}

	public Vertex getSelectedVertex() {
		return selectedVertex;
	}

	public void setSelectedVertex(Vertex selectedVertex) {
		this.selectedVertex = selectedVertex;
		if(selectedVertex != null)
			selectedVertex.resetForce();
	}

	public void setAllEdges(ArrayList<Edge> allEdges) {
		this.allEdges = allEdges;
	}


}
