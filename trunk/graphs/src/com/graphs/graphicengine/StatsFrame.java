package com.graphs.graphicengine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StatsFrame extends JFrame{
	private static final int WIDTH = 230;
	private static final int HEIGHT = 250;
	
	private Hashtable stats = new Hashtable();
	
	private Object mutex = new Object();
	
	private static boolean logsOn = true;
	
	private static StatsFrame instance =  new StatsFrame();
	
	public StatsFrame() {
		setSize(new Dimension(WIDTH, HEIGHT));
		setLocation(new Point((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-WIDTH, 50));
		setVisible(true);
		setTitle("Logs");
		getContentPane().add(new StatPanel());
	}
	
	public static StatsFrame getInstance(){
		return instance;
	}
	
	public void updateStat(String label, String value){
		if(!logsOn)
			return;
		stats.put(label, value);
		repaint();
	}
	
	class StatPanel extends JPanel{
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int i = 0;
			for(Enumeration en = stats.keys(); en.hasMoreElements();){
				String label = (String)en.nextElement();
				int strHeight = g.getFontMetrics().getHeight();
				int strLeading = g.getFontMetrics().getLeading();
				g.drawString(label + "=", 20, 20 + i * (strHeight + strLeading));
				g.drawString((String)stats.get(label) , (int)(25 + 
						g.getFontMetrics().getStringBounds(label, g).getWidth()), 
						20 + i * (strHeight + strLeading));
				
				i++;
			}
		}	
	}

	public static void setLogsOn(boolean logsOn) {
		StatsFrame.logsOn = logsOn;
	}
	
}