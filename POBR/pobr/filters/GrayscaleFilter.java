package pobr.filters;

import org.apache.log4j.Logger;


import pobr.Image2Process;

public class GrayscaleFilter extends Filter {

	 
	public void process(Image2Process img) {
		System.out.println("Starting grayscale filter");
		for(int x = 0; x < img.getWidth();x++){
			for(int y = 0; y < img.getHeight();y++){
				int gray = img.getRed(x, y) + img.getGreen(x, y) + img.getBlue(x, y);

				try{
					img.setRed(x, y, (int)(gray/3));
					img.setGreen(x, y, (int)(gray/3));
					img.setBlue(x, y, (int)(gray/3));
				}
				catch(Exception e){
					System.out.println(x + ", " + y + " - " + (byte)(gray/3));
				}
			}
		}
	}

}
