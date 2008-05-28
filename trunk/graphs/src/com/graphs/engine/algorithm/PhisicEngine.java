package com.graphs.engine.algorithm;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.util.Date;
import java.util.Iterator;

import javax.swing.Timer;

import com.graphs.engine.data.Edge;
import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.RunResult;
import com.graphs.engine.data.Vertex;
import com.graphs.graphicengine.GraphDrawer;
import com.graphs.graphicengine.SettingsDialog;
import com.graphs.graphicengine.StatsFrame;

public class PhisicEngine {
	private final int DELAY = 80;
	
	//private double GRAVITY_MULTIPLAYER = 800;
	//private double HOOKE_K = 0.3;
	//private double DAMPING = 0.7;
	
	private double SPRING_MINIMAL_LENGTH = 80; 
	
	private GraphContener graphContener;
	
	private Timer animationRunner;
	
	private Thread normalRunner;
	private boolean stopNormalRunner = false;
	
	private double kinetic = 0;
	
	private double getGravityMultiplayer(){
		return SettingsDialog.getGravityConst();
	}
	
	private double getHookConst(){
		return SettingsDialog.getHookConst();
	}
	
	private double getDamping(){
		return SettingsDialog.getDampingConst();
	}
	
	private double getSpringMinimalLen(){
		return SPRING_MINIMAL_LENGTH;
	}
	
	public void initCoords(){
		if(!graphContener.isHasCoordinates()){
			int vCount = graphContener.getVertexes().size();
			double R = (3.0 * vCount * Vertex.VERTEX_SIZE)/ (2.0 * Math.PI);
			double alfa = 0;
			int num = 0;
			for(Iterator<Vertex> iter = graphContener.getVertexes().iterator(); iter.hasNext();){
				Vertex v = (Vertex)iter.next();
				alfa = (2.0*Math.PI/vCount) * (double)num;
				int sgnX = 1;
				int sgnY = -1;
				double x = sgnX * R * Math.cos(alfa) + R + Vertex.VERTEX_SIZE;
				double y = sgnY * R * Math.sin(alfa) + R + Vertex.VERTEX_SIZE;
				
				v.setX((int)x);
				v.setY((int)y);
				num++;
			}
		}
	}
	
	private void recalcVectors(){
		kinetic = 0;
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
				double gravityF = (double)1/Math.pow(dist, 2) * getGravityMultiplayer();
				
				kinetic += Math.abs(gravityF);
				
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
				
				double hookeF = -(double)getHookConst() * (dist - getSpringMinimalLen());
				
				kinetic += Math.abs(hookeF);
				
				double hookeFx = hookeF * (-other.getX() + base.getX()) / dist;
				double hookeFy = hookeF * (-other.getY() + base.getY()) / dist;
				
				sumHookeFx += hookeFx;
				sumHookeFy += hookeFy;
			}
			
			// alltogether
			base.setDx((base.getDx() + sumGravityFx + sumHookeFx) * getDamping());
			base.setDy((base.getDy() + sumGravityFy + sumHookeFy) * getDamping());
			
		}
		
		StatsFrame.getInstance().updateStat("Energy", String.valueOf(kinetic));
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
		System.out.println("Initiating runners");
		animationRunner = new Timer(DELAY,
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						runAlgorithmStep();
					}
				}
				);
		normalRunner = new Thread(){
			public void run() {
				runAlgorithm();
			}
		};
	}
	
	private void runAlgorithmStep(){
		recalcVectors();
		recalcCoords();
	}
	
	private long runPureAlgorithm(){
		double minChange = 0.0001; 
		
		kinetic = 0;
		double oldKinetic = 0;

		Date start = new Date();
		while(kinetic == 0  || Math.abs((double)(oldKinetic - kinetic))/(double)kinetic > minChange){
			if(kinetic != 0){
				oldKinetic = kinetic;
			}
			else
				oldKinetic = Integer.MAX_VALUE;
						
			runAlgorithmStep();
		}
		long runTime = (new Date()).getTime() - start.getTime();
		
		return runTime;
	}
	
	private void runAlgorithm(){
		//StatsFrame.setLogsOn(false);
		
		double minChange = 0.0001; 
		
		kinetic = 0;
		double oldKinetic = 0;

		Date start = new Date();
		while(kinetic == 0  || Math.abs((double)(oldKinetic - kinetic))/(double)kinetic > minChange){
			StatsFrame.getInstance().updateStat("En. delta",String.valueOf(Math.abs((double)(oldKinetic - kinetic))/(double)kinetic));
			StatsFrame.getInstance().updateStat("Energy", String.valueOf(kinetic));
			long runTime = (new Date()).getTime() - start.getTime();
			StatsFrame.getInstance().updateStat("Timer", String.valueOf(runTime));

			if(kinetic != 0){
				oldKinetic = kinetic;
			}
			else
				oldKinetic = Integer.MAX_VALUE;
			
			if(stopNormalRunner){
				System.out.println("Phisic thread ended");
				return;
			}
			
			runAlgorithmStep();
		}
		StatsFrame.setLogsOn(true);
		long runTime = (new Date()).getTime() - start.getTime();

		StatsFrame.getInstance().updateStat("Last run", String.valueOf(runTime));
	}
	
	
	public void startAnimation(){
		animationRunner.start();
	}
	
	public void startNormal(){
		stopNormalRunner = false;
		normalRunner.start();
	}
	
	public void stopAnimation(){
		animationRunner.stop();
	}
	
	
	public void stopNormal(){
		stopNormalRunner = true;
		initRunner();
	}
	
	
	public RunResult processGraph(GraphContener graphContener){
		RunResult result = new RunResult();
		this.graphContener = graphContener;
		initCoords();
		long time = runPureAlgorithm();
		result.setTime(time);
		
		BufferedImage img = GraphDrawer.generateImage(graphContener);
		result.setImage(img);
		return result;
	}
	
	
	public PhisicEngine() {
		
	}
	
	public PhisicEngine(GraphContener graphContener) {
		this.graphContener = graphContener;
		initCoords();
		initRunner();
	}

	public void setGraphContener(GraphContener graphContener) {
		this.graphContener = graphContener;
	}
	

}
