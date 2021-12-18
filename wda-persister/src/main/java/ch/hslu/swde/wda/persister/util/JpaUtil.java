package ch.hslu.swde.wda.persister.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class JpaUtil {

	private static Logger log = LogManager.getLogger(JpaUtil.class);
	
	private static EntityManagerFactory entityManagerFactory = null;
	
	private static final String TEST_PU = "testPU";
	private static final String PRODUCTIVE_PU = "productivePU";
	private static String activePU = PRODUCTIVE_PU;

	static {
		try {
			/* EntityManagerFactory erzeugen */
			entityManagerFactory = Persistence.createEntityManagerFactory(activePU);
		} catch (Throwable e) {
			log.error("ERROR: ", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
}
