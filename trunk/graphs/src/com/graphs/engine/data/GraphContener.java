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
		Edge e = new Edge(a, b);
		a.addEdge(e);
		b.addEdge(e);
		allEdges.add(e);
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


}
