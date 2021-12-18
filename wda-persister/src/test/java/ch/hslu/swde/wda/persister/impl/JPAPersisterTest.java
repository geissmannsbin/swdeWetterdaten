package ch.hslu.swde.wda.persister.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetterdatensatz;
import ch.hslu.swde.wda.persister.util.JpaUtil;



class JPAPersisterTest {

private static List<Wetterdatensatz> listeWetter; 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	listeWetter = Util.createWetterListe();
	
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	/*
	@Test
	void testConnectionAndSave() {
		EntityManager em = JpaUtil.createEntityManager();
		System.out.println("EM Gestartet");
		Ortschaft ort = new Ortschaft(6300, "Zug");
		em.getTransaction().begin();
		for (Wetterdatensatz w : listeWetter) {
			em.persist(w);
		}
		
		
		em.getTransaction().commit();
		
		em.close();
		
		em=null;
		em= JpaUtil.createEntityManager();
		
		TypedQuery<Wetterdatensatz> tQry = em.createQuery("SELECT e FROM Wetterdatensatz e ORDER BY e.id", Wetterdatensatz.class);
		List<Wetterdatensatz> listeFromDb = tQry.getResultList();

		assertEquals(listeFromDb.size(), listeWetter.size());
		
	}

	 */
	
	
	@Test
	void findeOrtbyName() {
		
		JPAPersister jpa = new JPAPersister();
		
		try {
			Ortschaft ort2 = jpa.findeOrtschaftByName("Rotkreuz");
			System.out.println(ort2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
