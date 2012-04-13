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

public class Klassement {

	String naam;
	
	ArrayList<KlassementItem> uitslag;

	/**
	 * @param naam - Naam dit klassement
	 * @param uitslag - Uitslag van dit klassement
	 */
	public Klassement(String naam, ArrayList<KlassementItem> uitslag) {
		this.naam = naam;
		this.uitslag = uitslag;
	}

	//LEGE CONSTRUCTOR!
	public Klassement(){
		this.uitslag = new ArrayList<KlassementItem>();
	}

	/*Setters*/
	public void setNaam(String naam){this.naam = naam;}

	/*Getters*/
	public String getNaam() {return naam;}
	public ArrayList<KlassementItem> getUitslag(){return uitslag;}

	public String toString(){
		return "klassement: "+getNaam();
	}

	/**
	 * Voegt team toe aan dit klassement
	 * @ensures team.getKlassement() == this
	 * @param team
	 * @param plaats
	 */
	public void addKlassementInfo(KlassementItem info){
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