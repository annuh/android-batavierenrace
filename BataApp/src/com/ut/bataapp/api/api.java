/*
 * Versie: v4
 * Date: 08-03-12 11:54
 * By: Johem Elsinga
 * Update: Added findTeam() to get a list of teams containing a certain string
 * Update: Changed getTeams() to get actual teams
 * Update: Added sortTeamByName() to sort the teams by name
 */

package com.ut.bataapp.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.parser.*;

public class api extends Activity {
	
	public File getFile(String path){
		File result = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
		    //"externe" media is gemount dus we kunnen lezen en schrijven naar file.
			//Activity act = new Activity();
			result =  new File(this.getExternalFilesDir(api.getSDmap()).getPath()+path);
			//result = new File(Environment.getExternalStorageDirectory().getPath()+api.getSDmap()+path);
		}
		Log.d("parser","test"+result.getPath());
			return result;
	}
	
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
	public static Team getTeamByID(int id) {
		ArrayList<Looptijd> uitslagen = (ArrayList<Looptijd>) getUitslagByTeam(id).getResponse();
		Team team = new Team(uitslagen.get(0).getTeamStartnummer(),uitslagen.get(0).getTeamStartgroep(),uitslagen.get(0).getTeamNaam());
		for(int i=0;i<uitslagen.size()-1;i++){
			team.addLooptijd(uitslagen.get(i));
		}
		return team;
	}
	
	
	public static Response getKlassement(){
		KlassementHandler kh = new KlassementHandler("ask.xml");
		kh.parse();
		return kh.getParsedData();
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
