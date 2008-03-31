package pobr.dialogs;

import pobr.MainWindow;

	/**
	 *
	 * @author  Verne
	 */
	public class SampleDialog extends javax.swing.JDialog {
        
	    private javax.swing.JPanel buttonsPanel;
	    private javax.swing.JButton cancelButton;
	    private javax.swing.JPanel dataPanel;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JTextField jTextField1;
	    private javax.swing.JTextField jTextField2;
	    private javax.swing.JPanel mainPanel;
	    private javax.swing.JButton okButton;      
	    
		public void showMe(){
			setVisible(true);
		}
		
	    public SampleDialog() {
	        initComponents();
	        setResizable(false);
	    }
	    
		   private void initComponents() {
		        mainPanel = new javax.swing.JPanel();
		        buttonsPanel = new javax.swing.JPanel();
		        okButton = new javax.swing.JButton();
		        cancelButton = new javax.swing.JButton();
		        dataPanel = new javax.swing.JPanel();
		        jLabel2 = new javax.swing.JLabel();
		        jTextField1 = new javax.swing.JTextField();
		        jLabel1 = new javax.swing.JLabel();
		        jTextField2 = new javax.swing.JTextField();
		        
		        getContentPane().setLayout(new java.awt.FlowLayout());

		        mainPanel.setLayout(new java.awt.BorderLayout());

		        okButton.setText("   Ok  ");
		        buttonsPanel.add(okButton);

		        cancelButton.setText("Cancel");
		        buttonsPanel.add(cancelButton);

		        mainPanel.add(buttonsPanel, java.awt.BorderLayout.SOUTH);

		        dataPanel.setLayout(new java.awt.GridLayout(3, 3, 10, 15));

		        jLabel2.setText("Brightness");
		        dataPanel.add(jLabel2);

		        jTextField1.setText("brVal");
		        dataPanel.add(jTextField1);

		        jLabel1.setText("Additional");
		        dataPanel.add(jLabel1);

		        jTextField2.setText("adVal");
		        dataPanel.add(jTextField2);

		        mainPanel.add(dataPanel, java.awt.BorderLayout.CENTER);

		        getContentPane().add(mainPanel);

		        getContentPane().setLayout(new java.awt.FlowLayout());

		        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		        pack();
		    }// </editor-fold>                        
		    
		    /**
		     * @param args the command line arguments
		     */
		    public static void main(String args[]) {
		        java.awt.EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                new MainWindow().setVisible(true);
		            }
		        });
		    }
      

	}