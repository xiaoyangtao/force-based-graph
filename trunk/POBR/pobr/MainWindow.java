package pobr;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pobr.dialogs.AboutDialog;


public class MainWindow extends JFrame implements ActionListener{
	static final String Title = "Image Processing Toolkit";
	
	JDesktopPane desktop = new JDesktopPane();
	JMenuBar menuBar = new JMenuBar();
	
	public void actionPerformed(ActionEvent e) {
		String sel = e.getActionCommand();
		if(sel.equals("Exit")){
			dispose();
		}
		if(sel.equals("Open image")){
			openImage();
		}
		if(sel.equals("About")){
			AboutDialog aDial = new AboutDialog(this);
			aDial.showMe();
		}
	}
	
	public void openImage(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("File to open : " + file.getPath() + " (" + file.getName() + ")");
            Image2Process img;
			try {
				img = new Image2Process(file);
	            ImageView view = new ImageView(file.getName(), img, this);
	            desktop.add(view);
	            view.showMe();
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else {
            
        }
	}
	
	public void buildMenu(){
		JMenu menuFile = new JMenu("File");
		JMenuItem open_image = new JMenuItem("Open image");
		JMenuItem save_image = new JMenuItem("Save image");
		JMenuItem exit = new JMenuItem("Exit");
		open_image.addActionListener(this);
		save_image.addActionListener(this);
		exit.addActionListener(this);
		
		menuFile.add(open_image);
		menuFile.add(save_image);
		menuFile.addSeparator();
		menuFile.add(exit);
		
		JMenu menuHelp = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(this);
		menuHelp.add(about);
		
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
	}
	
	public MainWindow() {
		super(Title);
		
		getContentPane().add(desktop);
		setPreferredSize(new Dimension(500,500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		buildMenu();
	}
	
	public void start(){
		pack();
		setVisible(true);
	}
}
