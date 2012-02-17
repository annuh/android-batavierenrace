package com.ut.bataapp.objects;

public class Team {
	private int id;
	private String naam;
	private Object klassement;
	
	public Team(int id, String naam) {
		this.id = id;
		this.naam = naam;
	}
	
	public int getId(){
		return id;
	}
	
	public String getNaam() {
		return naam;
	}
	
	public Object getKlassement() {
		return klassement;
	}
}
