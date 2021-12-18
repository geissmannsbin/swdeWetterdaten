package ch.hslu.swde.wda.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.swde.wda.domain.Benutzer;
import ch.hslu.swde.wda.persister.impl.JPAPersister;

public class BenutzerVerwaltungImpl implements BenutzerVerwaltung {

	private static Logger log = LogManager.getLogger(BusinessLogikImpl.class);
	private JPAPersister jpaPersister = null;
	
	public BenutzerVerwaltungImpl() {
		try {
			/* EntityManagerFactory erzeugen */
			jpaPersister = new JPAPersister();
		} catch (Exception e) {
			log.error("ERROR: ", e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean login(String username, String password) {
		try {
			Benutzer benutzer = jpaPersister.getBenutzerbyName(username);
			if (benutzer.getPassword().equals(password) && benutzer.getUserName().equals(username)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("ERROR: ", e);
			return false;
		}
	}

	@Override
	public Benutzer erstelleBenutzer(String role, String userName, String email, String password) {
		Benutzer benutzer = new Benutzer(role, userName, email, password);
		try {
			jpaPersister.speichernBenutzer(benutzer);
			benutzer = jpaPersister.getBenutzerbyName(userName);
			return benutzer;
		} catch (Exception e) {
			log.error("ERROR: ", e);
			return null;
		}
	}

	@Override
	public boolean loescheBenutzer(Benutzer Benutzer) {
		try {
			jpaPersister.loeschenBenutzer(Benutzer);
			return true;
		} catch (Exception e) {
			log.error("ERROR: ", e);
			return false;
		}
	}

	@Override
	public Benutzer updateBenutzer(Benutzer Benutzer) {
		try {
			jpaPersister.aktualisierenBenutzer(Benutzer);
			Benutzer returnBenutzer = jpaPersister.getBenutzerbyName(Benutzer.getUserName());
			return Benutzer;
		} catch (Exception e) {
			log.error("ERROR: ", e);
			return null;
		}
	}

	@Override
	public Benutzer getBenutzer(String username) {
		try {
			Benutzer benutzer = jpaPersister.getBenutzerbyName(username);
			return benutzer;
		} catch (Exception e) {
			log.error("ERROR: ", e);
			return null;
		}
	}
	
}
