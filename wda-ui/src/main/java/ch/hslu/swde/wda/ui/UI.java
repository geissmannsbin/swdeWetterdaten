package ch.hslu.swde.wda.ui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import ch.hslu.swde.wda.business.api.BusinessLogik;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetterdatensatz;

public class UI {

	private static BusinessLogik businessStub;
	private static Scanner sc;

	static {
		Stub stub = new Stub ("localhost", 1099);
		stub.connect();
		businessStub = stub.getStub();
		sc = new Scanner (System.in);
	}


	public static void main(String[] args) {

		//Login: Prüft den Benutzername und das Passwort in der Datenbank.
		boolean login = false;
		do {
			System.out.println("Geben sie bitte Ihr Bentzername ein: ");
			String username = sc.next();
			System.out.println("\nGeben Sie bitte Ihr Passwort ein: ");
			String password =sc.next();

			try {
				login = businessStub.a8(username, password);
				if(login == true) {
					System.out.println("Login erfolgreich.");
				}else {
					System.out.println("Benutzername oder Passwort nicht korrekt.\n");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		} while (!login);

		// "startProgramm" ist dafür da um am Ende einer Abfrage eine
		// weitere Abfrage zu tätigen.
		boolean startProgramm = false;
		do {

			// A1 Ausgeben für welche Ortschaften die Wetterdaten zur Verfügung stehen
			System.out.println("\n\nFür diese Ortschaften stehen Wetterdaten zur Verfügung: \n");
			try {

				List<Ortschaft> list = businessStub.a1();
				for (Object element : list) {
					System.out.println(element.toString());
				}

			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			// Anzeige um dem Benutzer zu zeigen zischen welchen Funktionen dieser auswählen
			// kann.
			System.out.println("\nWelche Wetterdaten benötigen Sie?\n");
			System.out.println(
					"[2] für Temperatur, Luftdruck und Feuchtigkeit für eine angegebene Ortschaft während einer angegebenen Zeitperiode.");
			System.out.println(
					"[3] für Durchschnittswerte für Temperatur, Luftdruck und Feuchtigkeit für die angegebene Ortschaft in einer angegebenen Zeitperiode.");
			System.out.println(
					"[4] für Maximal- und Minimalwerte für Temperatur, Luftdruck und Feuchtigkeit für eine angegebene Ortschaft während einer angegebenen Zeitperiode.");
			System.out.println(
					"[5] für Maximal- und Minimalwerte für Temperatur, Luftdruck und Feuchtigkeit über alle Ortschaften zu einem bestimmten Zeitpunkt.");
			System.out.println("\nBitte wählen Sie Ihre gewünschte Abfrage:");

			// Benutzereingabe überprüfen ob eine Zahl (2-4) eigegeben wurde.
			boolean inputValidAuswahl = false;
			int wahlAbfrage = 0;

			do {
				String wahlAbfrageEingabe = sc.next();

				try {
					wahlAbfrage = Integer.parseInt(wahlAbfrageEingabe);
					inputValidAuswahl = true;

					if (wahlAbfrage >= 2 && wahlAbfrage <= 5) {
						System.out.println("Ihre Eingabe " + wahlAbfrage + " ist korrekt.\n");
						inputValidAuswahl = true;
					} else {
						System.out.println("\n" + wahlAbfrage + " ist keine korrekte Zahl. Bitte geben Sie eine korrekte Zahl [2] / [3] / [4] / [5] ein.");
						inputValidAuswahl = false;
					}

				} catch (NumberFormatException e) {
					System.out.println("\n" + wahlAbfrageEingabe + " ist keine Zahl. Bitte geben Sie eine Zahl [2] / [3] / [4] / [5] ein.");
					inputValidAuswahl = false;
				}
			} while (!inputValidAuswahl);

			// Nach der Prüfung der richtigen Eingabe wird die gewünschte Methode
			// aufgerufen.
			switch (wahlAbfrage) {
			case 2:
				try {
					wetterdatenAusgabe();
				} catch (RemoteException e) {
					System.out.println("Abfrage fehlgeschlagen");
					e.printStackTrace();
				}
				break;
			case 3:
				durchschnittswerte();
				break;
			case 4:
				minMaxWerteEineOrtschaft();
				break;
			case 5:
				minMaxWerteAlleOrtschaften();
				break;
			}
			// Aufgerufene Methode abgeschlossen, Benutzer kann entscheiden ob das Programm
			// beendet werden soll oder eine erneute Abfrage gestartet werden soll.
			boolean programmBeenden = false;
			int beendenZahl = 0;
			do {
				System.out.println("\nBenötigen Sie weitere Wetterdaten? [Ja]" );
				System.out.println("Oder wollen Sie das Programm beenden? [Exit]");

				// Einlesen und überprüfen ob die Benutzereingabe korrekt ist.
				String beendenEingabe = sc.next();
				String beenden = Character.toUpperCase(beendenEingabe.charAt(0)) + beendenEingabe.substring(1);

				if (beenden.equals("Ja")) {
					beendenZahl = 1;
					programmBeenden = true;
				} else if (beenden.equals("Exit")) {
					beendenZahl = 2;
					programmBeenden = true;
				} else {
					System.out.println("Keine korrekte Eingabe.\n");
					programmBeenden = false;
				}
			} while (!programmBeenden);

			// Ausfhüren der korrekten Benutzereingabe.
			switch (beendenZahl) {
			case 1:
				startProgramm = false;
				break;
			case 2:
				System.out.println("\nBesten Dank und auf Wiedersehen.");
				startProgramm = true;
				System.exit(0);
				break;
			}

		} while (!startProgramm);

	}

	// Methode für das einlsen und überprüfen der korrekten Ortschaft
	public static String eingabeOrtschaft() {
		boolean inputValidOrtschaft = false;
		String nameOrtschaft = null;
		do {
			System.out.println("Für welche Ortschaft benötigen Sie die Wetterdaten?");

			try {
				// Wahl einlesen
				String nameOrtschaftEingabe = sc.next();
				nameOrtschaft = nameOrtschaftEingabe.substring(0, 1).toUpperCase()
						+ nameOrtschaftEingabe.substring(1).toLowerCase();

					List<Ortschaft> list = businessStub.a1();

					// Input validieren
					int p = list.size();
					for (int i = 0; i <= p; i++) {
						if (list.get(i).getName().equals(nameOrtschaft)) {
							System.out.println("Ihre Eingabe " + nameOrtschaft + " ist korrekt.\n");
							inputValidOrtschaft = true;
							break;
						} else if (p == i + 1) {
							System.out.println("Ihre Eingabe " + nameOrtschaft + " ist in der Datenbank nicht vorhanden.\n");
							inputValidOrtschaft = false;
						}
					}
			}
						catch (Exception e) {
					//e.printStackTrace();
				}


		} while (!inputValidOrtschaft);

		return nameOrtschaft;
	}

	/* Methode für das einlesen und überprüfen eines korrekten start Datums.
	 * Prüft ob der eingelesene Sting als date konvertiert werden kann,
	 * wie auch ob es ein valides Datum ist. */
	public static Date startDatumIsValid() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		boolean valid = false;
		String dateEingabe;
		Date startDate = null;
		do {
			System.out.println("Eingabe start Datum(z.B. 1999-12-31): ");
			dateEingabe = sc.next();

			try {
				LocalDate.parse(dateEingabe,
						DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
				startDate = dateFormatter.parse(dateEingabe);
				System.out.println("Datum " + startDate + " ist korrekt!\n");
				valid = true;

			} catch (DateTimeParseException | ParseException e) {
				System.out.println("Datum " + dateEingabe + " ist nicht korrekt!\n");
				valid = false;
			}

		} while (!valid);
		return startDate;
	}

	/* Methode für das einlesen und überprüfen eines korrekten end Datums.
	 * Prüft ob der eingelesene Sting als date konvertiert werden kann,
	 * ob es ein valides Datum ist und ob das end Datum später als das start Datum ist. */
		public static Date endDatumIsValid(Date startDate) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

			boolean valid = false;
			String dateEingabe;
			Date date = null;
			do {
				System.out.println("Eingabe end Datum(z.B. 1999-12-31): ");
				dateEingabe = sc.next();

				try {
					LocalDate.parse(dateEingabe,
							DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
					date = dateFormatter.parse(dateEingabe);
					if (date.after(startDate)) {
					System.out.println("Datum " + date + " ist korrekt!\n");
					valid = true;}
					else {
						System.out.println("Das end Datum muss zu einem späteren Zeitpunkt als das start Datum sein.\n");
						valid = false;
					}

				} catch (DateTimeParseException | ParseException e) {
					System.out.println("Datum " + dateEingabe + " ist nicht korrekt!\n");
					valid = false;
				}

			} while (!valid);
			return date;
		}

	/* Ausgabe A2: Temperatur, Luftdruck und Feuchtigkeit Ausgabe für angegebene Ortschaft
	 * und Zeit. Mit den Methoden eingabeOrtschaft, startDatumIsValid und endDatumIsValid werden
	 * die Daten vom Benutzer eingelesen. Diese werden dann benötigt für die Methode businessStub.a2
	 * welche die Daten in eine List speichert. Diese List wird dann mit einer for Schleife ausgegeben.
	 * Der Benutzer kann dann noch wählen ob die Daten gespeichert werden sollen, für dies wird die Methode
	 * Export.listToJSON verwendet */
	public static void wetterdatenAusgabe() throws RemoteException {
		String ortschaft = eingabeOrtschaft();
		System.out.println("Für welche Zeitperiode benötigen Sie die Wetterdaten?");
		Date startDate = startDatumIsValid();
		Date endDate = endDatumIsValid(startDate);

		System.out.println("Ihre Wetterdaten für " + ortschaft + " von " + startDate + " bis " + endDate + ":\n");
		List<Wetterdatensatz> list = businessStub.a2(ortschaft, startDate, endDate);

		for (Object element : list) {
			System.out.println(element.toString());
		}
		boolean datenSpeichern = false;
		int datenSpeichernZahl = 0;
		do {
			System.out.println("\nWollen sie diese Daten speichern? [Ja] / [Nein]");

			// Einlsen und überprüfen ob die Benutzereingabe korrekt ist.
			String datenSpeichernEingabe = sc.next();
			String datenSpeichernString = Character.toUpperCase(datenSpeichernEingabe.charAt(0)) + datenSpeichernEingabe.substring(1);

			if (datenSpeichernString.equals("Ja")) {
				datenSpeichernZahl = 1;
				datenSpeichern = true;
			} else if (datenSpeichernString.equals("Nein")) {
				datenSpeichernZahl = 2;
				datenSpeichern = true;
			} else {
				System.out.println("Keine korrekte Eingabe.");
				datenSpeichern = false;
			}
		} while (!datenSpeichern);

		switch (datenSpeichernZahl) {
		case 1:
			Export e = new Export();
			String m = e.listToJSON(list);
			String outputFormat = "json";
			try {
				e.toFile(m, outputFormat);
				System.out.println("Ihre Wetterdaten wurden erfolgreich gespeichert.");
			} catch (IOException e1) {
				System.out.println("Ihre Wetterdaten konnten nicht gespeichert werden.");
				e1.printStackTrace();
			}
			break;
		case 2:
			break;
		}
	}

	/* Ausgabe A03: Durchschnittswerte für Temperatur, Luftdruck und Feuchtigkeit für
	 * angegebende Ortschaft und Zeitraum ausgeben. Mit den Methoden eingabeOrtschaft, startDatumIsValid und endDatumIsValid werden
	 * die Daten vom Benutzer eingelesen. Diese werden dann benötigt für die Methode businessStub.a3
	 * welche die Daten in eine Map speichert. Diese Map wird dann mit einer for Schleife ausgegeben.
	 * Der Benutzer kann dann noch wählen ob die Daten gespeichert werden sollen, für dies wird die Methode
	 * Export.mapFloatToJSON verwendet */
	public static void durchschnittswerte() {
		String ortschaft = eingabeOrtschaft();
		System.out.println("Für welche Zeitperiode benötigen Sie die Wetterdaten?");
		Date startDate = startDatumIsValid();
		Date endDate = endDatumIsValid(startDate);

		Map<String, Float> map = null;
		try {

		System.out.println("Die durchschnittlichen Wetterdaten für " + ortschaft + " zwischen " + startDate + " und " + endDate + " sind:\n");
			map = businessStub.a3(ortschaft, startDate, endDate);
			for (Entry<String, Float> e : map.entrySet()) {
				System.out.println(e.getKey() + ": " + e.getValue());
			}
		} catch (RemoteException e1) {
			System.out.println("Abfrage fehlgeschlagen");
			e1.printStackTrace();
		}

		boolean datenSpeichern = false;
		int datenSpeichernZahl = 0;
		do {
			System.out.println("\nWollen sie diese Daten speichern? [Ja] / [Nein]");

			// Einlsen und überprüfen ob die Benutzereingabe korrekt ist.
			String datenSpeichernEingabe = sc.next();
			String datenSpeichernString = Character.toUpperCase(datenSpeichernEingabe.charAt(0)) + datenSpeichernEingabe.substring(1);

			if (datenSpeichernString.equals("Ja")) {
				datenSpeichernZahl = 1;
				datenSpeichern = true;
			} else if (datenSpeichernString.equals("Nein")) {
				datenSpeichernZahl = 2;
				datenSpeichern = true;
			} else {
				System.out.println("Keine korrekte Eingabe.");
				datenSpeichern = false;
			}
		} while (!datenSpeichern);

		switch (datenSpeichernZahl) {
		case 1:
			Export e = new Export();
			String s = e.mapFloatToJSON(map);
			String outputFormat = "json";
			try {
				e.toFile(s, outputFormat);
				System.out.println("Ihre Wetterdaten wurden erfolgreich gespeichert.");
			} catch (IOException e1) {
				System.out.println("Ihre Wetterdaten konnten nicht gespeichert werden.");
				e1.printStackTrace();
			}
			break;
		case 2:
			break;
		}
	}

	/* Ausgabe A04: Min und Max für Temperatur, Luftdruck und Feuchtigkeit für angegebende
	 * Ortschaft und Zeitraum ausgeben. Mit den Methoden eingabeOrtschaft, startDatumIsValid und endDatumIsValid werden
	 * die Daten vom Benutzer eingelesen. Diese werden dann benötigt für die Methode businessStub.a4
	 * welche die Daten in eine Map speichert. Diese Map wird dann mit einer for Schleife ausgegeben.
	 * Der Benutzer kann dann noch wählen ob die Daten gespeichert werden sollen, für dies wird die Methode
	 * Export.mapWetterdatensatzToJSON verwendet. */
	public static void minMaxWerteEineOrtschaft() {
		String ortschaft = eingabeOrtschaft();
		System.out.println("Für welche Zeitperiode benötigen Sie die Wetterdaten?");
		Date startDate = startDatumIsValid();
		Date endDate = endDatumIsValid(startDate);

		Map<String, Wetterdatensatz> map = null;
		try {
			System.out.println("Ihre maximal und minimal Werte der Wetterdaten für " + ortschaft + " von " + startDate + " bis " + endDate + ":\n");
			map = businessStub.a4(ortschaft, startDate, endDate);
			for (Entry<String, Wetterdatensatz> e : map.entrySet()) {
				System.out.println(e.getKey() + ": " + e.getValue());
			}
		} catch (RemoteException | RuntimeException e1) {
			System.out.println("Abfrage fehlgeschlagen.");
			//e1.printStackTrace();
		}

		boolean datenSpeichern = false;
		int datenSpeichernZahl = 0;
		do {
			System.out.println("\nWollen sie diese Daten speichern? [Ja] / [Nein]");

			// Einlsen und überprüfen ob die Benutzereingabe korrekt ist.
			String datenSpeichernEingabe = sc.next();
			String datenSpeichernString = Character.toUpperCase(datenSpeichernEingabe.charAt(0)) + datenSpeichernEingabe.substring(1);

			if (datenSpeichernString.equals("Ja")) {
				datenSpeichernZahl = 1;
				datenSpeichern = true;
			} else if (datenSpeichernString.equals("Nein")) {
				datenSpeichernZahl = 2;
				datenSpeichern = true;
			} else {
				System.out.println("Keine korrekte Eingabe.");
				datenSpeichern = false;
			}
		} while (!datenSpeichern);

		switch (datenSpeichernZahl) {
		case 1:
			Export e = new Export();
			String m = e.mapWetterdatensatzToJSON(map);
			String outputFormat = "json";
			try {
				e.toFile(m, outputFormat);
				System.out.println("Ihre Wetterdaten wurden erfolgreich gespeichert.");
			} catch (IOException e1) {
				System.out.println("Ihre Wetterdaten konnten nicht gespeichert werden.");
				e1.printStackTrace();
			}
			break;
		case 2:
			break;
		}
	}

	/* A05: Min und Max für Temperatur, Luftdruck und Feuchtigkeit für angegebende
	Ortschaft und Zeitraum ausgeben. Mit den Methoden startDatumIsValid werden
	 * die Daten vom Benutzer eingelesen. Diese werden dann benötigt für die Methode businessStub.a5
	 * welche die Daten in eine Map speichert. Diese Map wird dann mit einer for Schleife ausgegeben.
	 * Der Benutzer kann dann noch wählen ob die Daten gespeichert werden sollen, für dies wird die Methode
	 * Export.mapWetterdatensatzToJSON verwendet. */
	public static void minMaxWerteAlleOrtschaften() {
		System.out.println("Für welche Tag benötigen Sie die Wetterdaten?");
		System.out.println("Eingabe Datum(z.B. 1999-12-31): ");
		Date startDate = startDatumIsValid();


		Map<String, Wetterdatensatz> map = null;
		try {
			System.out.println("Ihre maximal und minimal Werte der Wetterdaten für den " + startDate + ":\n");
			map = businessStub.a5(startDate);
			for (Entry<String, Wetterdatensatz> e : map.entrySet()) {
				System.out.println(e.getKey() + ": " + e.getValue());


			}
		} catch (RemoteException | RuntimeException e1) {
			System.out.println("Abfrage fehlgeschlagen.");
			//e1.printStackTrace();
		}
		boolean datenSpeichern = false;
		int datenSpeichernZahl = 0;
		do {
			System.out.println("\nWollen sie diese Daten speichern? [Ja] / [Nein]");

			// Einlsen und überprüfen ob die Benutzereingabe korrekt ist.
			String datenSpeichernEingabe = sc.next();
			String datenSpeichernString = Character.toUpperCase(datenSpeichernEingabe.charAt(0)) + datenSpeichernEingabe.substring(1);

			if (datenSpeichernString.equals("Ja")) {
				datenSpeichernZahl = 1;
				datenSpeichern = true;
			} else if (datenSpeichernString.equals("Nein")) {
				datenSpeichernZahl = 2;
				datenSpeichern = true;
			} else {
				System.out.println("Keine korrekte Eingabe.");
				datenSpeichern = false;
			}
		} while (!datenSpeichern);

		switch (datenSpeichernZahl) {
		case 1:
			Export e = new Export();
			String m = e.mapWetterdatensatzToJSON(map);
			String outputFormat = "json";
			try {
				e.toFile(m, outputFormat);
				System.out.println("Ihre Wetterdaten wurden erfolgreich gespeichert.");
			} catch (IOException e1) {
				System.out.println("Ihre Wetterdaten konnten nicht gespeichert werden.");
				e1.printStackTrace();
			}
			break;
		case 2:
			break;
		}

	}
}