package com.ut.bataapp.objects;

public class Uitslag {
	private Team team;
	private Etappe etappe;
	private String tijd;
	private String foutcode;
	
	/**
	 * 
	 * @param team_id - ID van team
	 * @param etappe_id - ID van etappe (vanaf 1)
	 * @param tijd - Tijd van deze uitslag
	 * @param foutcode - Eventuele foutcode
	 */
	public Uitslag(Team team, Etappe etappe, String tijd, String foutcode) {
		this.team = team;
		this.etappe = etappe;
		this.tijd = tijd;
		this.foutcode = foutcode;
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
	
	public String getFoutcode(){
		return foutcode;
	}
}
