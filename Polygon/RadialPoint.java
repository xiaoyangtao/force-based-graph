import java.awt.Point;

/* 	 Klasa pomocnicza uzywana do sortowania radialnie wierzcholkow
 *	 dziedziczaca po klasie Point
 */

public class RadialPoint extends Point{
	double kat;
	
	RadialPoint (Point p, double k){
		super(p.x,p.y);
		//x = p.x;
		//y = p.y;
		kat = k;
	}
}
