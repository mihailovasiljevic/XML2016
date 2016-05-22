package model;

import java.io.Serializable;

public class Korisnik implements Serializable{
	
	private String ime;
	private String prezime;
	private String username;
	private String password;
	private String uloga;
	
	
	public Korisnik(String ime, String prezime, String username,
			String password, String uloga) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.username = username;
		this.password = password;
		this.uloga = uloga;
	}


	public Korisnik() {
		// TODO Auto-generated constructor stub
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUloga() {
		return uloga;
	}


	public void setUloga(String uloga) {
		this.uloga = uloga;
	}
	
	
	
	
	
	
	

}
