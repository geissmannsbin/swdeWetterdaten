package ch.hslu.swde.wda.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.rmi.Naming;

import ch.hslu.swde.wda.business.api.BusinessLogik;

public class Stub {
	
	private String hostIp;
	private int rmiPort;
	private BusinessLogik stub;
	
	public Stub (String hostIp, int rmiPort) {
		this.hostIp = hostIp;
		this.rmiPort = rmiPort;
	}
	
	
	public void connect () {
		this.hostIp = hostIp;
		this.rmiPort = rmiPort;
		// policy-Datei angeben und SecurityManager installieren
		if (System.getSecurityManager() == null) {
			String file = System.getProperty("user.home") + File.separator + "Documents" +
					File.separator + "policy.policy";
			try {
				PrintWriter pw;
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file),Charset.forName("UTF-8"))));
				pw.println("grant {	permission java.security.AllPermission;};");
			pw.flush();
			pw.close();
			System.setProperty("java.security.policy", file);
				
			
			} catch(Exception e) {
				System.out.println("File was not found --> couldn't write policy.policy File");
				e.printStackTrace();
			}
			System.setSecurityManager(new SecurityManager());
		}
		// URL definieren und Referenz auf das entf. Objekt holen
		String url = "rmi://" + this.hostIp + ":" + this.rmiPort + "/" + BusinessLogik.RO_Name;
		try {
			stub = (BusinessLogik) Naming.lookup(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BusinessLogik getStub () {
		return this.stub;
	}

}
