import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

/*
 * Glowna klasa zawierajaca silnik calego programu.
 * Zajmuje sie przechowywanie danych i i obrobka;
 */

class Engine {
	// pomocnicza struktura przechowujaca wielokat (ze wzgledu na zbierznosc nazw nie importuje jej)
	java.awt.Polygon poly;
	// wektor ktory bedzie przechowywal wszystkie powstale trojkaty(przy uzyciu klasy Polygon)
	Vector triangles;
	// wektor przechowujacy potencjalne wierzcholki
	Vector points;
	// wierzcholki posortowane radialnie
	Vector sorted;
	// ile wygenerowac punktow ?
    int MAXPOINTS = 7;
	// szerokosc okna
    int width;
    // wysokosc okna 
	int height;
	// indeks najnizej polozonego punktu
	int idxs = 0;
	// faza :
	// 0 - inicjalizacja programu;
	// 1 - punkty wygenerowane, czeka na wymuszenie wyszukania wielokata
	// 2 - wygenerowano wielokat .. czekam na wybor punktu
	// 3 - zostal wybrany punkt mozna wybierac interesujace trojkaty
	
	int faza = 0;
	
	// punkt ktory posluzy do podzialu wielokata na trojkaty
	Point mid;
	
	// index w tablicy triangles wyselekcjonowanego trojkata (-1 - nie wybrano zadnego)
	int selected = -1;
	
	// pole wielokata (area)
	float area = 0;
	
	Engine(int w, int h){
    	// zapamietuje rozmiar ona dla ktorego bede generowal punkty
    	width = w;
    	height = h;
    }
    
	// Metoda generuja losowe punkty
	
	public void generate(){
		// zeruje pole
		area = 0;
		// tworze obiekt odpowiedzialny za wylosowane punktow
		Random rnd = new Random(System.currentTimeMillis());
		// czyszcze tablice a jesli jej instancja nie istnieje (pierwsze uruchomienie) to ja tworze
		if (points == null){
			points = new Vector();
		}
		else {
			points.removeAllElements();
		}
			

		// ustawiam najminejszy element na zerowy bo inaczej w 
		//pierwszej iteracji odwola sie do nieistniejacego elementu wektora points
		idxs = 0;
		Enumeration enu;
		for(int i = 0; i < MAXPOINTS; i ++){
			// losuje punkty i zapamietuje je w jako klasy typu Point w wektorze points
			int ty = rnd.nextInt(height);
			
			// zwiekszam prawdopodobienstwo ze nie bedzie punktow o dwoch takich samych wspolrzednych
			enu = points.elements();

				while(enu.hasMoreElements()){
					Point p = (Point)enu.nextElement();
					if(p.y == ty){
						ty = rnd.nextInt(height);
						break;
					}
				}
				
			points.add(new Point(rnd.nextInt(width), ty));
			// przy okazji szukam najnizej polozonego punktu			
			if ( ((Point)points.elementAt(idxs) ).y < ((Point)points.elementAt(i) ).y ){
				// ten pod indeksem i jest mniejszy wiec go ustawiam jako aktualnie najmniejszy
				idxs = i;
			}
		}
		
		// czas na faze 1
		faza = 1;
	}
	
	// metoda testujaca czy wierzcholek front jest "prawoskretny" wzgledem pozostalych dwoch 
	private boolean testVertex(int back, int center, int front){
		// zmienne przechowujace wsp. trzech pow. punktow 
		int xb, yb, xc, yc, xf, yf; 

		// zapamietuje potrzebne wspolrzedne
		xb = ((RadialPoint)sorted.elementAt(back)).x;
		yb = ((RadialPoint)sorted.elementAt(back)).y;
		xc = ((RadialPoint)sorted.elementAt(center)).x;
		yc = ((RadialPoint)sorted.elementAt(center)).y;
		xf = ((RadialPoint)sorted.elementAt(front)).x;
		yf = ((RadialPoint)sorted.elementAt(front)).y;
			
		// dalsze obliczenia sprawdzajace czy front jest prawoskretny 
		// (uzywam do tego celu wyznacznika ktory jesli jest ujemny to 
		// oznacza ze te wierzcholki sa prawoskretne (clockwise order))
		int det = (xc-xb)*(yf-yc)-(yc-yb)*(xf-xc);
		
		if (det > 0) return true;
		else return false;
	}
	
	
	// metoda wybierajaca odpowiednie punkty w wczesniej wylosowanych
	// i przeksztalacajaca je w wielokat wypukly
	
	public void  create_polygon() {
		// zapamietuje najnizszy punkt bo bedzie on punktem 
		// odniesienia dla wszystkich pozostalych przy sortowaniu radialnym
		// a nastepnie wyrzucam go z tablicy punktow wylosowanych
		int x0, y0, x1, y1; 
		double kat; 
		Point tmp;
		RadialPoint rp;
		
		x0 = ((Point)points.elementAt(idxs)).x;
		y0 = ((Point)points.elementAt(idxs)).y;
		
		// inicjalizuje wektor sorted
		if (sorted == null){
			sorted = new Vector();
		}
		else {
			sorted.removeAllElements();
		}
		
		// dodaje do posortowanych pierwszy element (punkt x0, y0)
		rp = new RadialPoint(new Point(x0, y0), 0);
		sorted.add(rp);
	
		points.removeElementAt(idxs);
	
		Enumeration en = points.elements();
		
		while(en.hasMoreElements()){
			tmp = (Point)en.nextElement();
			x1 = tmp.x;
			y1 = tmp.y;
			// obliczam kat pod jakim znajduje sie punkt x1, y1 w stosunku do osi przechodzacej przez punkt x0,y0
			kat = StrictMath.atan((double)(y0 -y1) / (x0 - x1));
			// dla lepszej czytelnosci zamieniam na stopnie
			kat = (kat * 180) / StrictMath.PI; 
			// jesli wyszedl kat ujemny znaczy to nalezy go odjac od 180
			if(kat < 0) kat = 180 + kat; 
			//System.out.println((double)kat + " " + x0 + " " + y0 + " sts : " + (y0 -y1) + " " + (x0 - x1));
			rp = new RadialPoint(tmp, kat);
			sorted.add(rp);
		}
		
		// tworze komparator urzywajac klasy anonimowej (sposob z ksiazki Java 2 Core na posortowanie wektora)	
		Comparator cmp = new Comparator()
		{
			public int compare(Object a, Object b){
				return (int)((((RadialPoint)a).kat - ((RadialPoint)b).kat) * 100); 
			}
		};
		
		// tworz obiekt listy
		java.util.List lst  = new ArrayList(sorted);
		
		// sortuje wspolrzedne po polu kat (urzywajac do tego statycznje metody sort z kolekcji(Collections) )
		Collections.sort(lst, cmp);
		sorted = new Vector(lst);
		
		
		// wypisuje pomocnicze dane na standardowe wyjscie
		// i przy okazji przepisuje je do obiektu klasy poly
		Enumeration enu = sorted.elements();
		int in = 0;
		while(enu.hasMoreElements()){
			RadialPoint rr = (RadialPoint)enu.nextElement();
			//System.out.println(in + " : " + rr.x + "," + rr.y + "  kat : " + rr.kat);

			System.out.println("points.add(new Point(" + rr.x +"," +rr.y+"));");
			
			in ++;
		}
		System.out.println("idxs = 0;");
		
		// dalej wprowadzam w zycie algorytm Grahama (a.k.a. 3-coins algorithm)
		int back, center, front;
		
		// licznik wykonanych operacji
		int counter = 0;
		
		
		// obiekt ktory posluzy nam do rozpoznania czy algorytm zatoczyl pelna petle
		Object stop = sorted.elementAt(0);
		// to pozwoli nam zrobic dodatkowa petle zanim wywola sie  break 
		// (trzeba sprawdzic ostatnie trzy wierzcholki koniecznie)
		boolean isEnd = false; 
		
		// pierwsze trzy wierzcholki :
		back = 0;
		center = 1;
		front = 2;	
	
		while(true){
			if(testVertex(back, center, front)){
				back = center;
				center = front;
				// jesli wartosc front przekroczy ilosc wierzcholkow to trzeba zaczac od nowa 
				if (front == (sorted.size()-1)){
					front = 0;
				}
				else{
					front++;
				}
			}
			else{
				sorted.removeElementAt(center);
				if (back > sorted.size() -1){
					back--;
				}
				center = back;
				// zmniejszam wartosc front bo teraz indeks sie przesunal o jeden ze wzgledu na brakujacy elem.

				if (front != 0){
					front--;
				}
				
				if (back == 0){
					back = sorted.size() -1;
				}
				else {
					back--;
				}
				
			}
			counter++;
			//if((center == 1) && (counter > sorted.size()-2)) break;
			if (isEnd) break;
			if(sorted.elementAt(front).equals(stop)) isEnd = true;
		}
		
		// wypisuje pomocnicze dane na standardowe wyjscie
		// i przy okazji przepisuje je do obiektu klasy poly
		enu = sorted.elements();
		poly = new java.awt.Polygon();
		in = 0;
		System.out.println("Po wyselekcjonowaniu wierzcholkow :");
		while(enu.hasMoreElements()){
			RadialPoint rr = (RadialPoint)enu.nextElement();
			poly.addPoint(rr.x,rr.y);
			System.out.println(in + " : " + rr.x + "," + rr.y + "  kat : " + rr.kat);
			in ++;
		}
		
		// czas na faze druga
		faza = 2;
	}
	
	
	// metoda dzielaca wielokat na trojkaty
	public void divide(List list /* referencja na komponent listy wyswietlajacy pola trojkatow */ ){
		Enumeration en = sorted.elements();
		// wybieram trojkaty tworzac znich trzywierzcholkowe Polygony
		// wierzcholek pierwszy
		RadialPoint wp = (RadialPoint)en.nextElement();
		RadialPoint wd = null;
		// zapewniamy miejsce w wektorze dla nowopowstalych trojkatow
		triangles = new Vector();
		// iterujemy wszysttkie wierzcholki (sa posortowane radialnie 
		//wiec mozna latwo wybierac z nich trojkaty)
		while(en.hasMoreElements()){
			// wierzcholek drugi
			wd = (RadialPoint)en.nextElement();
			// pomocnicz znienna przechowujaca trojkat
			java.awt.Polygon pol = new java.awt.Polygon();
			// dodaje wierzcholki trojkata
			pol.addPoint(wp.x,wp.y);
			pol.addPoint(wd.x,wd.y);
			pol.addPoint(mid.x,mid.y);
			// nowo powstaly trojkat wrzucam do wektora
			triangles.add(pol);
			// drugi wierzcholek staje sie pierwszym
			wp = wd;
		}
		// jeszcze trzeba zadbac o ostatni trojkat (tworzony przez pierwszy i ostatni wierzcholek)
		java.awt.Polygon pol = new java.awt.Polygon();
		wd = (RadialPoint)sorted.firstElement();
		// dodaje wierzcholki trojkata
		pol.addPoint(wp.x,wp.y);
		pol.addPoint(wd.x,wd.y);
		pol.addPoint(mid.x,mid.y);
		// nowo powstaly trojkat wrzucam do wektora
		triangles.add(pol);
		
		// wypelniamy liste danymi o trojkatach :
		double pole;
		area = 0;
		list.removeAll();
		en = triangles.elements();
		int i = 0;
		while(en.hasMoreElements()){
			java.awt.Polygon tmp = (java.awt.Polygon)en.nextElement();
			// licze pole trojkata
			pole = calc_area(tmp);
			// sumuje pole wielokata
			area+= pole;
			list.add(i + " -> Area = " + (float)pole);
			
			i++;
		}
		
		// czas na faze 4
		faza = 4;
	}
	
	
	// oblicza pole trojkata (klasa Polygon przechowuje trzy wierzcholki trojkata)
	private double calc_area(java.awt.Polygon pol){
		// wynik
		double result = 0;
		// trzy punkty tworzace trojkat :
		int x1 = pol.xpoints[0];
		int y1 = pol.ypoints[0];
		int x2 = pol.xpoints[1];
		int y2 = pol.ypoints[1];
		int x3 = pol.xpoints[2];
		int y3 = pol.ypoints[2];
		// licze dlugosc bokow :
		double a = StrictMath.sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
		double b = StrictMath.sqrt( (x2-x3)*(x2-x3) + (y2-y3)*(y2-y3));
		double c = StrictMath.sqrt( (x3-x1)*(x3-x1) + (y3-y1)*(y3-y1));
		
		// polowa obwodu :
		
		double p = ((a + b + c) / 2);
		
		// ostateczny wynik : (wzor na pole majac dane boki)
		result = StrictMath.sqrt(p*(p-a)*(p-b)*(p-c));
		
		return result;
	}
	
}
