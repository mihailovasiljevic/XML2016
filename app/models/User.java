package models;

import java.io.Serializable;

public class User implements Serializable{
	
	private String username;
	private String password;
	private String repeat_password;
	private String ime;
	private String prezime;
	private String uloga;
	private String email;

	


	public User(String username, String password, String repeat_password,
			String ime, String prezime, String uloga, String email) {
		super();
		this.username = username;
		this.password = password;
		this.repeat_password = repeat_password;
		this.ime = ime;
		this.prezime = prezime;
		this.uloga = uloga;
		this.email = email;

	}

	public String getRepeat_password() {
		return repeat_password;
	}

	public void setRepeat_password(String repeat_password) {
		this.repeat_password = repeat_password;
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

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
