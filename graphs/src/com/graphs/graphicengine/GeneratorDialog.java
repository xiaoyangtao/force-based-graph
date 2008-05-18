package com.graphs.graphicengine;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import com.graphs.engine.data.GraphGenerator;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class GeneratorDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel CenterPanel = null;

	private JLabel numVertexLabel = null;

	private JLabel numEdgesLabel = null;

	private JTextField numVertex = null;

	private JTextField numEdges = null;

	private JLabel numGraphsLabel = null;

	private JButton Generate = null;

	private JTextField numGraphs = null;

	private static WindowAdapter onClose = new WindowAdapter(){
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			System.exit(0);
		}

	};
	
	/**
	 * @param owner
	 */
	public GeneratorDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getCenterPanel(), BorderLayout.CENTER);
			jContentPane.add(getGenerate(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes CenterPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCenterPanel() {
		if (CenterPanel == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints11.gridy = 2;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints4.ipady = 10;
			gridBagConstraints4.gridy = 2;
			numGraphsLabel = new JLabel();
			numGraphsLabel.setText("Graphs count");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.ipady = 10;
			gridBagConstraints1.insets = new Insets(0, 6, 0, 10);
			gridBagConstraints1.gridy = 1;
			numEdgesLabel = new JLabel();
			numEdgesLabel.setText("Edges count");
			numEdgesLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			numEdgesLabel.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipady = 10;
			gridBagConstraints.ipadx = 0;
			gridBagConstraints.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints.gridy = 0;
			numVertexLabel = new JLabel();
			numVertexLabel.setText("Vertex count");
			CenterPanel = new JPanel();
			CenterPanel.setLayout(new GridBagLayout());
			CenterPanel.add(numVertexLabel, gridBagConstraints);
			CenterPanel.add(numEdgesLabel, gridBagConstraints1);
			CenterPanel.add(getNumVertex(), gridBagConstraints2);
			CenterPanel.add(getNumEdges(), gridBagConstraints3);
			CenterPanel.add(numGraphsLabel, gridBagConstraints4);
			CenterPanel.add(getNumGraphs(), gridBagConstraints11);
		}
		return CenterPanel;
	}

	/**
	 * This method initializes numVertex	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumVertex() {
		if (numVertex == null) {
			numVertex = new JTextField();
			numVertex.setPreferredSize(new Dimension(100, 20));
			numVertex.setToolTipText("");
			numVertex.setText("10");
		}
		return numVertex;
	}

	/**
	 * This method initializes numEdges	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumEdges() {
		if (numEdges == null) {
			numEdges = new JTextField();
			numEdges.setPreferredSize(new Dimension(100, 20));
			numEdges.setText("5");
		}
		return numEdges;
	}

	/**
	 * This method initializes Generate	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getGenerate() {
		if (Generate == null) {
			Generate = new JButton();
			Generate.setText("Generate");
			Generate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed() - generating"); 
					GraphGenerator.prepareCatalog();
					for(int i = 0; i < Integer.parseInt(numGraphs.getText()); i++){
						GraphGenerator.saveGraph(GraphGenerator.generate(Integer.parseInt(numVertex.getText()), Integer.parseInt(numEdges.getText()), i) );
					}
				}
			});
		}
		return Generate;
	}

	/**
	 * This method initializes numGraphs	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumGraphs() {
		if (numGraphs == null) {
			numGraphs = new JTextField();
			numGraphs.setPreferredSize(new Dimension(100, 20));
			numGraphs.setText("1");
		}
		return numGraphs;
	}

	public static void main(String[] args) {
		GeneratorDialog generator = new GeneratorDialog(null);
		generator.addWindowListener(onClose);
		generator.setVisible(true);
		
	}

}
