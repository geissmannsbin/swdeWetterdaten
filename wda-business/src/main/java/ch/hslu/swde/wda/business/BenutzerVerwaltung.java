package ch.hslu.swde.wda.business;

import ch.hslu.swde.wda.domain.Benutzer;

public interface BenutzerVerwaltung {
	
	/**
	 * Existiert der Benutzer
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean login(String username, String password);

	/**
	 * erstellt einen Benutzer
	 * @param role
	 * @param userName
	 * @param email
	 * @param password
	 * @return
	 */
	public Benutzer erstelleBenutzer(String role, String userName, String email, String password);
	
	/**
	 * loescht einen Benutzr
	 * @param Benutzer
	 * @return
	 */
	public boolean loescheBenutzer(Benutzer Benutzer);
	
	/**
	 * Updated einen Benutzer
	 * @param Benutzer
	 * @return
	 */
	public Benutzer updateBenutzer(Benutzer Benutzer);
	
	/**
	 * holt einen Benutzer nach username
	 * @param username
	 * @return
	 */
	public Benutzer getBenutzer(String username);
	
}
