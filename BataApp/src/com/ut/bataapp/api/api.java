package com.ut.bataapp.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

import com.ut.bataapp.R;
import com.ut.bataapp.objects.*;
import com.ut.bataapp.parser.*;

/**
 * Deze klasse verzorgd de koppeling tussen de xml files en parsers en de gui.
 * Vanuit de GUI kunnen methoden uit deze klasse aangeroepen worden om data uit de 
 * xml files te parsen en terug te geven.
 */
public class api {
		
	//~~ PARSER AANROEPEN ~~\\
	
	/**
	 * Haal van alle etappes basis informatie op.
	 * @return Response object met een lijst met alle etappes
	 */
	public static Response<ArrayList<Etappe>> getEtappes() {
		EtappeHandler eh = new EtappeHandler("etappes.xml");
		eh.parse(false);
		return eh.getParsedData();
	}
	
	/**
	 * Geeft uitgebreide info van 1 etappe
	 * @param id - ID van de etappe
	 * @return Response object met de uitgebreide informatie over de etappe met de gegeven id
	 */
	public static Response<Etappe> getEtappesByID(int id,Context context) {
		DetailedEtappeHandler deh = new DetailedEtappeHandler("etappes.xml",id);
		deh.parse(false);
		Etappe etappe = deh.getParsedData().getResponse();
		int status = deh.getParsedData().getStatus();
		if(status != Response.NOK_NO_DATA && etappe!=null){
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				EtappeDataHandler eDH = new EtappeDataHandler(context,etappe);
				xr.setContentHandler(eDH);
				xr.parse(new InputSource(context.getResources().openRawResource(R.raw.ei)));
				etappe = eDH.getParsedData();
			} catch (NotFoundException e) {
				status = Response.NOK_NO_DATA;
			} catch (IOException e) {
				status = Response.NOK_NO_DATA;
			} catch (SAXException e) {
				status = Response.NOK_NO_DATA;
			} catch (ParserConfigurationException e) {
				status = Response.NOK_NO_DATA;
			}
		}else{
			status = Response.NOK_NO_DATA;
		}
		return new Response<Etappe>(etappe,status);
	}

	/**
	 * Geeft lijst van uitslagen van een etappe
	 * @param id - ID van etapp
	 * @return Response object met een lijst van uitslagen van de etappe met de gegeven id
	*/
	public static Response<ArrayList<ArrayList<Looptijd>>> getUitslagenVanEtappe(int id){
		EtappeUitslagHandler euh = new EtappeUitslagHandler("etappeuitslag/"+id+".xml",id);
		euh.parse(false);
		return euh.getParsedData();
	}
	
	/**
	 * Geeft eem lijst van elk team met basis informatie op.
	 * @return Response object met de basisinformatie van alle teams
	 */
	public static Response<ArrayList<Team>> getTeams() {
		TeamHandler th = new TeamHandler("ploegoverzicht.xml");
		th.parse(false);
		return th.getParsedData();
	}

	/**
	 * Gedetailleerde informatie van een team, inclusief looptijden van de lopers (uitslag)
	 * @param id van team
	 * @return	getTeamByID(id, false)
	 */
	public static Response<Team> getTeamByID(int id) {
		return getTeamByID(id, false);
	}
	
	/**
	 * Gedetailleerde informatie van een team, inclusief looptijden van de lopers (uitslag)
	 * @param id van team
	 * @return Response object met de informatie van het team met de opgegeven id
	 */
	public static Response<Team> getTeamByID(int id, boolean surpressDownload) {
		PloegHandler phHandler = new PloegHandler("ploeguitslag/"+id+".xml");
		phHandler.parse(surpressDownload);
		return phHandler.getParsedData();
	}
	
	/**
	 * Geeft een lijst met de namen van klassementen terug
	 * TODO: Haal klassementen uit xml bestanden ipv hardcoded
	 * @return Response object met lijst van klassementen
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
	 * @return	Response object met de geparste klassementen
	 */
	public static Response<Klassement> getKlassementByNaam(String naam) {
		KlassementHandler kh = new KlassementHandler("klassement.xml",naam);
		kh.parse(false);
		return kh.getParsedData();
	}
	
	/**
	 * Geeft een lijst met alle berichten terug
	 * @return Response object met de geparste berichten
	 */
	public static Response<ArrayList<Bericht>> getBerichten(){
		BerichtenHandler bh = new BerichtenHandler("nieuws.xml");
		bh.parse(false);
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
		return "http://api.batavierenrace.nl/xml/2012/";
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