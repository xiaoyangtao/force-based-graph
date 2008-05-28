package com.graphs.graphicengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import com.graphs.engine.data.Edge;
import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.Vertex;

public class GraphDrawer {
	
	private static int getMaxHeight(GraphContener graphContener){
		int maxH = 0;
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v.getY() > maxH){
				maxH = (int)v.getY();
			}
		}
		return maxH;
	}
	
	private static int getMaxWidth(GraphContener graphContener){
		int maxW = 0;
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v.getX() > maxW){
				maxW = (int)v.getX();
			}
		}
		return maxW;
	}
	
	public static BufferedImage generateImage(GraphContener graphContener){
		BufferedImage buff = new BufferedImage(getMaxWidth(graphContener) + Vertex.VERTEX_SIZE, getMaxHeight(graphContener) + Vertex.VERTEX_SIZE, BufferedImage.TYPE_INT_RGB);
		buff.getGraphics().setColor(Color.white);
		buff.getGraphics().fillRect(0, 0, getMaxWidth(graphContener) + Vertex.VERTEX_SIZE, getMaxHeight(graphContener) + Vertex.VERTEX_SIZE);
		drawGraph((Graphics2D)buff.getGraphics(), graphContener);
		return buff;
	}
	
	public static void drawGraph(Graphics2D g, GraphContener graphContener){
		// save old ransformation
		AffineTransform saveAT = g.getTransform();
		
		g.translate(graphContener.getTranslationX(), graphContener.getTranslationY());
		for(Iterator<Edge> iter = graphContener.getAllEdges().iterator();iter.hasNext();){
			Edge v = (Edge)iter.next();
			g.setColor(Color.black);
			g.drawLine((int)v.getA().getX(), (int)v.getA().getY(), 
					(int)v.getB().getX(), (int)v.getB().getY());
		}

		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator();iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			if(v == graphContener.getSelectedVertex())
				g.setColor(Color.red);
			else
				g.setColor(Color.green);
			g.fillOval((int)(v.getX() - Vertex.VERTEX_SIZE/2), (int)(v.getY() - Vertex.VERTEX_SIZE/2), Vertex.VERTEX_SIZE, Vertex.VERTEX_SIZE);
			g.setColor(Color.black);
			g.drawOval((int)(v.getX() - Vertex.VERTEX_SIZE/2), (int)(v.getY() - Vertex.VERTEX_SIZE/2), Vertex.VERTEX_SIZE, Vertex.VERTEX_SIZE);
			if(v.getId().length() == 2)
				g.drawString(v.getId(), (int)(v.getX()-7), (int)(v.getY() + Vertex.VERTEX_SIZE/2-6));
			else if (v.getId().length() == 1)
				g.drawString(v.getId(), (int)(v.getX()-3), (int)(v.getY() + Vertex.VERTEX_SIZE/2-6));
			else
				g.drawString(v.getId().substring(0,1) + "..", (int)(v.getX()-7), (int)(v.getY() + Vertex.VERTEX_SIZE/2-6));
		}

		//restore transformation
		
		g.setTransform(saveAT);
	}
}
