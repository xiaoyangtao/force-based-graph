import javax.swing.JFrame;

public class Polygon {
	
	/*
	 * Metoda tworzaca okienko interfejsu przy starcie aplikacji 
	 */
	private static void run_GUI(){
		// Tworze obiekt klasy MainWindow
		MainWindow mainw = new MainWindow();
		// Ustawiam standardowa operacje ktora jest wykonywana przy probie wyjscia z programu
		mainw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// rozmieszczam elementy w oknie by zajowaly optynmalna ilosc miejsca 
		//mainw.pack();
		
		// czynie glowne okno widocznym
		mainw.setVisible(true);
	}

	/*
	 * G³ówna metoda uruchamiana przy samym starcie programu 
	 */
	public static void main(String[] args) {
		// laduje GUI
		run_GUI();
	}
}
