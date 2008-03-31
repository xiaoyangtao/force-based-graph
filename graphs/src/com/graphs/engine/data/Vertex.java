package com.graphs.engine.data;

import java.util.ArrayList;

public class Vertex {
	private String id;
	
	private double x;
	private double y;
	
	private double dx;
	private double dy;
	
	private ArrayList<Edge> edges = new ArrayList<Edge>();  
	
	public Vertex(String id) throws GraphException{
		if(id == null){
			throw new GraphException("Id is null");
		}
		this.id = id;
	}
	
	public void addEdge(Edge e){
		edges.add(e);
	}
	
	public String getId() {
		return id;
	}
	
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getDx() {
		return dx;
	}
	
	public double getDy() {
		return dy;
	}
	
	public void setDx(double dx) {
		this.dx = dx;
	}
	
	public void setDy(double dy) {
		this.dy = dy;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
}
