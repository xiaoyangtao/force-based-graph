package pobr.filters;

import pobr.Image2Process;
import pobr.dialogs.SampleDialog;

public class SampleFilter extends Filter {

	public void process(Image2Process img) {
		System.out.println("Starting sample filter");
		
		SampleDialog dialog = new SampleDialog(); 
		dialog.showMe();
		
		for(int x = 0; x < img.getWidth();x++){
			for(int y = 0; y < img.getHeight();y++){
				try{
					img.setRed(x, y, ((img.getRed(x, y) + 100) > 255)?255:(img.getRed(x, y) + 100));
					img.setGreen(x, y, ((img.getGreen(x, y) + 100) > 255)?255:(img.getGreen(x, y) + 100));
					img.setBlue(x, y, ((img.getBlue(x, y) + 100) > 255)?255:(img.getBlue(x, y) + 100));
				}
				catch(Exception e){
					System.out.println(x + ", " + y);
				}
			}
		}
	}

}
