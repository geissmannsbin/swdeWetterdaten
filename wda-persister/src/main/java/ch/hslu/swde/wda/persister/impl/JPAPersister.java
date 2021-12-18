package ch.hslu.swde.wda.persister.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import ch.hslu.swde.wda.domain.Benutzer;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetterdatensatz;
import ch.hslu.swde.wda.persister.api.Persister;
import ch.hslu.swde.wda.persister.util.JpaUtil;

public class JPAPersister implements Persister{

	@Override
	public Wetterdatensatz speichernWetterdatensatz(Wetterdatensatz wd) throws Exception {
		//Entity Manager erstellen
		EntityManager em = JpaUtil.createEntityManager();

		em.getTransaction().begin();
		em.persist(wd);
		em.getTransaction().commit();
		em.close();

		return wd;
	}

	@Override
	public boolean speichernListeWetterdatensatz(List<Wetterdatensatz> wd) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();

		em.getTransaction().begin();

		for( Wetterdatensatz w : wd) {
			em.persist(w);
		}
		
		em.getTransaction().commit();

		em.close();
		return true;
	}



	@Override
	public Wetterdatensatz aktualisierenWetterdatensatz(Wetterdatensatz wd) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();

		em.getTransaction().begin();
		em.merge(wd);
		em.getTransaction().commit();

		return wd;
	}

	@Override
	public boolean loeschenWetterdatensatz(Wetterdatensatz wd) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();

		em.getTransaction().begin();
		em.remove(wd);
		em.getTransaction().commit();

		return true;
	}

	@Override
	public boolean loeschenWetterdatensatzById(int WdId) throws Exception {

		EntityManager em = JpaUtil.createEntityManager();


		// Hier muss im Wetterdatensatz ein Query erstellt werden.
		TypedQuery<Wetterdatensatz> tQuery = em.createQuery ("SELECT w FROM Wetterdatensatz w WHERE w.id=:id", Wetterdatensatz.class);
		tQuery.setParameter("id", WdId);

		Wetterdatensatz wdLoeschen = tQuery.getSingleResult();
		em.close();
		loeschenWetterdatensatz(wdLoeschen);

		return true;
	}
	
	@Override
	public boolean loeschenAlleWetterdatensaetze() throws Exception{
		
		EntityManager em = JpaUtil.createEntityManager();
		Query query = em.createQuery("DELETE FROM Wetterdatensatz");
		query.executeUpdate();
		
		return true;
	}

	@Override
	public List<Wetterdatensatz> findeWetterdatensatzByOrt(String Ortschaft, Date Datum) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();
		// Hier muss im Wetterdatensatz ein Query erstellt werden.
		TypedQuery<Wetterdatensatz> tQuery = em.createQuery ("SELECT w FROM Wetterdatensatz w WHERE w.ortschaft=:ortschaft AND w.datum=:datum", Wetterdatensatz.class);
		tQuery.setParameter("datum", Datum, TemporalType.DATE);
		tQuery.setParameter("ortschaft", Ortschaft);

		List<Wetterdatensatz> listWd = tQuery.getResultList();
		em.close();

		return listWd;
	}

	@Override
	public Wetterdatensatz findeWetterdatensatzById(int WdId) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();


		// Hier muss im Wetterdatensatz ein Query erstellt werden.
		TypedQuery<Wetterdatensatz> tQuery = em.createQuery ("SELECT w FROM Wetterdatensatz w WHERE w.id=:id", Wetterdatensatz.class);
		tQuery.setParameter("id", WdId);
		

		Wetterdatensatz wd = tQuery.getSingleResult();
		em.close();
		
		return wd;

	}

	@Override
	public List<Wetterdatensatz> findeWetterdatensatzZwischenDatum(String Ortschaft, Date startdatum, Date schlussdatum)throws Exception {
		EntityManager em = JpaUtil.createEntityManager();
		
		Ortschaft ortschaftObj = findeOrtschaftByName(Ortschaft);
		
		// Hier muss im Wetterdatensatz ein Query erstellt werden.
		TypedQuery<Wetterdatensatz> tQuery = em.createQuery ("SELECT w FROM Wetterdatensatz w WHERE w.ortschaft=:ortschaft AND w.datum BETWEEN :datum1 AND :datum2", Wetterdatensatz.class);
		tQuery.setParameter("datum1", startdatum, TemporalType.DATE);
		tQuery.setParameter("datum2", schlussdatum, TemporalType.DATE);
		tQuery.setParameter("ortschaft", ortschaftObj);

		List<Wetterdatensatz> listWd = tQuery.getResultList();
		em.close();

		return listWd;
	}

	@Override
	public List<Wetterdatensatz> alleWetterdatensatz() throws Exception {
		EntityManager em = JpaUtil.createEntityManager();
		TypedQuery<Wetterdatensatz> tQuery = em.createQuery ("SELECT w FROM Wetterdatensatz w", Wetterdatensatz.class);
		List<Wetterdatensatz> listWd = tQuery.getResultList();
		em.close();

		return listWd;
	}

	@Override
	public List<Ortschaft> alleOrtschaften() throws Exception {
		EntityManager em = JpaUtil.createEntityManager();
		TypedQuery<Ortschaft> tQuery = em.createQuery ("SELECT o FROM Ortschaft o", Ortschaft.class);
		List<Ortschaft> listOrtschaften = tQuery.getResultList();
		
		return listOrtschaften;
	}

	@Override
	public Ortschaft findeOrtschaftByName(String ort) throws Exception {
		
		EntityManager em = JpaUtil.createEntityManager();


		// Hier muss im Wetterdatensatz ein Query erstellt werden.
		TypedQuery<Ortschaft> tQuery = em.createQuery ("SELECT o FROM Ortschaft o WHERE o.name=:ortschaft", Ortschaft.class);
		tQuery.setParameter("ortschaft", ort);
		

		Ortschaft ortDB = tQuery.getSingleResult();
		em.close();
		 
		return ortDB;
		
	}

	@Override
	public List<Wetterdatensatz> alleWetterdatensatzByDatum(Date zeitpunkt) throws Exception {
		Date dayLater = new Date(zeitpunkt.getTime() + (1000 * 60 * 60 * 24));
		
		EntityManager em = JpaUtil.createEntityManager();
		TypedQuery<Wetterdatensatz> tQuery = em.createQuery ("SELECT w FROM Wetterdatensatz w WHERE w.datum BETWEEN :datum1 AND :datum2", Wetterdatensatz.class);
		tQuery.setParameter("datum1", zeitpunkt, TemporalType.DATE);
		tQuery.setParameter("datum2", dayLater, TemporalType.DATE);
		List<Wetterdatensatz> listWd = tQuery.getResultList();
		em.close();
		return listWd;
	}

	@Override
	public boolean speichernListeOrtschaft(List<Ortschaft> listOrtschaft) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();

		em.getTransaction().begin();

		for( Ortschaft o : listOrtschaft) {
			em.persist(o);
		}
		
		em.getTransaction().commit();

		em.close();
		return true;
	}

	@Override
	public boolean speichernBenutzer(Benutzer bn) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();

		em.getTransaction().begin();
		em.persist(bn);
		em.getTransaction().commit();
		em.close();

		return true;
	}

	@Override
	public boolean loeschenBenutzer(Benutzer bn) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();
		em.getTransaction().begin();
		bn = em.merge(bn);		
		em.remove(bn);
		em.getTransaction().commit();

		return true;
	}

	@Override
	public boolean aktualisierenBenutzer(Benutzer bn) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();

		em.getTransaction().begin();
		em.merge(bn);
		em.getTransaction().commit();

		return true;
	}

	@Override
	public Benutzer getBenutzerbyName(String name) throws Exception {
		EntityManager em = JpaUtil.createEntityManager();


		// Hier muss im Wetterdatensatz ein Query erstellt werden.
		TypedQuery<Benutzer> tQuery = em.createQuery ("SELECT b FROM Benutzer b WHERE b.userName=:name", Benutzer.class);
		tQuery.setParameter("name", name);
		

		Benutzer bn = tQuery.getSingleResult();
		em.close();
		
		return bn;
		
	}

	
	
	
	


	



}
