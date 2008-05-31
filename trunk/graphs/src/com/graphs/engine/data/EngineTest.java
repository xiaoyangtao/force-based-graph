package com.graphs.engine.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.io.FilenameFilter;

import com.graphs.engine.algorithm.PhisicEngine;
import com.graphs.graphicengine.SettingsDialog;


public class EngineTest {
	public static void testEngine(){
		System.out.println("Starting engine test");
		File dir = new File("graphs\\"); 
		
		class XMLFilter implements FilenameFilter {
			
			public boolean accept(File dir, String name) {
			    return name.toLowerCase().endsWith(".xml");
			}

		}
		
		File [] fileList = dir.listFiles(new XMLFilter());
		
		
		if(fileList == null){
			JOptionPane.showMessageDialog(null, "Graph catalog is empty !!!");
			return;
		}
		
		File toDelete = new File("graphs\\RunTimes.txt");
		if(toDelete.exists()){
			toDelete.delete();
		}
		
		
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter("graphs\\RunTimes.txt"));//replace hellowrold.txt with the name of the file

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		for(int i = 0; i < fileList.length;i++){
			GraphContener graph = GraphLoader.load(fileList[i]);
			PhisicEngine p = new PhisicEngine();
			
			RunResult res = p.processGraph(graph);
			try {
				out.write(fileList[i].getName() + "\t" + String.valueOf(res.getTime()) + "\n");
				out.write("Engine params: gravity = " + SettingsDialog.getGravityConst() + ", " +
						"hook = " + SettingsDialog.getHookConst() + ", damping = " + SettingsDialog.getDampingConst() + "\n");
			} catch (IOException e1) {				
				e1.printStackTrace();
			}
			try {
				File imgFile = new File("graphs\\" +fileList[i].getName().substring(0, fileList[i].getName().length() - 3)+ "jpg");
				if(imgFile.exists()){
					imgFile.delete();
				}
				ImageIO.write(res.getImage(), "jpg", new File("graphs\\" +fileList[i].getName().substring(0, fileList[i].getName().length() - 3)+ "jpg") );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Engine test finished");
	}
}
