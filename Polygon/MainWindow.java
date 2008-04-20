import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/*
 * Klasa obslugujaca okienko w ktorym rysuje wielokat (uzyty zostal w tym celu JPanel ktory jest dolaczany )
 * jako do Containera (a dokladniej do ContentPaina Glownego okna (JFrame))
 */

class DrawWindow extends JComponent implements MouseListener, MouseMotionListener{
	// referencja do silnika obslugujaca genreowanie wielokata
	Engine eng;
	// okno nadrzedne 
	MainWindow parent; 
	// wsp. kursora
	int x = 0;
	int y = 0;
	
	DrawWindow(Engine e  /* jako argument przekazuje klase z danymi */ , MainWindow p){
		// ustawiam klasy nasluchujace zachowanie myszki
		addMouseListener(this);
		addMouseMotionListener(this);
		// zapisuje referencje do klasy przechowujacej dane
		eng = e;
		parent = p;
	}
	
	private void paintPolygon(Graphics g) {
		// rysuje punkty ktore wygenerowalem w pierwszej fazie
		// ustawiam kolor na czarny
		g.setColor(new Color(0,0,0));
		// obramowka 
		g.drawRect(0,0,200,200);
		// typ wyliczeniowy uzywany do przekladania zawartosci wektorow z wierzcholkami 
		Enumeration en;
		if(eng.faza == 1) {
			en = eng.points.elements();
			while(en.hasMoreElements()){
				Point tmp = (Point)en.nextElement();
				g.fillRect(tmp.x, tmp.y,3,3);
			}
			g.setColor(new Color(255,0,0));
			// przechwytuje wyjatek (w pewnym momecie idxs moze wyjsc poza tablice ale poniewaz jest to w momecie
			// kiedy zawartosc nie musi byc wyswieltlaan) mozna przechwycic i zignorowac wyjatek 
			try{
				g.fillRect(((Point)eng.points.elementAt(eng.idxs)).x  , ((Point)eng.points.elementAt(eng.idxs)).y,3,3);
			}
			catch(Exception e){}
		}
		
		// rysuje w fazie 2 - po wyselekcjonowaniu wierzcholkow nadajacych sie na wielokat
		if(eng.faza == 2) {
			en = eng.sorted.elements();
			while(en.hasMoreElements()){
				RadialPoint tmp = (RadialPoint)en.nextElement();
				g.fillRect(tmp.x, tmp.y,3,3);
			}
			g.drawPolygon(eng.poly);
			g.setColor(new Color(0,0,255));
			g.fillRect(((RadialPoint)eng.sorted.elementAt(0)).x  , ((RadialPoint)eng.sorted.elementAt(0)).y,3,3);
		}
		
		// rysuje w fazie 3 - po wybraniu punktu podzialu 
		if(eng.faza == 3) {
			en = eng.sorted.elements();
			while(en.hasMoreElements()){
				RadialPoint tmp = (RadialPoint)en.nextElement();
				g.fillRect(tmp.x, tmp.y,3,3);
			}
			g.drawPolygon(eng.poly);
			g.setColor(new Color(0,255,0));
			if(eng.mid != null){
				g.drawRect(eng.mid.x,eng.mid.y ,3,3);
			}
		}
		
		// rysuje w fazie 4 - po podzieleniu na trojkaty 
		if(eng.faza == 4) {
			en = eng.triangles.elements();
			while(en.hasMoreElements()){
				java.awt.Polygon tmp = (java.awt.Polygon)en.nextElement();
				g.drawPolygon(tmp);
			}
			// niebieski
			g.setColor(new Color(0,0,255));
			// rysuje punkt w srodku
			if(eng.mid != null){
				g.fillRect(eng.mid.x,eng.mid.y ,2,2);
			}
			if(eng.selected != -1){
				// wybieram zielony 
				g.setColor(new Color(0,255,0));
				g.drawPolygon((java.awt.Polygon)eng.triangles.elementAt(eng.selected));
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		// odnawiam zawartosc komponentow nadrzednych
		super.paintComponents(g);
		paintPolygon(g);
	}
	
	public void mouseDragged(MouseEvent e){
		
	}
	
	public void mouseClicked(MouseEvent e){
		// sprawdzam czy jest odpowiednia daza do wyboru punktu podzialu
		if(eng.faza == 2){
			// sprawdzam czy punkt znajduje sie wewnatrz wielokata
			if(eng.poly.contains(e.getPoint())){
				eng.mid = new Point(e.getPoint());
				eng.faza = 3;
				// dzielimy na trojkaty
				//DefaultListModel tmp = eng.divide();
				eng.divide(parent.list);
				
				//parent.list.setModel(tmp);
			}
		}
		// jesli faza 4 to sprawdzam ktory trojkat wybrano
		if(eng.faza == 4){
			Enumeration enu = eng.triangles.elements();
			// ustawiam selected na -1 w wypadku gdyby nie wybrano zadnego
			//eng.selected = -1;
			// zmienna przechowujaca aktualnie przetwarzany numer indeksu wektora triangles
			int i = 0;
			while(enu.hasMoreElements()){
				java.awt.Polygon tmp = (java.awt.Polygon)enu.nextElement();
				// sprawdzam czy w aktualnie analizowanym trojkacie zawiera sie punkt ktory kliknieto
				if(tmp.contains(e.getPoint())){
					// znaleziono wybrany trojkat (o indeksie i)
					eng.selected = i;
					break;
				}
				i++;
			}
			// ustawiam odpowiednia opzycje na liscie trojkatow
			parent.list.select(i);
		}
		// odswierza zawartosc label-u z polem powierzchni
		parent.pole.setText("Area = " + eng.area);
		// odmalowywuje okno po zmianach 
		repaint();
	}
	
	public void mouseMoved(MouseEvent e){
		x = e.getX();
		y = e.getY();
		//wymuszam odmalowanie komponentu z tekste,
		parent.label.invalidate();
		// wstawiam nowe wspolrzedne myszki do label
		parent.label.setText( "x= " + x + ", y= " + y );
		parent.paintComponents(parent.getGraphics());
		//parent.label.update(parent.label.getGraphics());
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
			
	}
}

/*
 * Klasa obslugujaca GUI programu (dziedziczy po standardowej 
 * klasie swinga JFrame)
 */
class MainWindow extends JFrame implements ActionListener, ItemListener, ChangeListener{
	// klasa z danymi
	Engine eng;
	// okno w ktorym rysuje wielokat
	DrawWindow dw;
	// okienko ze wspolrzednymi
	JLabel label;
	// okno z policzonym polem wielokata
	JLabel pole;
	// lista wyswietlajaca trojkaty
	List list;
	// opcja Find z menu Action ... zapisana globalnie 
	// by mozna bylo ja deaktywowac gdy nie powinna byc uruchamiana
	JMenuItem eFind;
	
	// pole do wyboru ilosci losowanych punktow 
	JSpinner spin;
	
	MainWindow(){
		// wywoluje konstruktor JFrame
		super("Polygon world ...");
		// tworze obiekt zajmujacy sie przechowywaniem i obrobka danych
		eng = new Engine(200,200);
		// losuje punkty
		eng.generate();
		// label ze wspolrzednymi kursora
		label = new JLabel("0");
		// label z polem wielokata
		pole = new JLabel("Area = " + eng.area);
		
		// model dzialania spinnera (w tym wypadku standardowa obsluga liczb)
		SpinnerModel model =
	        new SpinnerNumberModel(7, //initial value
	                               5, //min
	                               25, //max
	                               1);
		// tworze i konfiguruje obiekt spinnera
		spin = new JSpinner(model);
		
		spin.setBounds(320,185, 35,20);
		
		// dorzucam klase sluchajaca (this - czyli bierzaca) 
		spin.addChangeListener(this);
		// tworze liste
		list = new List();
		// ustalam wymiary i polozenie listy
		list.setBounds(220,25,150,155);
		
		// zapewniam zeby na raz mozna bylo wybrac tylko jeden element listy	
		list.setMultipleMode(false);
        // ustawiam actionListenera dla listy
		list.addItemListener(this);
		// tworze obiekt okna wykorzystywanego do rysowania wielokata
		dw = new DrawWindow(eng, this);
		// ustalam preferowany rozmiar okna dw
		dw.setSize(400,300);
		dw.setMinimumSize(new Dimension(300,300));
		
		// ustalam wymiary i polozenie labeli
		label.setBounds(220,0,100,20);
		pole.setBounds(220,155,100,80);
		
		// ustala menadzera okien : (a dokladniej wylacza go zeby mozna bylo recznie rozstawic elementy)
		this.setLayout(null);
		
		// ustala preferowany rozmiar glownego okna
		setSize(390,270);
		// dodaje dw do glownego okna
		getContentPane().add(spin);
		getContentPane().add(dw);
		getContentPane().add(label);
		getContentPane().add(list);
		getContentPane().add(pole);
		
		// dodaje menu do okna glownego
		// tworze pasek menu :
		JMenuBar pasekMenu = new JMenuBar();
		// dodaje pasek menu do glownego okna :
		setJMenuBar(pasekMenu);
		// tworz menu action
		JMenu menuAction = new JMenu("Action"); 
		// i dolaczam je do paska 
		pasekMenu.add(menuAction);
		// a nastepnie dodaje zawartosc menu Action
		JMenuItem eKoniec = new JMenuItem("End");
		JMenuItem eGeneruj = new JMenuItem("Generate Points");
		eFind = new JMenuItem("Find Convex Polygon");
		menuAction.add(eGeneruj);
		menuAction.add(eFind);
		menuAction.addSeparator();
		menuAction.add(eKoniec);
		// Ustawiam listenera dla menu Action
		eKoniec.addActionListener(this);
		eGeneruj.addActionListener(this);
		eFind.addActionListener(this);
	}
	
	// metoda nasluchujaca czy nastapila zmiana wartosci spinnera (okna 
	// edytowalnego umozliwiajcego ustawienie ilosci wierzcholkow)
	
    public void stateChanged(ChangeEvent e) {
        SpinnerModel nModel = spin.getModel();
        //jesli model obslugujacy tego spinera jest typu SpinnerNumberModel odczytuje jego nowa wartosc
        if (nModel instanceof SpinnerNumberModel) {
        	// zapamietuje nowa ilosc punktow i od razu restartuje caly silnik 
           eng.MAXPOINTS = ((SpinnerNumberModel)nModel).getNumber().intValue();
           // czyszcze liste trojkatow
           list.removeAll();
           // generuje od nowa punkty
           eng.generate();
           eFind.setEnabled(true);
           pole.setText("Area = " + eng.area);
           repaint();
        }
    }

	
	// metoda nasluchujaca czy nastapilo klkniecie na ktoras z pozycji menu Action
	
	public void actionPerformed(ActionEvent e){
		
		// jesli nastapilo w e zostanie przekazany string identyczny z nazwa wybranej pozycji 
		// wiec porownuje compareTo ze znanymi mi nazwami i podejmuje odpowiednie czynnosci
		
		// wybrano koniec
		if (e.getActionCommand().compareTo("End") ==0){
			System.exit(0);
		}
		
		// wybrano Generate ...
		if (e.getActionCommand().compareTo("Generate Points") ==0){
			eng.generate();
			eFind.setEnabled(true);
			// czyszcze liste
			list.removeAll();
			// ustawiam label z polem powierzchni
			pole.setText("Area = " + eng.area);
			// odswierzam wszystkie okna by zobaczyc zmiany
			dw.repaint();
		}

		
		// analogicznie jesli wybrano Find to ...
		if (e.getActionCommand().compareTo("Find Convex Polygon") ==0){
			eng.create_polygon();
			// uniemozliwiam uzywanie Find Convex .. poniewaz nie ma to sensu w tym etapie analizy wielokata
			eFind.setEnabled(false);
			dw.repaint();
		}
	}
	
	// metoda wywolywana gdy zostanie zaznaczona inna opcja na liscie 
	// (metoda ta nasluchuje czy doszlo do jakiejs interakcji z lista "list")
	
	public void itemStateChanged(ItemEvent e){
		// nastapila zmiana zaznaczenia na liscie wiec zmieniam numer wybranego trojkata
		eng.selected = list.getSelectedIndex();
		repaint();
	}
	
}