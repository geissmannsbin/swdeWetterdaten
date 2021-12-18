package ch.hslu.swde.wda.business.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ch.hslu.swde.wda.domain.Wetterdatensatz;
import ch.hslu.swde.wda.domain.Ortschaft;

public interface BusinessLogik extends Remote{
	
	String RO_Name = "RO_BusinessLogik";

	
	/**
	 * Für welche Ortschaften stehen die Wetterdaten zur Verfügung?
	 */
		public List<Ortschaft> a1() throws RemoteException;
		
	/**
	 * 	Wie sehen die Temperatur, Luftdruck und Feuchtigkeit für eine angegebene Ortschaft
	 *	während einer angegebenen Zeitperiode aus?
	 */
		public List<Wetterdatensatz> a2(String Ortschaft, Date startdatum, Date schlussdatum) throws RemoteException;
		
	/**
	 * Wie gross waren die Durchschnittswerte für Temperatur, Luftdruck und Feuchtigkeit für die
		angegebene Ortschaft in einer angegebenen Zeitperiode?
	 
	 */
		public Map<String, Float> a3(String Ortschaft, Date startdatum, Date schlussdatum) throws RemoteException;
		
	/**
	 * 	Wie sahen die Maximal- und Minimalwerte für Temperatur, Luftdruck und Feuchtigkeit für
		eine angegebene Ortschaft während einer angegebenen Zeitperiode aus?

	 */
		public Map<String, Wetterdatensatz> a4(String Ortschaft, Date startdatum, Date schlussdatum) throws RemoteException;
		
	/**
	 * Wie sahen die Maximal- und Minimalwerte für Temperatur, Luftdruck und Feuchtigkeit über
		alle Ortschaften zu einem bestimmten Zeitpunkt aus3? Welche Ortschaft hatte die höchste bzw. tiefste Temperatur an dem 15.06.2020 um 13:30
	
	 */
		public Map<String, Wetterdatensatz> a5(Date zeitpunkt) throws RemoteException;
		
		public boolean a8(String username, String password) throws RemoteException;
	
}
