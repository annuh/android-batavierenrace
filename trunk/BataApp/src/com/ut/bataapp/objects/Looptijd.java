/*
 * Versie: v2
 * Date: 07-03-12 15:15
 * By: Jochem Elsinga
 * Update: Added Setters
 * Update: Organized Getters
 * Update: Added toString()
 */

package com.ut.bataapp.objects;

public class Looptijd {
	private String teamnaam;
	private int teamStartnummer;
	private int teamStartgroep;
	private int etappe;
	private String tijd;
	private String foutcode;
	private String snelheid;
	
	/**
	 * 
	 * @param team_id - ID van team
	 * @param etappe_id - ID van etappe (vanaf 1)
	 * @param tijd - Tijd van deze uitslag
	 * @param foutcode - Eventuele foutcode
	 */
	public Looptijd(String teamnaam, int teamStartnummer, int teamStartgroep, int etappe, String tijd, String foutcode) {
		this.teamnaam = teamnaam;
		this.teamStartnummer = teamStartnummer;
		this.teamStartgroep = teamStartgroep;
		this.etappe = etappe;
		this.tijd = tijd;
		this.foutcode = foutcode;
	}
	
	/*Lege constructor*/
	public Looptijd(){}
	
	/*Setters*/
	public void setTeamNaam(String teamNaam){this.teamnaam = teamNaam;}
	public void setTeamStartgroep(int teamStartgroep){this.teamStartgroep = teamStartgroep;}
	public void setTeamStartnummer(int teamStartnummer){this.teamStartnummer = teamStartnummer;}
	public void setEtappe(int etappe){this.etappe = etappe;}
	public void setTijd(String tijd){this.tijd = tijd;}
	public void setFoutcode(String foutcode){this.foutcode = foutcode;}
	
	/*Getters*/
	public String getTeamNaam(){return teamnaam;}
	public int getTeamStartnummer(){return teamStartnummer;}
	public int getTeamStartgroep(){return teamStartgroep;}
	public int getEtappe(){return etappe;}
	public String getTijd(){return tijd;}
	public String getFoutcode(){return foutcode;}
	
	/*String Formaat*/
	public String toString(){
		return "Team: "+getTeamNaam()+" op etappe: "+getEtappe()+" met tijd: "+getTijd()+" en/of foutcode: "+getFoutcode()+'\n';
	}
}
