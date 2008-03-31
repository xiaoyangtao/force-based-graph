package com.graphs.engine.algorithm;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.Timer;

import com.graphs.engine.data.Edge;
import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.Vertex;

public class PhisicEngine {
	private final int DELAY = 80;
	
	private double GRAVITY_MULTIPLAYER = 2000;
	private double HOOKE_K = 0.3;
	private double DUMPING = 0.8;
	
	private double SPRING_MINIMAL_LENGTH = 80; 
	
	GraphContener graphContener;
	
	Timer runner;
	
	private void initCoords(){
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator(); iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			//v.setX(200);
			//v.setY(200);
		}
	}
	
	private void recalcVectors(){
		
		// Gravity
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator(); iter.hasNext();){
			Vertex base = (Vertex)iter.next();
			if(base == graphContener.getSelectedVertex())
				continue;
			double sumGravityFx = 0;
			double sumGravityFy = 0;
			
			for(Iterator<Vertex> iter2 = graphContener.getVertexes().iterator(); iter2.hasNext();){
				Vertex other = (Vertex)iter2.next();
				if(other == base)
					continue;
				double dist = Math.sqrt(Math.pow(base.getX() - other.getX(),2) + Math.pow(base.getY() -other.getY(),2));
				if (dist == 0){
					System.out.println("Distance is 0 !!!");
					continue;
				}
				//System.out.println("Distance between " + base.getId() + " and " + other.getId() + " is " + dist);
				double gravityF = (double)1/Math.pow(dist, 2) * GRAVITY_MULTIPLAYER;
				
				double gravityFx = gravityF * (-other.getX() + base.getX()) / dist;
				double gravityFy = gravityF * (-other.getY() + base.getY()) / dist;
								
				sumGravityFx += gravityFx;
				sumGravityFy += gravityFy;
				
			}
			//System.out.println("Gravity force for " + base.getId() + " Fx = " + sumGravityFx + " Fy = " + sumGravityFx);
			
			
			// Hook
			
			double sumHookeFx = 0;
			double sumHookeFy = 0;

			for(Iterator<Edge> iter2 = base.getEdges().iterator(); iter2.hasNext();){
				Edge e = (Edge)iter2.next();
				Vertex other=null;
				if(e.getA() == base){
					other = e.getB();
				}
				else if(e.getB() == base){
					other = e.getA();
				}
				else{
					System.out.println("Error - corrupted edge !!!");
					continue;
				}
				
				if(other == base){
					System.out.println("Error - base and other are the same vertex !!!");
					continue;
				}
				
				double dist = Math.sqrt(Math.pow(base.getX() - other.getX(),2) + Math.pow(base.getY() -other.getY(),2));
				if (dist == 0){
					System.out.println("Distance is 0 !!!");
					continue;
				}
				
				double hookeF = -(double)HOOKE_K * (dist - SPRING_MINIMAL_LENGTH);
				
				double hookeFx = hookeF * (-other.getX() + base.getX()) / dist;
				double hookeFy = hookeF * (-other.getY() + base.getY()) / dist;
				
				sumHookeFx += hookeFx;
				sumHookeFy += hookeFy;
			}
			
			// alltogether
			base.setDx(sumGravityFx + sumHookeFx);
			base.setDy(sumGravityFy + sumHookeFy);
			
			// only gravity force
			//base.setDx(sumGravityFx);
			//base.setDy(sumGravityFy);
			
			// only hooke force
			//base.setDx(sumHookeFx);
			//base.setDy(sumHookeFy);

		}
			
	}
	
	private void recalcCoords(){
		for(Iterator<Vertex> iter = graphContener.getVertexes().iterator(); iter.hasNext();){
			Vertex v = (Vertex)iter.next();
			//System.out.println("Moving " + v.getId() + " from " + v.getX() + "," + v.getY() + " vector " + v.getDx() + "," + v.getDy());
			v.setX(v.getX() + v.getDx());
			v.setY(v.getY() + v.getDy());
		}		
	}
	
	private void initRunner(){
		runner = new Timer(DELAY,
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						recalcVectors();
						recalcCoords();
					}
				}
				);
	}
	
	public void start(){
		runner.start();
	}
	
	public void stop(){
		runner.stop();
	}
	
	public PhisicEngine(GraphContener graphContener) {
		this.graphContener = graphContener;
		initCoords();
		initRunner();
	}
	

}
