package pobr;

import java.awt.Graphics;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	ImageView parent;
	
	public ImagePanel(ImageView p){
		super(true);
		parent = p;
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (parent.getImage2Process().getImage() != null)		
			g.drawImage(parent.getImage2Process().getImage(),0,0,null);

	}

}
