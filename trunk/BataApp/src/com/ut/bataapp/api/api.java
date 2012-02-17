package com.ut.bataapp.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Team;

public class api {
	
	static Etappe etappe1 = new Etappe(1, 2000, 'M', "Team 1", "0:30:12");
	static Etappe etappe2 = new Etappe(2, 3000, 'V', "Team 1", "0:30:12");
	static Etappe etappe3 = new Etappe(3, 3000, 'M', "Team 3", "0:30:12");
	static Etappe etappe4 = new Etappe(4, 3000, 'M', "Team 3", "0:30:12");
	static Etappe etappe5 = new Etappe(5, 3000, 'M', "Team 5", "0:30:12");
	static Etappe etappe6 = new Etappe(6, 3000, 'M', "Team 2", "0:30:12");
	static Etappe etappe7 = new Etappe(7, 3000, 'M', "Team 3", "0:30:12");
	
	static Team team1 = new Team(1, "Inter-Actief");
	static Team team2 = new Team(2, "Appeltaart");
	static Team team3 = new Team(3, "Tean123");
	static Team team4 = new Team(4, "Team234");
	
	static HashMap<Integer, Team> uitslag = new HashMap<Integer, Team>();
	
	
	static Klassement klassement = new Klassement("Algemeen Klassement", uitslag);
	static Klassement klassement1 = new Klassement("Universiteits Klassement", uitslag);
	
	/**
	 * Etappe info
	 * Voor elke etappe HashMap met:
	 * - ID
	 * - Omschrijving
	 * - Geslacht
	 * - Afstand
	 * @return 
	 */
	public static ArrayList<Etappe> getEtappes() {
		ArrayList<Etappe> etappes = new ArrayList<Etappe>();
		etappes.add(etappe1);
		etappes.add(etappe2);
		etappes.add(etappe3);
		etappes.add(etappe4);
		etappes.add(etappe5);
		etappes.add(etappe6);
		etappes.add(etappe7);
		return etappes;
	}
	
	public static Etappe getEtappesByID(int id) {
		return etappe1;
	}
	
	/**
	 * Team info
	 * Voor elke team HashMap met:
	 * - ID
	 * - Naam
	 */
	public static ArrayList<Team> getTeams() {
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.add(team1);
		teams.add(team2);
		teams.add(team3);
		teams.add(team4);
		return teams;
	}
	
	
	/**
	 * Team info voor een bepaald team
	 * @param id
	 * @return
	 */
	public static Team getTeamByID(int id) {
		return team1;
	}
	
	public static ArrayList<Klassement> getKlassementen() {
		// Universiteitsklassement
		ArrayList<Klassement> klassementen = new ArrayList<Klassement>();
		klassementen.add(klassement);
		klassementen.add(klassement1);
		return klassementen;
	}
	
	public static Klassement getKlassementByNaam() {
		return klassement1;
	}
	
	
}
