package models;

import java.io.Serializable;

public class Player implements Serializable {

	private String name;
	private String surname;
	private String club;
	
	public Player(String name, String surname, String club) {
		this.name = name;
		this.surname = surname;
		this.club = club;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}


	
}
