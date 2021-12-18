package ch.hslu.swde.wda.persister.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetterdatensatz;



public class Util {

	public static List<Wetterdatensatz> createWetterListe() {
		
		Date date = new Date();
		Ortschaft ort = new Ortschaft(6300, "Zug");

		List<Wetterdatensatz> liste = new ArrayList<>();

		//Constr. Form float temperatur, float feuchtigkeit, Date datum, Ortschaft ortschaft
		
		liste.add(new Wetterdatensatz(12.08F, 5.1F, date, ort, 1.0F));
		liste.add(new Wetterdatensatz(13.08F, 3.1F, date, ort, 1.0F));
		liste.add(new Wetterdatensatz(14.08F, 2.1F, date, ort, 1.0F));
		liste.add(new Wetterdatensatz(15.08F, 1.1F, date, ort, 1.0F));

		return liste;
	}
	
	
public static List<Wetterdatensatz> createAkualisierteWetterListe() {
		
		Date date = new Date();
		Ortschaft ort = new Ortschaft(6300, "Zug");

		List<Wetterdatensatz> liste = new ArrayList<>();

		//Constr. Form float temperatur, float feuchtigkeit, Date datum, Ortschaft ortschaft
		
		liste.add(new Wetterdatensatz(21.09F, 1.5F, date, ort, 1.0F));
		liste.add(new Wetterdatensatz(31.09F, 1.4F, date, ort, 1.0F));
		liste.add(new Wetterdatensatz(41.09F, 1.3F, date, ort, 1.0F));
		liste.add(new Wetterdatensatz(51.09F, 1.2F, date, ort, 1.0F));

		return liste;
	}
}
