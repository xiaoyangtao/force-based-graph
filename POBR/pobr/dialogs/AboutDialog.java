package pobr.dialogs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AboutDialog extends JDialog {
	static final Dimension SIZE = new  Dimension(404,170);
	
	class MyPicture extends JPanel{
		BufferedImage pic;
		Image pic2;
		public MyPicture() {
			try {
				
				File f = new File("about.jpg");
				pic = ImageIO.read(f);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(pic != null)
				g.drawImage(pic, 0, 0, null);
		}
	}
	
	MyPicture myPic = new MyPicture();
	
	public AboutDialog(JFrame owner) {
		super(owner, "About");
		myPic.setPreferredSize(SIZE);
		add(myPic);
		setPreferredSize(SIZE);
		setLocation(400, 300);
		setResizable(false);
		pack();
	}
	
	public void showMe(){
		setVisible(true);
	}
	
}
