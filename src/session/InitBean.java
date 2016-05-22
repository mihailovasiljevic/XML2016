package session;

import java.util.Set;

import javax.ejb.Init;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Korisnik;



@Stateless
@Remote(Init.class)
public class InitBean implements session.Init {

	@PersistenceContext(unitName = "XML-Parlieament")
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public void init() {
		
		Korisnik user = new Korisnik();
		user.setIme("Rajko");
		user.setPrezime("Ilic");
		user.setUsername("R");
		user.setPassword("rrr");
		user.setUloga("Gradjanin");
		em.persist(user);
		
		Korisnik user1 = new Korisnik();
		user1.setIme("Milos");
		user1.setPrezime("Savic");
		user1.setUsername("M");
		user1.setPassword("mmm");
		user1.setUloga("Odbornik");
		em.persist(user1);
		
		Korisnik user2 = new Korisnik();
		user2.setIme("Nemanja");
		user2.setPrezime("Starcev");
		user2.setUsername("N");
		user2.setPassword("nnn");
		user2.setUloga("Predsednik");
		em.persist(user2);
		
		Korisnik user3 = new Korisnik();
		user3.setIme("Mihailo");
		user3.setPrezime("Vasiljevic");
		user3.setUsername("M");
		user3.setPassword("mmm");
		user3.setUloga("Odbornik");
		em.persist(user3);

	}
}
