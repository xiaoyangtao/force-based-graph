package com.graphs.engine.data;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.io.FilenameFilter;
import java.text.DecimalFormat;

import com.graphs.engine.algorithm.PhisicEngine;
import com.graphs.graphicengine.SettingsDialog;

public class EngineTest {
	public static void testEngine(
			Component frame/*
							 * component neccesary for JOptionPane - yes/no
							 * dialog
							 */,
			double gravity, double hookConst) {
		System.out.println("Starting engine test");
		File dir = new File("graphs\\");

		class XMLFilter implements FilenameFilter {

			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}

		}

		File[] fileList = dir.listFiles(new XMLFilter());

		if (fileList == null) {
			JOptionPane.showMessageDialog(null, "Graph catalog is empty !!!");
			return;
		}

		File toDelete = new File("graphs\\RunTimes.txt");
		if (toDelete.exists()) {
			toDelete.delete();
		}

		BufferedWriter out = null;
		BufferedWriter out2 = null;
		try {
			out = new BufferedWriter(new FileWriter("graphs\\RunTimes.txt"));// replace
																				// hellowrold.txt
																				// with
																				// the
																				// name
																				// of
																				// the
																				// file
			out2 = new BufferedWriter(new FileWriter("crossedEdges.txt", true));// replace
																				// hellowrold.txt
																				// with
																				// the
																				// name
																				// of
																				// the
																				// file

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		boolean estimate = true;
		/*
		 * int resp = JOptionPane.showInternalConfirmDialog(frame, "Estimate
		 * engine parameters automatically ?", "Test engine",
		 * JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE); if(resp ==
		 * JOptionPane.YES_OPTION){ estimate = true; }
		 */
		double averageRatio = 0;
		DecimalFormat df = new DecimalFormat("0.000");
		for (int i = 0; i < fileList.length; i++) {
			GraphContener graph = GraphLoader.load(fileList[i]);
			PhisicEngine p = new PhisicEngine();
			SettingsDialog.setGravityConst(gravity);
			SettingsDialog.setHookConst(hookConst);
			RunResult res = p.processGraph(graph, estimate);
			try {
				out.write(fileList[i].getName() + "\t"
						+ String.valueOf(res.getTime()) + "\n");
				out.write("Engine params: gravity = "
						+ SettingsDialog.getGravityConst() + ", " + "hook = "
						+ SettingsDialog.getHookConst() + ", damping = "
						+ SettingsDialog.getDampingConst() + "\n");
				out.write("Crossed edges:" + graph.getCrossedEdgesCount());
				double ratio = ((double) graph.getCrossedEdgesCount() / (double) graph
						.getAllEdges().size());
				averageRatio += ratio;
				out.write("\nCrossed edges to edges ratio:" + df.format(ratio));
				out.write("\n===================\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				File imgFile = new File("graphs\\"
						+ fileList[i].getName().substring(0,
								fileList[i].getName().length() - 3) + "jpg");
				if (imgFile.exists()) {
					imgFile.delete();
				}
				ImageIO.write(res.getImage(), "jpg", new File("graphs\\"
						+ fileList[i].getName().substring(0,
								fileList[i].getName().length() - 3) + "jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			out.write("\nAverage crossed edges to edges ratio:"
					+ df.format(averageRatio / fileList.length));
			out2.write("\n" + df.format(gravity) + "	" + df.format(hookConst)
					+ "		" + df.format(averageRatio / fileList.length));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			out.close();
			out2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Engine test finished");
	}
}
