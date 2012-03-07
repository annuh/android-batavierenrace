/*
 * Versie: v3
 * Date: 07-03-12 17:20
 * By: Jochem Elsinga
 * Update: Changed getTeams() to get actual teams
 * Update: Added sortTeamByName() to sort the teams by name
 */

package com.ut.bataapp.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Uitslag;
import com.ut.bataapp.parser.Parsing;

public class api {
	
	/**
	 * Haal van alle etappes basis informatie op.
	 * @return 
	 */
	public static ArrayList<Etappe> getEtappes() {
		ArrayList<Etappe> etappes =new Parsing().parseEtappe();
		return etappes;
	}
	
	/**
	 * Geeft uitgebreide info van 1 etappe
	 * @param id - ID van de etappe
	 * @return
	 */
	public static Etappe getEtappesByID(int id) {
		Etappe etappe = new Etappe(1, 2500, 'M', "Team-naam", "0:23:23", null, "LOOPKML.kml","AUTOKML.kml","OVERSLAG.kml");
		return etappe;
	}
	
	/**
	 * COMMENT: Deze methode kunnen we eventueel samenvoegen met getEtappeById(), als we deze info willen laten zien bij de etappe info.
	 * @param etappe
	 * @return
	 */
	public static HashMap<Integer, Integer> getLooptijdenByEtappe(Etappe etappe) {
		HashMap<Integer, Integer> tijden = new HashMap<Integer, Integer>();
		// In minuut 0 finishen 0 lopers
		tijden.put(0, 0);
		tijden.put(1, 0);
		// In minuut 2 (2:00 - 2:59) finishen 3 lopers
		tijden.put(2, 3);
		// In minutt 3 (3:00 - 3:59) finishen 5 lopers etc..
		tijden.put(3, 5);
		tijden.put(4, 100);
		tijden.put(5, 12);
		tijden.put(6, 2);
		tijden.put(7, 0);
		return tijden;
	}
	
	/**
	 * Haal van elk team basis informatie op.
	 */
	public static ArrayList<Team> getTeams() {
		ArrayList<Team> teams = new Parsing().parseTeam();
		return teams;
	}
	
	public static ArrayList<Team> sortTeamByName(ArrayList<Team> teams){
		ArrayList<Team> t = teams;
		Collections.sort(t,new TeamNaamComparator());
		return t;
	}
	
	/**
	 * Detailleerde informatie van een team, inclusief looptijden van de lopers (uitslag)
	 * @param id van team
	 * @return
	 */
	public static Team getTeamByID(int id) {
		Team team = new Team(1,1, "Blaat");
		
		// Team 1 heeft Etappe 1 in 0:31:31 gelopen
		Uitslag uitslag1 = new Uitslag(team, new Etappe(1, 'M'), "0:31:31", null);
		team.addLooptijd(uitslag1);
		
		// Team 1 heeft Etappe 2 in 0:21:31 gelopen
		Uitslag uitslag2 = new Uitslag(team, new Etappe(2, 'M'), "0:21:31", null);
		team.addLooptijd(uitslag2);
		
		return team;
	}
	
	
	public static ArrayList<String> getKlassementsnamen(){
		String klassement1="Algemeen Klassement";
		String klassement2="Universiteits klassement";
		ArrayList<String> namen = new ArrayList<String>();
		namen.add(klassement1);
		namen.add(klassement2);
		return namen;
	}
		
	public static Klassement getKlassementByNaam(String naam) {
		HashMap<Integer, Team> uitslagen = new HashMap<Integer, Team>();
		uitslagen.put(1, new Team(1, 1, "Inter-Actief"));
		uitslagen.put(2, new Team(5, 1, "Team 5"));
		uitslagen.put(3, new Team(11, 1, "Team 11"));
		Klassement klassement = new Klassement(0, "Algemeen Klassement", uitslagen);
		return klassement;
	}
	
	/**
	 * Haal jaarkleur op.
	 * @return
	 */
	public String getJaarkleur() {
		return "#FFE13B";
	}
	
	/**
	 * Haal data van de volgende Bata op
	 * @return dag/maand/jaar van volgende bata
	 */
	public String getDatum(){
		return "28/04/2012";
	}
	
		
}
