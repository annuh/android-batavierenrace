package com.ut.bataapp.objects;

public class Uitslag {
	private Team team;
	private Etappe etappe;
	private String tijd;
	
	public Uitslag(Team team, Etappe etappe, String tijd) {
		this.team = team;
		this.etappe = etappe;
		this.tijd = tijd;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public Etappe getEtappe(){
		return etappe;
	}
	
	public String getTijd(){
		return tijd;
	}
}
