/*
 * Versie: v2
 * Date: 08-03-12 13:05
 * By: Jochem Elsinga
 * Update: removed ID becuase a klassement doesnt have an id.
 * Update: Format of code
 * Update: Added id and naam setter
 */

package com.ut.bataapp.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Klassement {
	
	String naam;
	ArrayList<KlassementInfo> uitslag;
	
	/**
	 * Lijst met alle teamnamen, gesorteerd op positie in klassement.
	 */
	private ArrayList<String> teamnaam = new ArrayList<String>();
	private ArrayList<String> teamuitslag = new ArrayList<String>();
	private ArrayList<Integer> teamid = new ArrayList<Integer>();
	private ArrayList<String> teamtijd = new ArrayList<String>();
	
	/**
	 * @param naam - Naam dit klassement
	 * @param uitslag - Uitslag van dit klassement
	 */
	public Klassement(String naam, ArrayList<KlassementInfo> uitslag) {
		this.naam = naam;
		this.uitslag = uitslag;
	}
	
	// NIEUWE METHODE (?)
	public void addUitslag(String naam, String uitslag, int id, String tijd) {
		teamnaam.add(naam);
		teamuitslag.add(uitslag);
		teamid.add(id);
		teamtijd.add(tijd);
		
	}
	
	//LEGE CONSTRUCTOR!
	public Klassement(){
		this.uitslag = new ArrayList<KlassementInfo>();
	}
		
	/*Setters*/
	public void setNaam(String naam){this.naam = naam;}
	
	/*Getters*/
	public String getNaam() {return naam;}
	public ArrayList<KlassementInfo> getUitslag(){return uitslag;}
	
	/*public Iterable<KlassementInfo> getKlassementInfo() {return uitslag.values();}
	public int getUitslagFromTeam(KlassementInfo info) {
		for (Map.Entry<Integer, KlassementInfo> entry : uitslag.entrySet()) {
		    int key = entry.getKey();
		    KlassementInfo value = entry.getValue();
		    if(value == info)
		    	return key;
		}
		return -1;
	}*/
	
	public String toString(){
		return "klassement: "+getNaam();
	}
	
	/**
	 * Voegt team toe aan dit klassement
	 * @ensures team.getKlassement() == this
	 * @param team
	 * @param plaats
	 */
	public void addKlassementInfo(KlassementInfo info){
		uitslag.add(info);
	}
	
	public class KlassementInfo{
		
		int plaats;
		String teamNaam;
		String tijd;
		int teamStartNummer;
		
		public KlassementInfo(){}
		
		public void setPlaats(int plaats) {this.plaats = plaats;}
		public void setTeamNaam(String teamNaam){ this.teamNaam = teamNaam;}
		public void setTijd(String tijd){ this.tijd = tijd;}
		public void setTeamStartNummer(int teamStartNummer){ this.teamStartNummer = teamStartNummer;}
		
		public int getPlaats() { return plaats; }
		public String getTeamNaam(){ return teamNaam;}
		public String getTijd(){ return tijd;}
		public int getTeamStartNummer(){ return teamStartNummer;}
		public String toString(){
			return "KlassementInfo: "+getTeamNaam()+", "+getTeamStartNummer()+", "+getTijd();
		}
	}
}