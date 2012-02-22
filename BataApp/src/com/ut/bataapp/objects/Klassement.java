package com.ut.bataapp.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Klassement {
	
	int id;
	String naam;
	HashMap<Integer, Team> uitslag;
	
	/**
	 * @param id - ID van dit klassement
	 * @param naam - Naam dit klassement
	 * @param uitslag - Uitslag van dit klassement
	 */
	public Klassement(int id, String naam, HashMap<Integer, Team> uitslag) {
		this.id = id;
		this.naam = naam;
		this.uitslag = uitslag;
	}
	
	/**
	 * @return Naam van dit klassement
	 */
	public String getNaam() {
		return naam;
	}
	
	public HashMap<Integer, Team> getUitslag(){
		return uitslag;
	}
	
	public Iterable<Team> getTeams() {
		return uitslag.values();
	}
	
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