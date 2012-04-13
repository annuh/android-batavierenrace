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

import android.util.Log;

import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Klassement.KlassementInfo;
import com.ut.bataapp.objects.KlassementItem;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.parser.*;

public class api {
	
	/**
	 * Haal van alle etappes basis informatie op.
	 * @return 
	 */
	public static Response getEtappes() {
		EtappeHandler eh = new EtappeHandler("etappes.xml");
		eh.parse();
		return eh.getParsedData();
	}
	
	/**
	 * Geeft uitgebreide info van 1 etappe
	 * @param id - ID van de etappe
	 * @return
	 */
	public static Response getEtappesByID(int id) {
		DetailedEtappeHandler deh = new DetailedEtappeHandler("etappes.xml",id);
		deh.parse();
		return deh.getParsedData();
	}
	
	public static Response getUitslagenVanEtappe(int id){
		EtappeUitslagHandler euh = new EtappeUitslagHandler("etappeuitslag/"+id+".xml",id);
		euh.parse();
		return euh.getParsedData();
	}
	
	/**
	 * Nieuw, laatste binnengekomen teams per etappe. Gesorteerd op binnenkomsttijd!
	 * @param id
	 * @return
	 */
	public static Response getLaatstBinnengekomenVanEtappe(int id) {
		ArrayList<Looptijd> laatste = new ArrayList<Looptijd>();
		Looptijd l1 = new Looptijd();
		l1.setSnelheid("14.3");
		l1.setTeamNaam("Inter-Actief");
		l1.setTeamStartnummer(34);
		l1.setTijd("12:45");
		laatste.add(l1);
		return new Response(laatste, Response.OK_NO_UPDATE);
	}
	
	//Haal van elk team basis informatie op.
	public static Response getTeams() {
		TeamHandler th = new TeamHandler("ploegen.xml");
		th.parse();
		return th.getParsedData();
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
	
	public static Response getUitslagByTeam(int id){
		PloegHandler ph = new PloegHandler("ploeguitslag/"+id+".xml");
		ph.parse();
		return ph.getParsedData();
	}
	
 /**
	 * Detailleerde informatie van een team, inclusief looptijden van de lopers (uitslag)
	 * @param id van team
	 * @return
	 */
	public static Response getTeamByID(int id) {
		Response uitslagbyteam = getUitslagByTeam(id);
		Response result = null;
		if(uitslagbyteam.getStatus() == Response.NOK_NO_DATA){
			result = uitslagbyteam;
		}else{
			ArrayList<Looptijd> uitslagen = (ArrayList<Looptijd>) uitslagbyteam.getResponse();
			Team team = new Team(uitslagen.get(0).getTeamStartnummer(),uitslagen.get(0).getTeamStartgroep(),uitslagen.get(0).getTeamNaam());
			for(int i=0;i<uitslagen.size();i++){
				team.addLooptijd(uitslagen.get(i));
			}
			result = new Response(team,uitslagbyteam.getStatus());
		}	
		return result;
	}
	
	/**
	 * NIEUWE METHODE: GEEFT KLASSEMENTEN TERUG	
	 * @return
	 */
	public static Response getKlassementen(){
		String kl1 = "Algemeen";
		String kl2 = "Universiteitscompetitie";
		ArrayList<String> klassementen = new ArrayList<String>();
		klassementen.add(kl1); klassementen.add(kl2);
		return new Response(klassementen, Response.OK_UPDATE);	
	}
	
	/**
	 * NIEUWE METHODE:
	 * @param naam
	 * @return
	 */
	public static Response getKlassementByNaam(String naam) {
		KlassementHandler kh = new KlassementHandler("klassement.xml",naam);
		kh.parse();
		return kh.getParsedData();
	}
	
	
	/**
	* Haal de url op waar de bestanden op de servers staan
	*/
	public static String getURL(){
		return "http://api.batavierenrace.nl/xml/2011/";
	}
	
	/**
	 * Haal de submap op waar de bestanden op de sdcard moeten staan
	 * @return Stringvorm van de map waar bestanden op SD moeten staan
	 */
	public static String getSDmap(){
		return "/bataxml/";
	}
	
	/**
	 * NIEUWE METHODE
	 * @return
	 */
	public static ArrayList<Bericht> getBerichten(){
		ArrayList<Bericht> berichten = new ArrayList<Bericht>();
		berichten.add(new Bericht("003", 0, "Permanente stop", "Omdat bij de toplopers doping is geconstateerd is de batavierenrace 2012 permanent gestopt.", "10/01/12 om 15:34"));
		berichten.add(new Bericht("002", 1, "Tijdelijke stop", "Omdat de lopers te hard gelopen hebben is er een tijdelijke stop. De batavierenracecommissie onderzoekt of er doping is gebruikt.", "10/01/12 om 15:13"));
		berichten.add(new Bericht("001", 2, "Lopers veel succes", "De batavierenracecommissie wenst elke loper veel succes in de race.", "10/01/12 om 15:09"));
		
		return berichten;
	}

	//Omdat dit ergens wordt aangeroepen :S
	public static HashMap<Integer, Integer> getLooptijdenByEtappe(Etappe etappe) {
		return null;
	}	

}
