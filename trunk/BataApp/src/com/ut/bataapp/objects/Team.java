/*
 * Versie: v2
 * Date: 07-03-12 15:07
 * By: Jochem Elsinga
 * Update: Added Setters
 * Update: Organized Getters
 * Update: Added toString()
 * Update: Added lege constructor
 */

package com.ut.bataapp.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class Team {
	private int startnummer = 0;
	private int startgroep = 0;
	private String naam = "";
	private String klassement ="";
	private int id = 0;
	private int klassementsnotering = -1;
	private int klassementTotEtappe = 0;
	private ArrayList<Looptijd> looptijden = new ArrayList<Looptijd>();
	
	/**
	 * Kleine constructor
	 * @param teamid - ID van dit team
	 * @param naam - Naam van dit team
	 */
	public Team(int startnummer, int startgroep, String naam) {
		this.startnummer = startnummer;
		this.startgroep = startgroep;
		this.naam = naam;
	}
	
	/**
	 * Wordt gebruikt in FavoTeam
	 * TODO: Samenvoegen met bovenstaande constructor?
	 * @param naam
	 * @param startnummer
	 * @param id
	 */
	public Team(int id,String naam) {
		this.id = id;
		this.startnummer = id;
		this.naam = naam;
		
	}
	
	/*lege constructor*/
	public Team(){}
	
	/*Setters*/
	public void setStartnummer(int start){this.startnummer = start;}
	public void setStartGroep(int groep){this.startgroep = groep;}
	public void setNaam(String naam){this.naam = naam;}
	public void addLooptijd(Looptijd looptijd) {looptijden.add(looptijd);}
	public void setKlassementsnotering(int klassementsnotering) { this.klassementsnotering = klassementsnotering; }
	public void setKlassementTotEtappe(int totEtappe) { this.klassementTotEtappe = totEtappe; }
	public void appendNaam(String naam){this.naam = this.naam.concat(naam);}
	/*Getters*/
	public int getStartnummer(){return startnummer;}
	public int getStartGroep(){return startgroep;}
	public String getNaam() {return naam;}
	public ArrayList<Looptijd> getLooptijden(){return looptijden;}
	public int getKlassementsnotering() {return klassementsnotering; }
	public int getKlassementTotEtappe() {return klassementTotEtappe; }
	
	public int getID() {
		// zolang uid nog niet uit XML te halen is:
		return startnummer;
	}
	
	public String getCumKlassement() {
		String cumKlassement = "";
		if(looptijden != null && looptijden.size() > 0 ) {
			cumKlassement = String.valueOf(looptijden.get(looptijden.size()-1).getCumulatieveStand());
		}
		return cumKlassement;
	}
	
	public String getTotaalTijd() {
		String totaaltijd = "";
		if(looptijden != null && looptijden.size() > 0 ) {
			totaaltijd = String.valueOf(looptijden.get(looptijden.size()-1).getCumtotaaltijd());
		}
		return totaaltijd;
	}
	
	/*String formaat*/
	public String toString(){
		return "Team#: "+getStartnummer()+" group#: "+getStartGroep()+" naam: "+getNaam()+'\n';
	}

	public void setKlassement(String klassement) {
		this.klassement = klassement;
	}

	public String getKlassement() {
		if(klassement == null)
			Log.d("Klassement","NULL");
		if(klassement.equals("A")){
			return "Algemeen";
		}else if(klassement.equals("U")){
			return "Universiteitscompetitie";
		}else{
			return klassement;
		}
	}
}
