package pobr.filters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import pobr.Image2Process;
import pobr.filters.AngleFilter.Segment;


public class HPFilter extends Filter {
	// wyostrzanie
//	float [][] maska				 = {{-1,-1,-1},
	//									{-1, 9,-1},
	//									{-1,-1,-1}};
	
	float [][] maska				 = {{0,-1,0},
										{-1, 5,-1},
										{0,-1,0}};

	
	int countWhite = 1;
	int countBlue = 1;
	
	class Rect{
		public int x;
		public int y;
		public int width;
		public int height;
		// numer segmentu
		public int red;
		
		// srodek geometryczny
		int midx = 0;
		int midy = 0;
		
		// pole
		int S = 0;
		double m00 = 0;
		double m01 = 0;
		double m10 = 0;
		double m11 = 0;
		double m20 = 0;
		double m02 = 0;
		
		double M20 = 0;
		double M02 = 0;
		double M11 = 0;
		
		double M1 = 0;
		double M7 = 0;
		
		
		// srodek ciezkosci
		double i = 0;
		double j = 0;
		
		// 1 - plat lewy lub prawy
		int typ;
		
	}
	
	Hashtable<Integer, Rect> foundWhite = new Hashtable<Integer, Rect>();
	Hashtable<Integer, Rect> foundBlue = new Hashtable<Integer, Rect>();
	
	private void znieksztalcenie_macierzowe(Image2Process img){
//		System.out.println(" r = " + img.getRed(x, y) + " g = " + img.getGreen(x, y) + " b = " + img.getBlue(x, y));
//		img.setRed(1, 1, 25);
//		img.setGreen(1, 1, 75);
//		img.setBlue(1, 1, 149);
		
//		System.out.println(" h = " + img.getH(1, 1) + " s = " + img.getS(1, 1) + " l = " + img.getL(1, 1));
//		System.out.println(" h = " + img.getH(x, y) + " s = " + img.getS(x, y) + " l = " + img.getL(x, y));
		BufferedImage tmp = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Image2Process tmpImg = new Image2Process();
		tmpImg.setImage(tmp);
		
		//img.setHSL(1, 1, 120, 100, 75);
		
		//img.setHSL(1, 1, 215, 71, 34);
		
		
		for(int x = 1; x < img.getWidth()-1;x++){
			for(int y = 1; y < img.getHeight()-1;y++){
				//tmpImg.setHSL(x, y, img.getH(x, y), img.getS(x, y), img.getL(x, y));
				float pixel = 0;
				float r = 0;
				float g = 0;
				float b = 0;
				
				for(int i = -1; i <= 1; i++){
					for(int j = -1; j <= 1; j ++){
						try{
							//pixel += img.getL(x + i, y + j) * maska[i + 1][j + 1];
							r += img.getRed(x + i, y + j) * maska[i + 1][j + 1];
							g += img.getGreen(x + i, y + j) * maska[i + 1][j + 1];
							b += img.getBlue(x + i, y + j) * maska[i + 1][j + 1];
							
						}
						catch(Exception e){
							e.printStackTrace();
							System.out.println("Exception ! x = " + x + " i= " + i + " y =" + y +" j = " +j);
							break;
						}
					}
				}
				//System.out.println("pixel = " + pixel);
				if(pixel < 0){
					pixel = 0;
				}
				if(r < 0){
					r = 0;
				}
				if(g < 0){
					g = 0;
				}
				if(b < 0){
					b = 0;
				}
				if(r > 255){
					r = 255;
				}
				if(g > 255){
					g = 255;
				}
				if(b > 255){
					b = 255;
				}

				
				//r /= 9;
				//g /= 9;
				//b /= 9;
				
				//tmpImg.setHSL(x, y, img.getH(x, y), img.getS(x, y), (int)pixel);
				tmpImg.setRed(x, y, (int)r);
				tmpImg.setGreen(x, y, (int)g);
				tmpImg.setBlue(x, y, (int)b);
				
			}
		}
		
		img.setImage(tmpImg.getImage());
		

	}
	
	/**
	 * zwiekszamy
	 * @param img
	 */
	private void poprawa_jasnosci(Image2Process img){
		for(int x = 0; x < img.getWidth();x++){
			for(int y = 0; y < img.getHeight();y++){
				try{
					int brighter = 0;
					int red = img.getRed(x, y);
					
					//int red = (img.getRed(x, y) * img.getRed(x, y)) / 255;
					//int red = (int)Math.sqrt((img.getRed(x, y))) * 15;
					
					img.setRed(x, y, (red  + brighter > 255)?255:(red + brighter));
					
					int green = img.getGreen(x, y);
					//int green = (img.getGreen(x, y) * img.getGreen(x, y)) / 255;
					//int green = (int)Math.sqrt((img.getGreen(x, y))) * 15;
					img.setGreen(x, y, (green + brighter > 255)?255:(green + brighter));

					int blue = img.getBlue(x, y);
					//int blue = (img.getBlue(x, y) * img.getBlue(x, y)) / 255;
					//int blue = (int)Math.sqrt((img.getBlue(x, y))) * 15;
					img.setBlue(x, y, (blue + brighter> 255)?255:(blue + brighter));

					//img.setRed(x, y, ((img.getRed(x, y) + 100) > 255)?255:(img.getRed(x, y) + 100));
					//img.setGreen(x, y, ((img.getGreen(x, y) + 100) > 255)?255:(img.getGreen(x, y) + 100));
					//img.setBlue(x, y, ((img.getBlue(x, y) + 100) > 255)?255:(img.getBlue(x, y) + 100));
				}
				catch(Exception e){
					System.out.println(x + ", " + y);
				}
			}
		}
	}

	
	/**
	 * 
	 * 
	 */
	private void progowanie(Image2Process img){
		int blueThreshold = 35;
		int whiteThreshold = 50;
		
		for(int x = 0; x < img.getWidth();x++){
			for(int y = 0; y < img.getHeight();y++){
				try{
					int redStr = (int)(((double)img.getRed(x, y)/255) * 100);
					int greenStr = (int)(((double)img.getGreen(x, y)/255) * 100);
					int blueStr = (int)(((double)img.getBlue(x, y)/255) * 100);
					
					if(blueStr > blueThreshold && greenStr < whiteThreshold && redStr < whiteThreshold){
						//System.out.println(redStr + ", " + greenStr + ", " + blueStr);
						
						img.setRed(x, y, 0);
						img.setGreen(x, y, 0);
						img.setBlue(x, y, 255);
					}
					else if(blueStr > whiteThreshold && greenStr > whiteThreshold && redStr > whiteThreshold){
						img.setRed(x, y, 255);
						img.setGreen(x, y, 255);
						img.setBlue(x, y, 255);						
					}
					else{
						img.setRed(x, y, 0);
						img.setGreen(x, y, 0);
						img.setBlue(x, y, 0);						
					}
					//img.setRed(x, y, ((img.getRed(x, y) + 100) > 255)?255:(img.getRed(x, y) + 100));
					//img.setGreen(x, y, ((img.getGreen(x, y) + 100) > 255)?255:(img.getGreen(x, y) + 100));
					//img.setBlue(x, y, ((img.getBlue(x, y) + 100) > 255)?255:(img.getBlue(x, y) + 100));
				}
				catch(Exception e){
					System.out.println(x + ", " + y);
				}
			}
		}
	}
	
	
	public void segmentacja(Image2Process img){
		
		
		for(int x = 1; x < img.getWidth()-1;x++){
			for(int y = 1; y < img.getHeight()-1;y++){
				// niebieski
				if(img.getBlue(x, y) == 255 && img.getRed(x, y) != 255){
					//seedBlue(img, x, y);
					//count ++;
				}
				// a to musi byæ bia³y
				if(img.getRed(x, y) == 255 && img.getGreen(x, y) == 255 && img.getBlue(x, y) == 255){
					seedWhite(img, x, y);
					countWhite ++;
				}				
			}
		}
	}
	
	public void seedBlue(Image2Process img, int x, int y){
		Vector<Point> pixels = new Vector<Point>();
		pixels.add(new Point(x,y));
		int minx = 99999;
		int miny = 99999;
		int maxx = 0;
		int maxy = 0;
		System.out.println("Blue found ! x = " + x  + " y = " + y);
		System.out.println("Rozrost count = " + countBlue);
		
		while(pixels.size() != 0){
			
			Vector<Point> tmp = new Vector<Point>();
			for(Iterator iter = pixels.iterator(); iter.hasNext();){
				Point act = (Point)iter.next();
				if(act.x+1 < img.getWidth()){
					if(img.getBlue(act.x + 1, act.y) == 255 && img.getGreen(act.x + 1, act.y) == 0){
						img.setGreen(act.x + 1, act.y, 1);
						tmp.add(new Point(act.x + 1,act.y));
					}
				}
				if(act.x-1 > 0){
					if(img.getBlue(act.x - 1, act.y) == 255 && img.getGreen(act.x - 1, act.y) == 0){
						img.setGreen(act.x - 1, act.y, 1);
						tmp.add(new Point(act.x - 1,act.y));
					}
				}
				if(act.y+1 < img.getHeight()){
					if(img.getBlue(act.x, act.y + 1) == 255 && img.getGreen(act.x, act.y + 1) == 0){
						img.setGreen(act.x, act.y + 1, 1);
						tmp.add(new Point(act.x,act.y + 1));
					}
				}
				if(act.y-1 > 0){
					if(img.getBlue(act.x, act.y-1) == 255 && img.getGreen(act.x, act.y-1) == 0){
						img.setGreen(act.x, act.y - 1, 1);
						tmp.add(new Point(act.x ,act.y-1));
					}
				}
				
				img.setRed(act.x, act.y, countBlue);
				minx = (act.x < minx?act.x:minx);
				miny = (act.y < miny?act.y:miny);
				maxx = (act.x > maxx?act.x:maxx);
				maxy = (act.y > maxy?act.y:maxy);
				
				img.setBlue(act.x, act.y, 220);
			}
			pixels.removeAllElements();
			System.out.println("Adding " + tmp.size() + " pixels after grow");
			pixels.addAll(tmp);
			System.out.println("Added " + pixels.size() + " pixels after grow");
			
		}
		
		Rect rec = new Rect();
		rec.red = countBlue;
		rec.x = minx;
		rec.y = miny;
		rec.width = maxx - minx;
		rec.height = maxy - miny;
		foundBlue.put(new Integer(countBlue), rec);
		img.getImage().getGraphics().setColor(new Color(220,200,0));
		img.getImage().getGraphics().drawRect(rec.x, rec.y, rec.width, rec.height);
	}

	public void seedWhite(Image2Process img, int x, int y){
		Vector<Point> pixels = new Vector<Point>();
		pixels.add(new Point(x,y));
		int minx = 99999;
		int miny = 99999;
		int maxx = 0;
		int maxy = 0;
		System.out.println("White found ! x = " + x  + " y = " + y);
		System.out.println("Rozrost count = " + countWhite);
		while(pixels.size() != 0){
			
			Vector<Point> tmp = new Vector<Point>();
			for(Iterator iter = pixels.iterator(); iter.hasNext();){
				Point act = (Point)iter.next();
				if(act.x+1 < img.getWidth()){
					if(img.getBlue(act.x + 1, act.y) == 255 && img.getGreen(act.x + 1, act.y) == 255 && img.getBlue(act.x + 1, act.y) == 255){
						img.setGreen(act.x + 1, act.y, 254);
						tmp.add(new Point(act.x + 1,act.y));
					}
				}
				if(act.x-1 > 0){
					if(img.getBlue(act.x - 1, act.y) == 255 && img.getGreen(act.x - 1, act.y) == 255 && img.getBlue(act.x - 1, act.y) == 255){
						img.setGreen(act.x - 1, act.y, 254);
						tmp.add(new Point(act.x - 1,act.y));
					}
				}
				if(act.y+1 < img.getHeight()){
					if(img.getBlue(act.x, act.y + 1) == 255 && img.getGreen(act.x, act.y + 1) == 255 && img.getBlue(act.x, act.y + 1) == 255){
						img.setGreen(act.x, act.y + 1, 254);
						tmp.add(new Point(act.x,act.y + 1));
					}
				}
				if(act.y-1 > 0){
					if(img.getBlue(act.x, act.y-1) == 255 && img.getGreen(act.x, act.y-1) == 255){
						img.setGreen(act.x, act.y - 1, 254);
						tmp.add(new Point(act.x ,act.y-1));
					}
				}
				
				img.setRed(act.x, act.y, countWhite);
				minx = (act.x < minx?act.x:minx);
				miny = (act.y < miny?act.y:miny);
				maxx = (act.x > maxx?act.x:maxx);
				maxy = (act.y > maxy?act.y:maxy);
				
				img.setBlue(act.x, act.y, 254);
			}
			pixels.removeAllElements();
			System.out.println("Adding " + tmp.size() + " pixels after grow");
			pixels.addAll(tmp);
			System.out.println("Added " + pixels.size() + " pixels after grow");
			
		}
		
		Rect rec = new Rect();
		rec.red = countWhite;
		rec.x = minx;
		rec.y = miny;
		rec.width = maxx - minx;
		rec.height = maxy - miny;
		foundWhite.put(new Integer(countWhite), rec);
		//img.getImage().getGraphics().setColor(new Color(220,200,0));
		//img.getImage().getGraphics().drawRect(rec.x, rec.y, rec.width, rec.height);
	}

	public void erozja_i_rozrost(Image2Process img){
		BufferedImage tmp = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Image2Process tmpImg = new Image2Process();
		tmpImg.setImage(tmp);
		
		for(int x = 1; x < img.getWidth()-1;x++){
			for(int y = 1; y < img.getHeight()-1;y++){
				boolean wasSet = false;
				for(int i = -1; i <= 1; i++){
					for(int j = -1; j <= 1; j ++){
						try{
							if(img.getRed(x+i, y+j) == 0 && img.getGreen(x+i, y+j) == 0 && img.getBlue(x+i, y+j) == 0){
								tmpImg.setRed(x, y, 0);
								tmpImg.setGreen(x, y, 0);
								tmpImg.setBlue(x, y, 0);
								wasSet = true;
							}
						}
						catch(Exception e){
							e.printStackTrace();
							System.out.println("Exception ! x = " + x + " i= " + i + " y =" + y +" j = " +j);
							break;
						}
					}
				}
				if(!wasSet){
					tmpImg.setRed(x, y, img.getRed(x, y));
					tmpImg.setGreen(x, y, img.getGreen(x, y));
					tmpImg.setBlue(x, y, img.getBlue(x, y));
				}
			}
		}
		img.setImage(tmpImg.getImage());
		
		tmp = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		tmpImg = new Image2Process();
		tmpImg.setImage(tmp);
		
		for(int x = 1; x < img.getWidth()-1;x++){
			for(int y = 1; y < img.getHeight()-1;y++){
				boolean wasSet = false;
				for(int i = -1; i <= 1; i++){
					for(int j = -1; j <= 1; j ++){
						try{
							if(img.getRed(x+i, y+j) == 255 && img.getGreen(x+i, y+j) == 255 && img.getBlue(x+i, y+j) == 255){
								tmpImg.setRed(x, y, 255);
								tmpImg.setGreen(x, y, 255);
								tmpImg.setBlue(x, y, 255);
								wasSet = true;
							}
						}
						catch(Exception e){
							e.printStackTrace();
							System.out.println("Exception ! x = " + x + " i= " + i + " y =" + y +" j = " +j);
							break;
						}
					}
				}
				if(!wasSet){
					tmpImg.setRed(x, y, img.getRed(x, y));
					tmpImg.setGreen(x, y, img.getGreen(x, y));
					tmpImg.setBlue(x, y, img.getBlue(x, y));
				}
			}
		}
		
		img.setImage(tmpImg.getImage());
	}
	
	public void pokaz_segmenty(Image2Process img){
		for(Enumeration iter = foundWhite.elements(); iter.hasMoreElements();){
			Rect segment = (Rect)iter.nextElement();
			img.getImage().getGraphics().setColor(Color.red);
			img.getImage().getGraphics().drawRect(segment.x, segment.y, segment.width, segment.height);
		}
	}
	
	
	double centralny(int p, int q, Image2Process img, Rect seg)
	{
		int x = seg.x;
		int y = seg.y;
		int width = seg.width;
		int height = seg.height;
		int red = seg.red;
		
		double result = 0;
		double spot = 0.0;

		for( int i = y; i <= y + height; i++ )
		{
			for( int j = x; j <= x + width; j++ )
			{
				//spot = (img.getRed(j, i) + img.getGreen(j, i) + img.getBlue(j, i)) /3;
				if( img.getRed(j, i) == red){
					spot = 1.0;
				}
				else{
					spot = 0.0;
				}

				result += Math.pow(i - y, p) * Math.pow(j - x, q) * spot;
			}
		}

		return result;
	}
	
	public void policz_cechy(Image2Process img){
		for(Enumeration iter = foundWhite.elements(); iter.hasMoreElements();){
			Rect seg = (Rect)iter.nextElement();			
			
			seg.m20 = centralny(2,0,img, seg);
			seg.m02 = centralny(0,2,img, seg);
			seg.m00 = centralny(0,0,img, seg);
			seg.m10 = centralny(1,0,img, seg);
			seg.m01 = centralny(0,1,img, seg);
			seg.m11 = centralny(1,1,img, seg);

			seg.M20 = seg.m20 - seg.m10*seg.m10/seg.m00;
			seg.M02 = seg.m02 - seg.m01*seg.m01/seg.m00;
			seg.M11 = seg.m11 - seg.m10*seg.m01/seg.m00;

			seg.M7 = ( seg.M20*seg.M02 - seg.M11*seg.M11 ) / Math.pow( seg.m00, 4 );
			seg.M1 = (seg.M20 + seg.M02)/ Math.pow( seg.m00, 2 );
			
			seg.midx = seg.width/2;
			seg.midy = seg.height/2;
					
			seg.i = seg.m10 / seg.m00 - seg.y;
			seg.j = seg.m01 / seg.m00 - seg.x;
						
		}
	}
	
	public void wypisz_cechy(Image2Process img){
		for(Enumeration en = foundWhite.elements(); en.hasMoreElements();){
			Rect seg = (Rect)en.nextElement();
			System.out.println("Segment nr " + seg.red + " M1 = " + seg.M1 + " M7 = " + seg.M7);
		}
	}
	
	public void znajdz_platy(Image2Process img){
		double platLewyM1 = 0.25;
		double platLewyM7 = 0.0112;
		
		double platPrawyM1 =  0.265;
		double platPrawyM7 = 0.0138;
		
		// szukamy platow
		Vector platy = new Vector();
		
		for(Enumeration en = foundWhite.elements(); en.hasMoreElements();){
			Rect seg = (Rect)en.nextElement();
			//System.out.println("Segment nr " + seg.red + " M1 = " + seg.M1 + " M7 = " + seg.M7);
			
			double tolerancja = 0.13;
			
			if(seg.M1 > (1-tolerancja) *platLewyM1 && seg.M1 < (1+tolerancja) *platLewyM1 
				&& seg.M7 > (1-tolerancja) *platLewyM7 && seg.M7 < (1+tolerancja) *platLewyM7)
			{
				platy.add(seg);
				continue;
			}
			if(seg.M1 > (1-tolerancja) *platPrawyM1 && seg.M1 < (1+tolerancja) * platPrawyM1 
			&& seg.M7 > (1-tolerancja) *platPrawyM7 && seg.M7 < (1+tolerancja) * platPrawyM7)
			{
				platy.add(seg);
				continue;
			}
		}
		
		System.out.println("Znaleziono platow : " + platy.size());
		
		Rect lewy = null;
		Rect prawy = null;
		
		for(Enumeration zew = platy.elements(); zew.hasMoreElements();){
			Rect segZew = (Rect)zew.nextElement();
			for(Enumeration wew = platy.elements(); wew.hasMoreElements();){
				Rect segWew = (Rect)wew.nextElement();
				if(segZew == segWew){
					continue;
				}
				if(lewy == null && prawy == null){
					if(segZew.x >= segWew.x){
						prawy = segZew;
						lewy = segWew;
					}
					else{
						prawy = segWew;
						lewy = segZew;
					}
					continue;
				}
				double distOld = Math.sqrt((lewy.x - prawy.x)*(lewy.x - prawy.x) + (lewy.y - prawy.y)*(lewy.y - prawy.y));
				double distNew = Math.sqrt((segWew.x - segWew.x)*(segWew.x - segWew.x) + (segZew.y - segZew.y)*(segZew.y - segZew.y));
				if(distNew < distOld){
					if(segZew.x >= segWew.x){
						prawy = segZew;
						lewy = segWew;
					}
					else{
						prawy = segWew;
						lewy = segZew;
					}

				}
			}
		}
		
		if (lewy == null || prawy == null){
			return;
		}
		
		System.out.println("Lewy - " + lewy.red + " prawy - " + prawy.red);
		
		int logoX = lewy.x;
		int logoWidth = prawy.x + prawy.width - lewy.x;
		
		int logoY = lewy.y;
		int logoHeight = prawy.y + prawy.height - lewy.y;
		if(lewy.y > prawy.y){
			logoY = prawy.y;
			logoHeight = lewy.y + lewy.height - prawy.y;
		}
		
		int niebieskiS = 0;
		for(int x = logoX; x < logoX + logoWidth;x++){
			for(int y = logoY; y < logoY + logoHeight;y++){
				if(img.getBlue(x, y) == 255 && img.getGreen(x, y) == 0 && img.getRed(x, y) == 0){
					niebieskiS++;
				}
			}
		}
		
		System.out.println("Pole niebieskie = " + niebieskiS);
		
		double wspNieb = (double)niebieskiS/(lewy.m00+prawy.m00);
		
		if (wspNieb < 0.1){
			return;
		}
		
		System.out.println("wspolczynnik = " + (double)niebieskiS/(lewy.m00+prawy.m00));
		
		
		Graphics2D g = img.getImage().createGraphics();
		g.setColor(new Color(255,0,0));
		g.drawRect(logoX, logoY, logoWidth, logoHeight);
		
		
	}
	
	public void process(Image2Process img) {
		System.out.println("Starting HP filter");

		//znieksztalcenie_macierzowe(img);
		//poprawa_jasnosci(img)
		progowanie(img);
		erozja_i_rozrost(img);
		segmentacja(img);
		pokaz_segmenty(img);
		policz_cechy(img);
		wypisz_cechy(img);
		znajdz_platy(img);
		JOptionPane.showMessageDialog(null, "Job completed");
	}

}







/*
Kernel kernel = new Kernel(3, 3,
        new float[] {
            -1, -1, -1,
            -1, 9, -1,
            -1, -1, -1});
    BufferedImageOp op = new ConvolveOp(kernel);
    BufferedImage buff = new BufferedImage(img.getImage().getWidth(), img.getImage().getHeight(), img.getImage().getType());
    op.filter(img.getImage(), buff);
    
    img.setImage(buff);
*/