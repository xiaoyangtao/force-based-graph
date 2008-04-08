package com.graphs.graphicengine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.graphs.engine.data.GraphContener;
import com.graphs.engine.data.Settings;

public class GraphFrame extends JFrame {
	private int FRAME_WIDTH = 800;
	private int FRAME_HEIGHT = 600;
	private final String APP_TITLE = "Graphs";
	
	GraphPanel graphPanel;
	
	private void initFrameSettings(){
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		// calculating frame position - should be placed in the middle
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getSize().width / 2 ,
					Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getSize().height / 2
					);
		setTitle(APP_TITLE);
	}
	
	private void addSubComponents(GraphContener data){
		graphPanel = new GraphPanel(data);
		add(graphPanel);
	}
	
	public GraphFrame(GraphContener data) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initFrameSettings();
		addSubComponents(data);
		setVisible(true);
	}
	
}
