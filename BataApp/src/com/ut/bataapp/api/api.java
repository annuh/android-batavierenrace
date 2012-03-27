/*
 * Versie: v4
 * Date: 08-03-12 11:54
 * By: Johem Elsinga
 * Update: Added findTeam() to get a list of teams containing a certain string
 * Update: Changed getTeams() to get actual teams
 * Update: Added sortTeamByName() to sort the teams by name
 */

package com.ut.bataapp.api;

import java.util.ArrayList;
import java.util.HashMap;
import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.parser.Parsing;

public class api {
	
	/**
	 * Haal van alle etappes basis informatie op.
	 * @return 
	 */
	public static Response getEtappes() {
		return new Response(Parsing.parseEtappe(),Response.OK_UPDATE);
	}
	
	/**
	 * Geeft uitgebreide info van 1 etappe
	 * @param id - ID van de etappe
	 * @return
	 */
	public static Etappe getEtappesByID(int id) {
		return Parsing.parseDetailEtappe(id);
	}
	
	public static ArrayList<Looptijd> getUitslagenVanEtappe(int id){
		ArrayList<Looptijd> uitslagen = new ArrayList<Looptijd>();
		Looptijd looptijd1 = new Looptijd(new Team(1,1,"team1"),new Etappe(id),"12:30:00","");
		Looptijd looptijd2 = new Looptijd(new Team(2,1,"team1"),new Etappe(id),"01:43:00","");
		Looptijd looptijd3 = new Looptijd(new Team(3,1,"team1"),new Etappe(id),"06:11:40","");
		uitslagen.add(looptijd1);uitslagen.add(looptijd2);uitslagen.add(looptijd3);
		return uitslagen;
		
	}
	
	//Haal van elk team basis informatie op.
	public static ArrayList<Team> getTeams() {
		ArrayList<Team> teams = Parsing.parseTeam();
		return teams;
	}
	
	//Zoek teams bij naam
	public static ArrayList<Team> findTeam(String deelNaam,ArrayList<Team> teams){
		ArrayList<Team> t = new ArrayList<Team>();
		for(int i=0;i<teams.size()-1;i++){
			if(teams.get(i).getNaam().contains(deelNaam)){
				t.add(teams.get(i));
			}
		}
		return t;
	}
	
	public static ArrayList<Looptijd> getUitslagByTeam(int id){
		return Parsing.parseUitslag(id);
	}
	
	/**
	 * Detailleerde informatie van een team, inclusief looptijden van de lopers (uitslag)
	 * @param id van team
	 * @return
	 */
	public static Team getTeamByID(int id) {
		ArrayList<Looptijd> uitslagen = Parsing.parseUitslag(id);
		Team team = uitslagen.get(0).getTeam();
		for(int i=0;i<uitslagen.size()-1;i++){
			team.addLooptijd(uitslagen.get(i));
		}
		return team;
	}
	
	
	public static ArrayList<Klassement> getKlassement(){
		return Parsing.parseKlassement();
	}
		
	public static Klassement getKlassementByNaam(String naam) {
		HashMap<Integer, Team> uitslagen = new HashMap<Integer, Team>();
		uitslagen.put(1, new Team(1, 1, "Inter-Actief"));
		uitslagen.put(2, new Team(5, 1, "Team 5"));
		uitslagen.put(3, new Team(11, 1, "Team 11"));
		Klassement klassement = new Klassement("Algemeen Klassement", uitslagen);
		return klassement;
	}
	
	
	/**
	* Haal de url op waar de bestanden op de servers staan
	*/
	public static String getURL(){
		return "http://bata-dev.snt.utwente.nl/~jorne/xml_2011/";
	}
	/**
	 * Haal de submap op waar de bestanden op de sdcard moeten staan
	 * @return Stringvorm van de map waar bestanden op SD moeten staan
	 */
	public static String getSDmap(){
		return "/bataxml/";
	}
	public static ArrayList<Bericht> getBerichten(){
		ArrayList<Bericht> berichten = new ArrayList<Bericht>();
		berichten.add(new Bericht("003", 0, "Batacommissie", "Permanente stop", "Omdat bij de toplopers doping is geconstateerd is de batavierenrace 2012 permanent gestopt.", "10/01/12 om 15:34"));
		berichten.add(new Bericht("002", 1, "Batacommissie", "Tijdelijke stop", "Omdat de lopers te hard gelopen hebben is er een tijdelijke stop. De batavierenracecommissie onderzoekt of er doping is gebruikt.", "10/01/12 om 15:13"));
		berichten.add(new Bericht("001", 2, "Batacommissie", "Lopers veel succes", "De batavierenracecommissie wenst elke loper veel succes in de race.", "10/01/12 om 15:09"));
		
		return berichten;
	}

	//Omdat dit ergens wordt aangeroepen :S
	public static HashMap<Integer, Integer> getLooptijdenByEtappe(Etappe etappe) {
		return null;
	}	

}
