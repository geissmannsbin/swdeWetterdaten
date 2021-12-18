package ch.hslu.swde.wda.business.adminInterface;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ch.hslu.swde.wda.business.*;
import ch.hslu.swde.wda.business.reader.OrtschaftenReader;
import ch.hslu.swde.wda.business.reader.WetterdatenReader;
import ch.hslu.swde.wda.domain.*;

import ch.hslu.swde.wda.business.api.BusinessLogik;

import ch.hslu.swde.wda.persister.impl.*;


public class AdminInterface {
	private static Scanner sc;
	
	public static void main(String[] args) throws RemoteException {
		// Programm start, "startProgramm" ist dafür da um am Ende einer Abfrage eine
		// weitere Abfrage zu tätigen.
		boolean startProgramm = false;
		sc = new Scanner(System.in);
		do {
			
			// Anzeige um dem Benutzer zu zeigen zischen welchen Funktionen dieser auswählen
			// kann.
			System.out.println("\nWas moechten Sie tun?\n");
			System.out.println("2 Alle moeglichen Ortschaften ins System Holen.");
			System.out.println("3 Wetterdaten fuer alle Verfuegbaren Ortschaften von Startdatum in DB laden.");
			System.out.println("4 Alle Wetter Daten Loeschen.");
			System.out.println("5 User Erfassen");
			System.out.println("6 User Passwort aendern");
			System.out.println("7 User Loeschen");
			System.out.println("\nBitte wählen Sie Ihre gewünschte Aktion(z.B.: 2).");

			// Benutzereingabe überprüfen ob eine Zahl (2-7) eigegeben wurde.
			boolean inputValidAuswahl = false;
			int wahlAbfrage = 0;

			do {
				String wahlAbfrageEingabe = sc.nextLine();

				try {
					wahlAbfrage = Integer.parseInt(wahlAbfrageEingabe);
					inputValidAuswahl = true;

					if (wahlAbfrage >= 2 && wahlAbfrage <= 7) {
						inputValidAuswahl = true;
					} else {
						System.out.println("Es wurde keine korrekte Zahl eingegeben.");
						inputValidAuswahl = false;
					}

				} catch (NumberFormatException e) {
					System.out.println("Es wurde keine Zahl eingegeben");
					inputValidAuswahl = false;
				}
			} while (!inputValidAuswahl);

			// Nach der Prüfung der richtigen Eingabe wird die gewünschte Methode
			// aufgerufen.
			switch (wahlAbfrage) {
			case 2:
				fetchOrtschaften();
				break;
			case 3:
				fetchWetterDaten();
				break;
			case 4:
				deleteWetterDaten();
				break;
			case 5:
				createUser();
				break;
			case 6:
				changePassword();
				break;
			case 7:
				deleteUser();
				break;
			}

			// Aufgerufene Methode abgeschlossen, Benutzer kann entscheiden ob das Programm
			// beendet werden soll oder eine erneute Abfrage gestartet werden soll.
			boolean programmBeenden = false;
			int beendenZahl = 0;
			do {
				System.out.println("Wollen sie weitere Mutationen vornhemen?");
				System.out.println("Für Ja: Ja");
				System.out.println("Für Nein: Nein");

				// Einlsen und überprüfen ob die Benutzereingabe korrekt ist.
				String beendenEingabe = sc.next();
				String beenden = Character.toUpperCase(beendenEingabe.charAt(0)) + beendenEingabe.substring(1);

				if (beenden.equals("Ja")) {
					beendenZahl = 1;
					programmBeenden = true;
				} else if (beenden.equals("Nein")) {
					beendenZahl = 2;
					programmBeenden = true;
				} else {
					System.out.println("Keine korrekte Eingabe.");
					programmBeenden = false;
				}
			} while (!programmBeenden);

			// Ausfhüren der korrekten Benutzereingabe.
			switch (beendenZahl) {
			case 1:
				startProgramm = false;
				break;
			case 2:
				System.out.println("Besten Dank und auf Wiedersehen.");
				startProgramm = true;
				System.exit(0);
				break;
			}
		} while (!startProgramm);
	}

	private static void fetchOrtschaften() {
		OrtschaftenReader ortschaftenReader = new OrtschaftenReader();
		if (ortschaftenReader.fetchOrtschaften() == true) {
			System.out.println("Ortschaften in dB!");
		} else {
			System.out.println("Fehler!");
		}
	}

	private static void fetchWetterDaten() {
		System.out.println("Ab welchem Datum benoetigen sie die Wetterdaten?");
		Date startDate = datumIsValid();
		WetterdatenReader wetterdatenReader = new WetterdatenReader();
		if (wetterdatenReader.fetchWetterdaten(startDate) == true) {
			System.out.println("WetterDaten in dB!");
		} else {
			System.out.println("Fehler!");
		}
	}

	private static void deleteWetterDaten() {
		JPAPersister persister = new JPAPersister();
		try {
			if (persister.loeschenAlleWetterdatensaetze() == true) {
				System.out.println("Wetterdaten geloescht!");
			} else {
				System.out.println("Fehler!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fehler!");
		}
	}

	private static boolean createUser() {
		JPAPersister persister = new JPAPersister();
		boolean inputValidCreateUser = false;
		Benutzer benutzer = new Benutzer();
		
		do {
			// Get user Properties
			System.out.println("BenutzerName");
			benutzer.setUserName(sc.next());
			System.out.println("Email:");
			benutzer.setEmail(sc.next());
			System.out.println("Role:");
			benutzer.setRole(sc.next());
			
			String pwBuffer = "";
			String currentPw = "";
			boolean pwMatch = false;
			
			// Check for pw match
			do {
				System.out.println("Password:");
				pwBuffer = sc.next();
				System.out.println("Type Password again:");
				currentPw = sc.next();
				if (pwBuffer.equals(currentPw)) {
					System.out.println("Passwords Match!");
					System.out.println("Creating User " + benutzer.getUserName());
					benutzer.setPassword(pwBuffer);
					pwMatch = true;
				} else {
					System.out.println("Password dit not match! Again..");
				}
			} while (!pwMatch);
			try {
				if (persister.speichernBenutzer(benutzer)) {
					System.out.println("Created Benutzer: " + benutzer.getUserName());
					inputValidCreateUser=true;
				} else {
					System.out.println("Error!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error!");
			}
		} while (!inputValidCreateUser);
		
		boolean result = false;
		return result;
	}

	private static boolean changePassword() {
		boolean result = false;
		JPAPersister persister = new JPAPersister();
		System.out.println("BenutzerName");
		String benutzerName = sc.next();
		Benutzer benutzer;
		
		try {
			benutzer = persister.getBenutzerbyName(benutzerName);
			
			String pwBuffer = "";
			String currentPw = "";
			boolean pwMatch = false;
			
			// Check for pw match
			do {
				System.out.println("Passwort:");
				pwBuffer = sc.next();
				System.out.println("Wiederholen sie das Passwort:");
				currentPw = sc.next();
				if (pwBuffer.equals(currentPw)) {
					System.out.println("Passwoerter Match!");
					System.out.println("Aktualisiere Benutzer" + benutzer.getUserName());
					benutzer.setPassword(pwBuffer);
					pwMatch = true;
				} else {
					System.out.println("Passwoerter nicht gleich! Nochmals..");
				}
			} while (!pwMatch);
			
			if (persister.aktualisierenBenutzer(benutzer)) {
				System.out.println("Benutzer Aktualisiert: " + benutzer.getUserName());
				result = true;
			} else {
				System.out.println("Error!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static boolean deleteUser() {
		boolean result = false;
		JPAPersister persister = new JPAPersister();
		System.out.println("BenutzerName");
		String benutzerName = sc.next();
		try {
			Benutzer benutzer = persister.getBenutzerbyName(benutzerName);
			persister.loeschenBenutzer(benutzer);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// Methode für das einlesen und überprüfen eines korrekten Datums.
	public static Date datumIsValid() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		boolean valid = false;
		String dateEingabe;
		Date date = null;
		do {
			dateEingabe = sc.next();

			try {
				LocalDate.parse(dateEingabe,
						DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
				date = dateFormatter.parse(dateEingabe);
				valid = true;

			} catch (DateTimeParseException | ParseException e) {
				System.out.println("Datum nicht korrekt!");
				System.out.println("Eingabe Datum:");
				valid = false;
			}
		} while (!valid);
		return date;
	}
}
