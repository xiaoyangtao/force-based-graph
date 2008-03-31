package pobr.filters;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import pobr.ImageView;


public class FilterInstaller {
		
	static void buildMenu(JMenu menu, Hashtable hash, ImageView actionLsn){
		for(Enumeration e = hash.keys(); e.hasMoreElements();){
			JMenuItem item = new JMenuItem(e.nextElement().toString());
			item.addActionListener(actionLsn);
			menu.add(item);
		}
	}
		
	public static Hashtable install(JMenu menu, ImageView actionLsn){
		Hashtable hash = new Hashtable();
		
		hash.put("Greyscale", new GrayscaleFilter());		
		hash.put("Sample filter", new SampleFilter());
		hash.put("Angle testet", new AngleFilter());
		hash.put("HP filter", new HPFilter());
		
		buildMenu(menu, hash, actionLsn);
		return hash;
	}
}
