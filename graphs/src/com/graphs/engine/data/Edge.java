package com.graphs.engine.data;

public class Edge {
	private Vertex a;
	private Vertex b;
	
	public Edge(Vertex a, Vertex b) throws GraphException{
		if(a == null || b == null) {
			throw new GraphException("One of following is null : a = " + a + " b = " + b);
		}
		this.a = a;
		this.b = b;
	}

	public void setA(Vertex a) {
		this.a = a;
	}
	
	public void setB(Vertex b) {
		this.b = b;
	}
	
	public Vertex getA() {
		return a;
	}
	
	public Vertex getB() {
		return b;
	}
}
