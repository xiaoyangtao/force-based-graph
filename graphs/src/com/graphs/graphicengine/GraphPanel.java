package com.graphs.graphicengine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.graphs.engine.algorithm.PhisicEngine;
import com.graphs.engine.data.Edge;
import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.GraphException;
import com.graphs.engine.data.Vertex;

public class GraphPanel extends JPanel{
	private final int FRAMES_PER_SECOND = 50; 	
	
	private final String SCREENSHOOT_FILENAME = "scr.jpg";
	
	private final int SCROLL_SPEED = 15;
	
	JButton startAnimated = new JButton("Start Animated");
	JButton startNormal = new JButton("Start Normal");
	
	Timer repainter;
	
	GraphContener graphContener;
	
	PhisicEngine engine;

	// dummy edge
	Edge newEdge = null;
	
	int newEdgeX;
	int newEdgeY;	
	
	public GraphPanel(GraphContener data) {
		setBackground(Color.white);
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e) {
				if(graphContener.getSelectedVertex()!= null && newEdge == null){
					graphContener.getSelectedVertex().setX(e.getX() - graphContener.getTranslationX());
					graphContener.getSelectedVertex().setY(e.getY() - graphContener.getTranslationY());					
				}
				if(newEdge != null){
					newEdgeX = e.getX() - graphContener.getTranslationX();
					newEdgeY = e.getY() - graphContener.getTranslationY();
				}
			}
		});
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				for(Iterator<Vertex> iter = graphContener.getVertexes().iterator(); iter.hasNext();){
					Vertex v = (Vertex)iter.next();
					if(v.contains(e.getX()-graphContener.getTranslationX(), e.getY()-graphContener.getTranslationY())){
						graphContener.setSelectedVertex(v);
					}
				}
				// left button
				if(e.getButton() == MouseEvent.BUTTON1){
					return;
				}
				// right button
				if(e.getButton() == MouseEvent.BUTTON3){
					try {
						//dummy edge
						newEdge = new Edge(graphContener.getSelectedVertex(),graphContener.getSelectedVertex());
						newEdgeX = (int)graphContener.getSelectedVertex().getX();
						newEdgeY = (int)graphContener.getSelectedVertex().getY();
					} catch (GraphException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
			public void mouseReleased(MouseEvent e) {
				if(newEdge!= null){
					Vertex endVertex = null;
					for(Iterator<Vertex> iter = graphContener.getVertexes().iterator(); iter.hasNext();){
						Vertex v = (Vertex)iter.next();
						if(v.contains(e.getX()-graphContener.getTranslationX(), e.getY()-graphContener.getTranslationY())){
							endVertex = v;
						}
					}
					if(endVertex != null){
						try {
							graphContener.addEdge(newEdge.getA(), endVertex);
						} catch (GraphException e1) {
							e1.printStackTrace();
						}
					}
				}
				graphContener.setSelectedVertex(null);
				newEdge = null;
			}
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					saveScreenshot();
				}
			}
		});
		
		setLayout(new BorderLayout());
		
		graphContener = data;
		engine = new PhisicEngine(data);
		prepareRepainter();
		repainter.start();
		
		
		startAnimated.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(startAnimated.getText().equals("Start Animated")){
					startAnimated.setText("Stop Animated");
					engine.startAnimation();
					startNormal.setEnabled(false);
				}
				else{
					startAnimated.setText("Start Animated");
					engine.stopAnimation();
					startNormal.setEnabled(true);
				}
			}
		});
		
		startNormal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(startNormal.getText().equals("Start Normal")){
					startNormal.setText("Stop Normal");
					engine.startNormal();
					startAnimated.setEnabled(false);
				}
				else{
					startNormal.setText("Start Normal");
					engine.stopNormal();
					startAnimated.setEnabled(true);
				}
			}
		});
		
		
		JPanel buttonContener = new JPanel();
		buttonContener.add(startAnimated);
		buttonContener.add(startNormal);
		
		add(buttonContener, BorderLayout.SOUTH);

		
		KeyAdapter adapter = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					getGraphContener().adjustTranslationX(-SCROLL_SPEED);
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					getGraphContener().adjustTranslationX(SCROLL_SPEED);
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					getGraphContener().adjustTranslationY(SCROLL_SPEED);
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					getGraphContener().adjustTranslationY(-SCROLL_SPEED);
				}
			}
		};
		
		startNormal.addKeyListener(adapter);
		startAnimated.addKeyListener(adapter);
		
		
	}

	private void saveScreenshot(){
		BufferedImage buff = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		buff.getGraphics().setColor(Color.white);
		buff.getGraphics().fillRect(0, 0, getWidth(), getHeight());
		drawGraph((Graphics2D)buff.getGraphics());
		try {
			ImageIO.write(buff, "jpg", new File(SCREENSHOOT_FILENAME));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(GraphPanel.this, "Screenshot saved");
	}
	
	private void prepareRepainter(){
		repainter = new Timer(1000/FRAMES_PER_SECOND, 
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						repaint();
					}
				}
			);	
	}
	
	private void drawTime(Graphics2D g){
		Date now = new Date();
		g.drawString(DateFormat.getTimeInstance().format(now), 20, 20);
	}
	
	private void drawGraph(Graphics2D g){
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
		
		// new edge
		g.setColor(Color.black);
		if(newEdge != null){
			g.drawLine((int)newEdge.getA().getX(), (int)newEdge.getA().getY(), newEdgeX, newEdgeY);
		}
		
		//restore transformation
		
		g.setTransform(saveAT);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawTime((Graphics2D)g);
		drawGraph((Graphics2D)g);
	}
	
	public GraphContener getGraphContener() {
		return graphContener;
	}
	
}
