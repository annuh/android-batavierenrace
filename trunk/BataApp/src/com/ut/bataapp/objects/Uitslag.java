/*
 * Versie: v2
 * Date: 07-03-12 15:15
 * By: Jochem Elsinga
 * Update: Added Setters
 * Update: Organized Getters
 * Update: Added toString()
 */

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
	
	
	/*Setters*/
	public void setTeam(Team team){this.team = team;}
	public void setEtappe(Etappe etappe){this.etappe = etappe;}
	public void setTijd(String tijd){this.tijd = tijd;}
	public void setFoutcode(String foutcode){this.foutcode = foutcode;}
	
	/*Getters*/
	public Team getTeam(){return team;}
	public Etappe getEtappe(){return etappe;}
	public String getTijd(){return tijd;}
	public String getFoutcode(){return foutcode;}
	
	/*String Formaat*/
	public String toString(){
		return "Team: "+getTeam().getNaam()+" op etappe: "+getEtappe().getId()+" met tijd: "+getTijd()+" en/of foutcode: "+getFoutcode()+'\n';
	}
}
