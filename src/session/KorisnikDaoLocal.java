package session;

import model.Korisnik;

public interface KorisnikDaoLocal extends GenericDaoLocal<Korisnik, Integer> {

	public Korisnik findKorisnikWithUsernameAndPassword(String username, String password);
}
