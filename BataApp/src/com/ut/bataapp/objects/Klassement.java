/*
 * Versie: v2
 * Date: 08-03-12 13:05
 * By: Jochem Elsinga
 * Update: removed ID becuase a klassement doesnt have an id.
 * Update: Format of code
 * Update: Added id and naam setter
 */

package com.ut.bataapp.objects;

import java.util.HashMap;
import java.util.Map;

public class Klassement {
	
	String naam;
	HashMap<Integer, KlassementInfo> uitslag;
	
	/**
	 * @param naam - Naam dit klassement
	 * @param uitslag - Uitslag van dit klassement
	 */
	public Klassement(String naam, HashMap<Integer, KlassementInfo> uitslag) {
		this.naam = naam;
		this.uitslag = uitslag;
	}
	
	//LEGE CONSTRUCTOR!
	public Klassement(){
		this.uitslag = new HashMap<Integer,KlassementInfo>();
	}
		
	/*Setters*/
	public void setNaam(String naam){this.naam = naam;}
	
	/*Getters*/
	public String getNaam() {return naam;}
	public HashMap<Integer, KlassementInfo> getUitslag(){return uitslag;}
	public Iterable<KlassementInfo> getKlassementInfo() {return uitslag.values();}
	public int getUitslagFromTeam(KlassementInfo info) {
		for (Map.Entry<Integer, KlassementInfo> entry : uitslag.entrySet()) {
		    int key = entry.getKey();
		    KlassementInfo value = entry.getValue();
		    if(value == info)
		    	return key;
		}
		return -1;
	}
	
	public String toString(){
		return "klassement: "+getNaam();
	}
	
	/**
	 * Voegt team toe aan dit klassement
	 * @ensures team.getKlassement() == this
	 * @param team
	 * @param plaats
	 */
	public void addKlassementInfo(KlassementInfo info, int plaats){
		uitslag.put(plaats, info);
	}
	
	public class KlassementInfo{
		
		String teamNaam;
		String tijd;
		int teamStartNummer;
		
		public KlassementInfo(){}
		
		public void setTeamNaam(String teamNaam){ this.teamNaam = teamNaam;}
		public void setTijd(String tijd){ this.tijd = tijd;}
		public void setTeamStartNummer(int teamStartNummer){ this.teamStartNummer = teamStartNummer;}
		public String getTeamNaam(){ return teamNaam;}
		public String getTijd(){ return tijd;}
		public int getTeamStartNummer(){ return teamStartNummer;}
		public String toString(){
			return "KlassementInfo: "+getTeamNaam()+", "+getTeamStartNummer()+", "+getTijd();
		}
	}
}