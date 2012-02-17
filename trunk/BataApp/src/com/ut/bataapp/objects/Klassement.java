package com.ut.bataapp.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Klassement {
	
	String naam;
	HashMap<Integer, Team> uitslag;
	
	public Klassement(String naam, HashMap<Integer, Team> uitslag) {
		this.naam = naam;
		this.uitslag = uitslag;
	}
	
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
	

	
	
}