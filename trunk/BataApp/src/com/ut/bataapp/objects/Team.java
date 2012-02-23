package com.ut.bataapp.objects;

import java.util.ArrayList;

public class Team {
	private int startnummer = 0;
	private int startgroep = 0;
	private String naam = "";
	private ArrayList<Uitslag> looptijden = new ArrayList<Uitslag>();
	
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
	 * 
	 * @return ID van dit team
	 */
	public int getStartnummer(){
		return startnummer;
	}
	
	/**
	 * 
	 * @return Startgroep van deze groep
	 */
	public int getStartGroep(){
		return startgroep;
	}
	
	/**
	 * Naam van dit team
	 * @return
	 */
	public String getNaam() {
		return naam;
	}
	
	public void addLooptijd(Uitslag looptijd) {
		looptijden.add(looptijd);
	}
	
	public ArrayList<Uitslag> getLooptijden(){
		return looptijden;
	}
}
