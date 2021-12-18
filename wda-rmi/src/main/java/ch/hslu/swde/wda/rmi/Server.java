package ch.hslu.swde.wda.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import ch.hslu.swde.wda.business.BusinessLogikImpl;
import ch.hslu.swde.wda.business.api.BusinessLogik;



public class Server {


	public static void main(String[] args) {

		// IP Adresse des RMI Servers (von Aussen erreichbar) und die RMI-Port-Nummer
		String hostIp = "localhost";
		int rmiPort = 1099;

		// Host-IP als System-Property setzen (wird zu Clients im Stub zugestellt)
		System.setProperty("java.rmi.server.hostname", hostIp);
		
		

		// Entferntes Objekt erzeugen
		try {
			BusinessLogik remoteObject = new BusinessLogikImpl();
			

			// Namensdienst erstellen und starten
			Registry reg = null;
			reg = LocateRegistry.createRegistry(rmiPort);

			if (reg != null) {

				// Entferntes Objekt beim Namensdienst registrieren (binding)

				reg.rebind(BusinessLogik.RO_Name, remoteObject);
				
				System.out.println("\nRMI Server gestartet: [" + hostIp + ":" + rmiPort + "]");

				System.out.println("\nBeenden mit ENTER-Taste!\n");

				new java.util.Scanner(System.in).nextLine();


				// Unbind remote object
				reg.unbind(BusinessLogik.RO_Name);
				System.exit(0);

				}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

