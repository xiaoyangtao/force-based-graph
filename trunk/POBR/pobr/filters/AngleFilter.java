package pobr.filters;

import java.util.Hashtable;
import java.util.Iterator;

import pobr.Image2Process;
import pobr.dialogs.SampleDialog;
import java.math.*;

public class AngleFilter extends Filter {
	class Segment{
		int classify = -1;
		
		int minx = 99999;
		int maxx = 0;
		int miny = 99999;
		int maxy = 0;
		
		int midx = 0;
		int midy = 0;
		
		int S = 0;
		int m01 = 0;
		int m10 = 0;
		
		double i = 0;
		double j = 0;
	};
	
	public void process(Image2Process img) {
		System.out.println("Starting sample filter");
		
		Hashtable arrows = new Hashtable();
		
		for(int x = 0; x < img.getWidth();x++){
			for(int y = 0; y < img.getHeight();y++){
				try{
					int red = img.getRed(x, y);
					Segment seg = null;
					if(red != 255){
						seg = (Segment)arrows.get(new Integer(red));
						if(seg == null){
							seg = new Segment();
						}
						// klasyfikacja
						seg.classify = red;
						// pole
						seg.S++;
						
						// minimalne i maksymalne wspolrzedne
						if(seg.minx > x)
							seg.minx = x;
						if(seg.maxx < x)
							seg.maxx = x;
						if(seg.miny > y)
							seg.miny = y;
						if(seg.maxy < y)
							seg.maxy = y;
						
						seg.m10 += y;
						seg.m01 += x;
						
							
						arrows.put(new Integer(red), seg);
					}
				}
				catch(Exception e){
					System.out.println(x + ", " + y);
				}
			}
		}
		
		for(Iterator iter = arrows.values().iterator(); iter.hasNext();){
			Segment seg = (Segment)iter.next();
			seg.midx = (seg.maxx - seg.minx)/2;
			seg.midy = (seg.maxy - seg.miny)/2;
			
			
			seg.i = seg.m10 / seg.S - seg.miny;
			seg.j = seg.m01 / seg.S - seg.minx;
			

				double alfa = (seg.midy - seg.i) / Math.sqrt( (seg.midx - seg.j)*(seg.midx - seg.j) 
									+ (seg.midy - seg.i)* (seg.midy - seg.i)); 
			
				
				
				double angle = Math.asin(alfa) * (180/Math.PI);
				
				if(seg.midx - seg.j > 0){
					angle = 180 - angle; 
				}
			
				
				System.out.println("red = " +seg.classify + "; Pole = " + seg.S + "; alfa = " + alfa + "; midx = " + seg.midx  + "; midy = " + seg.midy + "; i = " + seg.i +  "; j = " + seg.j);
				System.out.println("angle = " + angle );
		}
	}

}
