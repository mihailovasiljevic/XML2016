package session;

import java.util.List;
 

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import model.Korisnik;



@Stateless
@Local(KorisnikDaoLocal.class)
public class KorisnikDaoBean extends GenericDaoBean<Korisnik, Integer> implements KorisnikDaoLocal {

	public Korisnik findKorisnikWithUsernameAndPassword(String username, String password) {
		Query q = em
				.createNamedQuery("findKorisnikWithUsernameAndPassword");
		q.setParameter("username", username);
		q.setParameter("password", password);
		Korisnik result = (Korisnik) q.getSingleResult();
		return result;
	}

	
}
