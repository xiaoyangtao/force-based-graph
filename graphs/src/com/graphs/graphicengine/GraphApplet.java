package com.graphs.graphicengine;

import javax.swing.JApplet;

import com.graphs.engine.data.GraphLoader;
import com.graphs.engine.data.Settings;

public class GraphApplet extends JApplet {

	GraphPanel graphPanel;
	
	public void init() {
		Settings.XMLFileName = "../graph.xml";
		graphPanel = new GraphPanel(GraphLoader.load());
		add(graphPanel);
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
	
}
