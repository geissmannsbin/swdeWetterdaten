package ch.hslu.swde.wda.business;

import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetterdatensatz;
import ch.hslu.swde.wda.persister.impl.JPAPersister;
import ch.hslu.swde.wda.business.api.BusinessLogik;

public class BusinessLogikImpl extends UnicastRemoteObject implements BusinessLogik  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5447002684476346674L;
	private static Logger log = LogManager.getLogger(BusinessLogikImpl.class);
	private JPAPersister jpaPersister = null;

	public BusinessLogikImpl() throws RemoteException {
		try {
			/* EntityManagerFactory erzeugen */
			jpaPersister = new JPAPersister();
		} catch (Exception e) {
			log.error("ERROR: ", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Ortschaft> a1() throws RemoteException {
		try {
			List<Ortschaft> listReturn = jpaPersister.alleOrtschaften();
			return listReturn;
		} catch (Exception e) {
			log.error("ERROR: ", e);
			throw new RuntimeException(e);
		}

	}


	@Override
	public List<Wetterdatensatz> a2(String Ortschaften, Date startdatum, Date schlussdatum) throws RemoteException {
		try {
			List<Wetterdatensatz> listReturn = jpaPersister.findeWetterdatensatzZwischenDatum(Ortschaften, startdatum, schlussdatum);
			return listReturn;
		} catch (Exception e) {
			log.error("ERROR: ", e);
			throw new RuntimeException(e);
		}
		
	}


	@Override
	/**
	* Returns a Map of values according to:
	* Wie gross waren die Durchschnittswerte für Temperatur, Luftdruck und Feuchtigkeit für die
	*	angegebene Ortschaft in einer angegebenen Zeitperiode?
	* @param ortschaft  name of desired ortschaft
	* @param startdatum from which date on is the average wanted
	* @param schlussdatum up until which date is the average wanted
	* @return a map of the desired Values avgTemperatur, avgFeuchtigkeit, avgLuftdruck
	* 			--> on Error value of null is returned
	*/
	public Map<String, Float> a3(String ortschaft, Date startdatum, Date schlussdatum)throws RemoteException {
		
		Map<String, Float> returnValues = new HashMap<String, Float>();
		List<Wetterdatensatz> inputList = null;
		
		float addiertLuftdruck = 0;
		float addiertTemperatur = 0;
		float addiertFeuchtigkeit = 0;
		
		try {
			inputList = jpaPersister.findeWetterdatensatzZwischenDatum(ortschaft, startdatum, schlussdatum);
			for(Wetterdatensatz d: inputList) {
				addiertTemperatur += d.getTemperatur();
				addiertLuftdruck += d.getLuftdruck();
				addiertFeuchtigkeit += d.getFeuchtigkeit();
			}	
			
			returnValues.put("avgTemperatur", addiertTemperatur/inputList.size());
			returnValues.put("avgFeuchtigkeit", addiertFeuchtigkeit/inputList.size());
			returnValues.put("avgLuftdruck", addiertLuftdruck/inputList.size());
			
		} catch (Exception e) {
			log.error("ERROR: ", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
		return returnValues;
	}


	@Override
	/**
	* Returns a Map of values according to:
	* Wie sahen die Maximal- und Minimalwerte für Temperatur, Luftdruck und Feuchtigkeit für
	*	eine angegebene Ortschaft während einer angegebenen Zeitperiode aus?
	* @param ortschaft  name of desired ortschaft
	* @param startdatum from which date data is wanted
	* @param schlussdatum up until which date data is wanted
	* @return a map of the desired Values maxLuftdruck, minLuftdruck, maxTemperatur, minTemperatur, maxFeuchtigkeit,minFeuchtigkeit
	* 			--> on Error value of null is returned
	*/
	public Map<String, Wetterdatensatz> a4(String ortschaft, Date startdatum, Date schlussdatum)throws RemoteException {
		Map<String, Wetterdatensatz> returnValues = new HashMap<String, Wetterdatensatz>();
		List<Wetterdatensatz> inputList = null;
		
		Wetterdatensatz maxLuftdruck = null;
		Wetterdatensatz minLuftdruck = null;
		Wetterdatensatz maxTemperatur = null;
		Wetterdatensatz minTemperatur = null;
		Wetterdatensatz maxFeuchtigkeit = null;
		Wetterdatensatz minFeuchtigkeit = null;
		
		try {
			inputList = jpaPersister.findeWetterdatensatzZwischenDatum(ortschaft, startdatum, schlussdatum);
			
			maxLuftdruck = Collections.max(inputList, Comparator.comparing(w -> w.getLuftdruck()));
			minLuftdruck = Collections.min(inputList, Comparator.comparing(w -> w.getLuftdruck()));
			maxTemperatur = Collections.max(inputList, Comparator.comparing(w -> w.getTemperatur()));
			minTemperatur = Collections.min(inputList, Comparator.comparing(w -> w.getTemperatur()));
			maxFeuchtigkeit = Collections.max(inputList, Comparator.comparing(w -> w.getFeuchtigkeit()));
			minFeuchtigkeit = Collections.min(inputList, Comparator.comparing(w -> w.getFeuchtigkeit()));
			
			returnValues.put("maxLuftdruck", maxLuftdruck);
			returnValues.put("minLuftdruck", minLuftdruck);
			returnValues.put("maxTemperatur", maxTemperatur);
			returnValues.put("minTemperatur", minTemperatur);
			returnValues.put("maxFeuchtigkeit", maxFeuchtigkeit);
			returnValues.put("minFeuchtigkeit", minFeuchtigkeit);
			
		} catch (Exception e) {
			log.error("ERROR: ", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return returnValues;
	}


	@Override
	/**
	* Returns a Map of values according to:
	* Wie sahen die Maximal- und Minimalwerte für Temperatur, Luftdruck und Feuchtigkeit über
	* alle Ortschaften zu einem bestimmten Zeitpunkt aus3? Welche Ortschaft hatte die höchste bzw. tiefste Temperatur an dem 15.06.2020 um 13:30
	* @param zeitpunkt from which date on is the average wanted
	* @return a map of the desired Values maxLuftdruck, minLuftdruck, maxTemperatur, minTemperatur, maxFeuchtigkeit,minFeuchtigkeit
	* 			--> on Error value of null is returned
	*/
	public Map<String, Wetterdatensatz> a5(Date zeitpunkt) throws RemoteException {
		Map<String, Wetterdatensatz> returnValues = new HashMap<String, Wetterdatensatz>();
		List<Wetterdatensatz> inputList = null;
		
		Wetterdatensatz maxLuftdruck = null;
		Wetterdatensatz minLuftdruck = null;
		Wetterdatensatz maxTemperatur = null;
		Wetterdatensatz minTemperatur = null;
		Wetterdatensatz maxFeuchtigkeit = null;
		Wetterdatensatz minFeuchtigkeit = null;
		
		try {
			inputList = jpaPersister.alleWetterdatensatzByDatum(zeitpunkt);
			
			maxLuftdruck = Collections.max(inputList, Comparator.comparing(w -> w.getLuftdruck()));
			minLuftdruck = Collections.min(inputList, Comparator.comparing(w -> w.getLuftdruck()));
			maxTemperatur = Collections.max(inputList, Comparator.comparing(w -> w.getTemperatur()));
			minTemperatur = Collections.min(inputList, Comparator.comparing(w -> w.getTemperatur()));
			maxFeuchtigkeit = Collections.max(inputList, Comparator.comparing(w -> w.getFeuchtigkeit()));
			minFeuchtigkeit = Collections.min(inputList, Comparator.comparing(w -> w.getFeuchtigkeit()));
			
			returnValues.put("maxLuftdruck", maxLuftdruck);
			returnValues.put("minLuftdruck", minLuftdruck);
			returnValues.put("maxTemperatur", maxTemperatur);
			returnValues.put("minTemperatur", minTemperatur);
			returnValues.put("maxFeuchtigkeit", maxFeuchtigkeit);
			returnValues.put("minFeuchtigkeit", minFeuchtigkeit);
			
		} catch (Exception e) {
			log.error("ERROR: ", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return returnValues;
	}
	
	public boolean a8(String username, String password) throws RemoteException {
		BenutzerVerwaltungImpl benutzerVerwaltung = new BenutzerVerwaltungImpl();
		return benutzerVerwaltung.login(username, password);		
	}
}
