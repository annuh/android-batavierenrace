/*
 * Versie: v2
 * Date: 07-03-12 15:07
 * By: Jochem Elsinga
 * Update: Added Setters
 * Update: Organized Getters
 * Update: Added toString()
 * Update: Added lege constructor
 */

package com.ut.bataapp.objects;

import java.util.ArrayList;

public class Team {
	private int startnummer = 0;
	private int startgroep = 0;
	private String naam = "";
	private int id = 0;
	private ArrayList<Looptijd> looptijden = new ArrayList<Looptijd>();
	
	/**
	 * Kleine constructor
	 * @param teamid - ID van dit team
	 * @param naam - Naam van dit team
	 */
	public Team(int startnummer, int startgroep, String naam) {
		this.startnummer = startnummer;
		this.startgroep = startgroep;
		this.naam = naam;
	}
	
	/**
	 * Wordt gebruikt in FavoTeam
	 * TODO: Samenvoegen met bovenstaande constructor?
	 * @param naam
	 * @param startnummer
	 * @param id
	 */
	public Team(int id,String naam) {
		this.id = id;
		this.startnummer = id;
		this.naam = naam;
		
	}
	
	/*lege constructor*/
	public Team(){}
	
	/*Setters*/
	public void setStartnummer(int start){this.startnummer = start;}
	public void setStartGroep(int groep){this.startgroep = groep;}
	public void setNaam(String naam){this.naam = naam;}
	public void addLooptijd(Looptijd looptijd) {looptijden.add(looptijd);}
	
	/*Getters*/
	public int getStartnummer(){return startnummer;}
	public int getStartGroep(){return startgroep;}
	public String getNaam() {return naam;}
	public ArrayList<Looptijd> getLooptijden(){return looptijden;}
	
	public int getID() {
		// zolang uid nog niet uit XML te halen is:
		return startnummer;
	}
	
	/*String formaat*/
	public String toString(){
		return "Team#: "+getStartnummer()+" group#: "+getStartGroep()+" naam: "+getNaam()+'\n';
	}
}
