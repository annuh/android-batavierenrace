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
	HashMap<Integer, Team> uitslag;
	
	/**
	 * @param naam - Naam dit klassement
	 * @param uitslag - Uitslag van dit klassement
	 */
	public Klassement(String naam, HashMap<Integer, Team> uitslag) {
		this.naam = naam;
		this.uitslag = uitslag;
	}
	
	/*Setters*/
	public void setNaam(String naam){this.naam = naam;}
	
	/*Getters*/
	public String getNaam() {return naam;}
	public HashMap<Integer, Team> getUitslag(){return uitslag;}
	public Iterable<Team> getTeams() {return uitslag.values();}
	public int getUitslagFromTeam(Team team) {
		for (Map.Entry<Integer, Team> entry : uitslag.entrySet()) {
		    int key = entry.getKey();
		    Team value = entry.getValue();
		    if(value == team)
		    	return key;
		}
		return -1;
	}
	
	/**
	 * Voegt team toe aan dit klassement
	 * @ensures team.getKlassement() == this
	 * @param team
	 * @param plaats
	 */
	public void addTeam(Team team, int plaats){
		uitslag.put(plaats, team);
	}
	

	
	
}