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
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.parser.BerichtenHandler;
import com.ut.bataapp.parser.DetailedEtappeHandler;
import com.ut.bataapp.parser.EtappeHandler;
import com.ut.bataapp.parser.EtappeUitslagHandler;
import com.ut.bataapp.parser.KlassementHandler;
import com.ut.bataapp.parser.PloegHandler;
import com.ut.bataapp.parser.TeamHandler;

public class api {
		
	//~~ PARSER AANROEPEN ~~\\
	
	/**
	 * Haal van alle etappes basis informatie op.
	 * @return 
	 */
	public static Response<ArrayList<Etappe>> getEtappes() {
		EtappeHandler eh = new EtappeHandler("etappes.xml");
		eh.parse();
		return eh.getParsedData();
	}
	
	/**
	 * Geeft uitgebreide info van 1 etappe
	 * @param id - ID van de etappe
	 * @return
	 */
	public static Response<Etappe> getEtappesByID(int id) {
		DetailedEtappeHandler deh = new DetailedEtappeHandler("etappes.xml",id);
		deh.parse();
		//EtappeDataHandler eDH = new EtappeDataHandler(context);
		return deh.getParsedData();
	}
	
	/**
	 * Geeft lijst van uitslagen van een etappe
	 * @param id - ID van etapp
	 * @return
	*/
	public static Response<ArrayList<ArrayList<Looptijd>>> getUitslagenVanEtappe(int id){
		ArrayList<ArrayList<Looptijd>> uitslagen = new ArrayList<ArrayList<Looptijd>>();
		
		ArrayList<Looptijd> uni = new ArrayList<Looptijd>();
		Looptijd l1 = new Looptijd();
		l1.setSnelheid("14.3");
		l1.setTeamNaam("Inter-Actief1");
		l1.setTeamStartnummer(34);
		l1.setTijd("12:45");
		l1.setEtappeStand(30);
		uni.add(l1);
		Looptijd l4 = new Looptijd();
		l4.setSnelheid("14.3");
		l4.setTeamNaam("Inter-Actief4");
		l4.setTeamStartnummer(7);
		l4.setTijd("12:45");
		l4.setEtappeStand(3);
		uni.add(l4);
		
		ArrayList<Looptijd> alg = new ArrayList<Looptijd>();
		Looptijd l2 = new Looptijd();
		l2.setSnelheid("14.3");
		l2.setTeamNaam("Inter-Actief2");
		l2.setTeamStartnummer(34);
		l2.setTijd("12:45");
		l2.setEtappeStand(23);
		alg.add(l2);
		Looptijd l3 = new Looptijd();
		l3.setSnelheid("14.3");
		l3.setTeamNaam("Inter-Actief3");
		l3.setTeamStartnummer(34);
		l3.setTijd("12:45");
		l3.setEtappeStand(11);
		alg.add(l3);
		
		uitslagen.add(uni);uitslagen.add(alg);
		return new Response<ArrayList<ArrayList<Looptijd>>>(uitslagen, Response.OK_UPDATE);
		
		//EtappeUitslagHandler euh = new EtappeUitslagHandler("etappeuitslag/"+id+".xml",id);
		//euh.parse();
		//return euh.getParsedData();
	}
	
	/**
	 * Nieuw, laatste binnengekomen teams per etappe. Gesorteerd op binnenkomsttijd!
	 * @param id
	 * @return
	 */
	public static Response<ArrayList<Looptijd>> getLaatstBinnengekomenVanEtappe(int id) {
		
		ArrayList<Looptijd> laatste = new ArrayList<Looptijd>();
		Looptijd l1 = new Looptijd();
		l1.setSnelheid("14.3");
		l1.setTeamNaam("Inter-Actief");
		l1.setTeamStartnummer(34);
		l1.setTijd("12:45");
		laatste.add(l1);
		return new Response<ArrayList<Looptijd>>(laatste, Response.OK_NO_UPDATE);
	}
	
	/**
	 * Geeft eem lijst van elk team met basis informatie op.
	 * @return
	 */
	public static Response<ArrayList<Team>> getTeams() {
		TeamHandler th = new TeamHandler("ploegen.xml");
		th.parse();
		return th.getParsedData();
	}

	/**
	 * Detailleerde informatie van een team, inclusief looptijden van de lopers (uitslag)
	 * @param id van team
	 * @return
	 */
	public static Response<Team> getTeamByID(int id) {
		PloegHandler phHandler = new PloegHandler("ploeguitslag/"+id+".xml");
		phHandler.parse();
		return phHandler.getParsedData();
	}
	
	/**
	 * Geeft een lijst met de namen van klassementen terug
	 * HARDCODED
	 * @return
	 */
	public static Response<ArrayList<String>> getKlassementen(){
		String kl1 = "Algemeen";
		String kl2 = "Universiteitscompetitie";
		ArrayList<String> klassementen = new ArrayList<String>();
		klassementen.add(kl1); klassementen.add(kl2);
		return new Response<ArrayList<String>>(klassementen, Response.OK_UPDATE);	
	}
	
	/**
	 * Geeft een lijst van KlassementItems
	 * @param naam - Naam van het klassement
	 * @return
	 */
	public static Response<Klassement> getKlassementByNaam(String naam) {
		KlassementHandler kh = new KlassementHandler("klassement.xml",naam);
		kh.parse();
		return kh.getParsedData();
	}
	
	/**
	 * Geeft een lijst met alle berichten terug
	 * @return
	 */
	public static Response<ArrayList<Bericht>> getBerichten(){
		BerichtenHandler bh = new BerichtenHandler("nieuws.xml");
		bh.parse();
		return bh.getParsedData();
	}

	//~~ SORTEER METHODES ~~\\
	
	/**
	 * Zoekt een team uit de lijst van teams
	 * @param deelNaam - String on naar te zoeken
	 * @param teams - Lijst van teams om in te zoeken
	 * @return
	 */
	public static ArrayList<Team> findTeam(String deelNaam,ArrayList<Team> teams){
		ArrayList<Team> t = new ArrayList<Team>();
		for(int i=0;i<teams.size()-1;i++){
			if(teams.get(i).getNaam().contains(deelNaam)){
				t.add(teams.get(i));
			}
		}
		return t;
	}
	
	//~~ OTHER ~~\\
	
	/**
	* Haal de url op waar de bestanden op de servers staan
	*/
	public static String getURL(){
		return "http://api.batavierenrace.nl/xml/4040/";
	}
	
	/**
	 * Haal de submap op waar de bestanden op de sdcard moeten staan
	 * @return Stringvorm van de map waar bestanden op SD moeten staan
	 */
	public static String getSDmap(){
		return "/bataxml/";
	}
	
	//Omdat dit ergens wordt aangeroepen :S
	public static HashMap<Integer, Integer> getLooptijdenByEtappe(Etappe etappe) {
		return null;
	}	

}
