package ch.hslu.swde.wda.persister.api;

import java.util.Date;
import java.util.List;

import ch.hslu.swde.wda.domain.Benutzer;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetterdatensatz;



public interface Persister {


	/**
	 * Liest die übergebenen Wetterdaten ein und speichert sie.
	 * @param Wetterdaten --> der zu speichernde Wetterdatensatz
	 * @return gibt das gespeicherte Wetterdatenobjekt zurück.
	 * @throws Exception falls das Speichern misslingt
	 */
	Wetterdatensatz speichernWetterdatensatz(Wetterdatensatz wd) throws Exception;

	/**
	 * Liest die übergebenen Wetterdaten ein und speichert sie.
	 * @param Wetterdatenliste --> der zu speichernde Wetterdatensatz
	 * @return gibt boolean zum Bestätigen zurück.
	 * @throws Exception falls das Speichern misslingt
	 */
	boolean speichernListeWetterdatensatz(List<Wetterdatensatz> listWd) throws Exception;
	
	
	
	/**
	 * Liest die übergebenen Ortschaften ein und speichert sie.
	 * @param Ortschaftenlisteliste --> der zu speichernde Ortschaften
	 * @return gibt boolean zum Bestätigen zurück.
	 * @throws Exception falls das Speichern misslingt
	 */
	boolean speichernListeOrtschaft(List<Ortschaft> listOrtschaft) throws Exception;


	/**
	 * aktualisiert ein Wetterdatenobjekt.
	 * @param Wetterdaten --> der zu aktualisiernde Wetterdatensatz
	 * @return gibt das aktualisierte Wetterdatenobjekt zurück.
	 * @throws Exception falls das Aktualisieren misslingt
	 */
	Wetterdatensatz aktualisierenWetterdatensatz(Wetterdatensatz wd) throws Exception;


	/**
	 * löscht ein Wetterdatenobjekt.
	 * @param Wetterdaten --> der zu löschende Wetterdatensatz
	 * @return gibt den Status des löschens zurück.
	 * @throws Exception falls das Löschen misslingt
	 */	
	boolean loeschenWetterdatensatz(Wetterdatensatz wd) throws Exception;
	
	/**
	 * löscht alle Wetterdatensaetze.
	 * @param Wetterdaten --> der zu löschende Wetterdatensatz
	 * @return gibt den Status des löschens zurück.
	 * @throws Exception falls das Löschen misslingt
	 */		
	boolean loeschenAlleWetterdatensaetze() throws Exception;

	/**
	 * löscht ein Wetterdatenobjekt anhand seiner Id.
	 * @param int wdId --> ID des zu löschenden Wetterdatensatz
	 * @return gibt den Status des löschens zurück.
	 * @throws Exception falls das Löschen misslingt
	 */
	boolean loeschenWetterdatensatzById(int WdId) throws Exception;

	/**
	 * gibt eine Liste aller zutreffenden Ergebnisse der Abfrage zurück.
	 * @param Wetterdaten --> der zu findende Wetterdatensatz
	 * @return Liste von Wetterdaten
	 * @throws Exception falls das Finden misslingt
	 */
	List<Wetterdatensatz> findeWetterdatensatzByOrt(String Ortschaft, Date Datum) throws Exception;

	/**
	 * gibt das gesuchte Wetterdatenobjekt anhand der Id zurück
	 * @param Wetterdatensatz ID
	 * @return gibt den gefundenen Wetterdatensatz zurück
	 * @throws Exception falls das Finden misslingt
	 */
	Wetterdatensatz findeWetterdatensatzById(int WdId) throws Exception;


	/**
	 * gibt die gesuchte Wetterdatenobjekte anhand des Datums zuück
	 * @param Ortschaft, Startdatum und Schlussdatum 
	 * @return gibt den gefundene Wetterdatensätze als Liste zurück
	 * @throws Exception falls das Finden misslingt
	 */
	List<Wetterdatensatz> findeWetterdatensatzZwischenDatum(String Ortschaft, Date startdatum, Date schlussdatum)throws Exception;

	/**
	 * gibt Liste aller Wetterdaten zurück
	 *@return Liste aller Wetterdaten
	 *@throws Exception falls das Finden misslingt
	 */
	List<Wetterdatensatz> alleWetterdatensatz() throws Exception;
	
	/**
	 * gibt Liste aller Wetterdaten an einem bestimmten Zeitpunkt zurück
	 *@return Liste aller Wetterdaten an einem bestimmten Zeitpunkt zurück
	 *@throws Exception falls das Finden misslingt
	 */
	List<Wetterdatensatz> alleWetterdatensatzByDatum(Date zeitpunkt) throws Exception;

	List<Ortschaft> alleOrtschaften() throws Exception;

	Ortschaft findeOrtschaftByName(String ort) throws Exception;

	boolean speichernBenutzer(Benutzer bn) throws Exception;
	
	boolean loeschenBenutzer(Benutzer bn) throws Exception;
	
	boolean aktualisierenBenutzer(Benutzer bn) throws Exception;
	
	Benutzer getBenutzerbyName(String str) throws Exception;
	
	
	
}
