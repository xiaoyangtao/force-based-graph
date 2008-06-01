package com.graphs.graphicengine;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JSlider;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class SettingsDialog extends JFrame {

	private static double GRAVITY_const = 200000.0;
	private static double HOOKE_K_const = 700.0;
	private static double DAMPING_const = 100.0;
	
	public static int MAX_GRAVITY = 1000000;
	public static int MAX_HOOKE_K = 1500;
	public static int MAX_DAMPING = 1000;
	
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel centerPanel = null;

	private static JSlider gravity = null;

	private JLabel gravityLabel = null;

	private JLabel hookLabel = null;

	private static JSlider hook = null;

	private JLabel dampingLabel = null;

	private static JSlider damping = null;

	private static JTextField gravityValue = null;

	private static JTextField hookValue = null;

	private static JTextField dampingValue = null;
	private JLabel jLabel = null;
	private static JCheckBox resolveCrossed = null;
	/**
	 * This is the default constructor
	 */
	public SettingsDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 186);
		this.setLocation(new Point(0, 70));
		this.setContentPane(getJContentPane());
		this.setTitle("Engine Parameters");
		gravity.setValue((int)GRAVITY_const);
		hook.setValue((int)HOOKE_K_const);
		damping.setValue((int)DAMPING_const);
		
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
		}
		return jContentPane;
	}
	
	public static void getInstance(){
		SettingsDialog s = new SettingsDialog();
		s.setVisible(true);
	}

	/**
	 * This method initializes centerPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.gridy = 6;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridx = 0;
			gridBagConstraints32.gridy = 6;
			jLabel = new JLabel();
			jLabel.setText("Resolve crossed edges");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints41.gridy = 5;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.gridx = 1;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints5.gridy = 5;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridx = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 4;
			dampingLabel = new JLabel();
			dampingLabel.setText("Damping");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 2;
			hookLabel = new JLabel();
			hookLabel.setText("Hook const");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gravityLabel = new JLabel();
			gravityLabel.setText("Gravity");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.gridx = 0;
			centerPanel = new JPanel();
			centerPanel.setLayout(new GridBagLayout());
			centerPanel.add(getGravity(), gridBagConstraints);
			centerPanel.add(gravityLabel, gridBagConstraints1);
			centerPanel.add(hookLabel, gridBagConstraints2);
			centerPanel.add(getHook(), gridBagConstraints3);
			centerPanel.add(dampingLabel, gridBagConstraints4);
			centerPanel.add(getDamping(), gridBagConstraints5);
			centerPanel.add(getGravityValue(), gridBagConstraints11);
			centerPanel.add(getHookValue(), gridBagConstraints31);
			centerPanel.add(getDampingValue(), gridBagConstraints41);
			centerPanel.add(jLabel, gridBagConstraints32);
			centerPanel.add(getResolveCrossed(), gridBagConstraints51);
		}

		return centerPanel;
	}

	/**
	 * This method initializes gravity	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getGravity() {
		if (gravity == null) {
			gravity = new JSlider(1, MAX_GRAVITY);
			gravity.setPaintLabels(true);
			gravity.setPaintTicks(false);
			gravity.setName("Gravity");
			gravity.setPreferredSize(new Dimension(200, 20));
			gravity.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					GRAVITY_const = gravity.getValue();
					gravityValue.setText(String.valueOf(GRAVITY_const));
				}
			});
			
		}
		return gravity;
	}

	/**
	 * This method initializes hook	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getHook() {
		if (hook == null) {
			hook = new JSlider(1,MAX_HOOKE_K);
			hook.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					HOOKE_K_const = hook.getValue()/1000.0;
					hookValue.setText(String.valueOf(HOOKE_K_const));
				}
			});
		}
		return hook;
	}

	/**
	 * This method initializes damping	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getDamping() {
		if (damping == null) {
			damping = new JSlider(0,MAX_DAMPING);
			damping.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					DAMPING_const = damping.getValue()/1000.0;
					dampingValue.setText(String.valueOf(DAMPING_const));
				}
			});
		}
		return damping;
	}

	/**
	 * This method initializes gravityValue	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getGravityValue() {
		if (gravityValue == null) {
			gravityValue = new JTextField();
			gravityValue.setPreferredSize(new Dimension(55, 20));

		}
		return gravityValue;
	}

	/**
	 * This method initializes hookValue	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getHookValue() {
		if (hookValue == null) {
			hookValue = new JTextField();
			hookValue.setPreferredSize(new Dimension(55, 20));
		}
		return hookValue;
	}

	public static double getGravityConst(){
		return GRAVITY_const;
	}
	
	public static double getHookConst(){
		return HOOKE_K_const;
	}
	
	public static double getDampingConst(){
		return DAMPING_const;
	}
	
	public static void setGravityConst(double val){
		val = Math.min(val,MAX_GRAVITY);
		GRAVITY_const = val;
		if(gravity!= null){
			gravity.setValue((int)val);
			gravityValue.setText(String.valueOf((int)val));
			gravity.getParent().repaint();
		}
	}
	
	public static void setHookConst(double val){
		val = Math.min(val,MAX_HOOKE_K);
		HOOKE_K_const = val;
		if(hook != null){
			hook.setValue((int)(val * 1000.0));
			hookValue.setText(String.valueOf(val));
			hook.getParent().repaint();
		}
	}
	
	public static void setDampingConst(double val){
		val = Math.min(val,MAX_DAMPING);
		DAMPING_const = val;
		if(damping != null){
			damping.setValue((int)(val * 1000.0));
			System.out.println((int)(val * 1000.0));
			dampingValue.setText(String.valueOf(val));
		}
	}	
	
	public static boolean getResolveCrossedEdges(){
		return resolveCrossed.isSelected();
	}
	
	public static void setResolveCrossedEdges(boolean resolve){
		resolveCrossed.setSelected(resolve);
	}
	
	/**
	 * This method initializes dampingValue	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDampingValue() {
		if (dampingValue == null) {
			dampingValue = new JTextField();
			dampingValue.setPreferredSize(new Dimension(55, 20));

		}
		return dampingValue;
	}

	/**
	 * This method initializes resolveCrossed	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getResolveCrossed() {
		if (resolveCrossed == null) {
			resolveCrossed = new JCheckBox();
		}
		return resolveCrossed;
	}

}  //  @jve:decl-index=0:visual-constraint="16,10"
