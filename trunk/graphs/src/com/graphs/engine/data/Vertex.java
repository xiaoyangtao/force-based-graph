package com.graphs.engine.data;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Vertex {
	
	private String id;
	
	private double x;
	private double y;
	
	private double dx;
	private double dy;
	
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public final static int VERTEX_SIZE = 22;  
	
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
	
	public void resetForce(){
		setDx(0);
		setDy(0);
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
	
	public boolean contains(int px, int py){
		Ellipse2D circle = new Ellipse2D.Double(x-VERTEX_SIZE/2,y-VERTEX_SIZE/2,VERTEX_SIZE,VERTEX_SIZE);
		if(circle.contains(px, py)){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vertex){
			return ((Vertex)obj).getId().equals(id);
		}
		return false;
	}
	
}
