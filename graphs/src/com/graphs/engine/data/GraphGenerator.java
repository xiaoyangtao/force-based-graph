package com.graphs.engine.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class GraphGenerator {
	public static GraphContener generate(int vertexNum, int edgesNum){
		System.out.println("Generating graph v = " +  vertexNum + " e = " + edgesNum);
		GraphContener graph = new GraphContener("Generated_on_" + (new Date()).getTime());
		for(int v = 0; v < vertexNum;v++){
			try {
				graph.addVertex(String.valueOf(v));
			} catch (GraphException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Edge> allPossibleEdges = new ArrayList<Edge>();
		ArrayList<Vertex> vertexes;
		ArrayList<Vertex> copyVertexes = new ArrayList<Vertex>();
		
		vertexes = graph.getVertexes();
		for(int v = 0; v < vertexNum;v++){
			try {
				copyVertexes.add(new Vertex(String.valueOf(v)));
			} catch (GraphException e) {
				e.printStackTrace();
			}
		}
		
		
		for (Iterator iter = vertexes.iterator(); iter.hasNext();) {
			Vertex vStart = (Vertex) iter.next();
			copyVertexes.remove(vStart);
			for (Iterator iterator = copyVertexes.iterator(); iterator
					.hasNext();) {
				Vertex vEnd = (Vertex) iterator.next();
				try {
					Edge e = new Edge(vStart, vEnd);
					allPossibleEdges.add(e);
				} catch (GraphException e) {
					e.printStackTrace();
				}
			}
		}
		
		ArrayList<Edge> randomEdges = new ArrayList<Edge>();
		
		if(edgesNum > allPossibleEdges.size()){
			JOptionPane.showMessageDialog(null, "To many edges !", "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		for(int i = 0;i < edgesNum;i++){
			int sel = (int)((edgesNum-1) * Math.random());
			Edge e = allPossibleEdges.get(sel);
			allPossibleEdges.remove(sel);
			randomEdges.add(e);
		}
		
		graph.setAllEdges(randomEdges);
		
		return graph;
	}
	
	public static void saveGraph(GraphContener graph){
		System.out.println("Saving graph ...");
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(graph.getName() + ".xml"));
			try {
				out.write(graph.toXML());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Graph saved");
	}
}
