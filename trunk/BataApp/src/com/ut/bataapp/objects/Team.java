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

import java.util.ArrayList;
/**
 * Instanties van deze klasse representeren een bepaald team in het systeem.
 */
public class Team {
	private int startnummer = 0;
	private int startgroep = 0;
	private String naam = "";
	private String klassement ="";
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
	 * Constructor. Wordt gebruikt in FavoTeam
	 * TODO: Samenvoegen met bovenstaande constructor?
	 * @param naam
	 * @param startnummer
	 * @param id
	 */
	public Team(int id,String naam) {
		this.startnummer = id;
		this.naam = naam;
	}
	
	/**
	 * Default Constructor
	 */
	public Team(){}
	
	/*Setters*/
	public void setStartnummer(int start){this.startnummer = start;}
	public void setStartGroep(int groep){this.startgroep = groep;}
	public void setNaam(String naam){this.naam = naam;}
	public void addLooptijd(Looptijd looptijd) {looptijden.add(looptijd);}
	public void setKlassementTotEtappe(int totEtappe) { this.klassementTotEtappe = totEtappe; }
	public void appendNaam(String naam){this.naam = this.naam.concat(naam);}
	/*Getters*/
	public int getStartnummer(){return startnummer;}
	public int getStartGroep(){return startgroep;}
	public String getNaam() {return naam;}
	public ArrayList<Looptijd> getLooptijden(){return looptijden;}
	public int getKlassementTotEtappe() {return klassementTotEtappe; }
	/**
	 * Geeft het unieke ID van een Team terug. 
	 * Geeft nu nog gewoon startnummer terug
	 * TODO: UID uit xml halen
	 * @return this.startnummer
	 */
	public int getID() {
		return startnummer;
	}
	/**
	 * Geeft de cumelatieve stand van dit team in het klassement
	 * @return String met de cummelatieve stand(getal)
	 */
	public String getCumKlassement() {
		String cumKlassement = String.valueOf(startnummer);
		if (looptijden != null) {
			boolean found = false;
			int i = 0;
			while (!found && i < looptijden.size()) {
				Looptijd looptijd = looptijden.get(i);
				if (looptijd.getEtappe() == klassementTotEtappe) {
					found = true;
					cumKlassement = (looptijd.getCumulatieveStand() + "");
				}
				i++;
			}
				
		}
		return cumKlassement;
	}
	/**
	 * Geeft integer waarde van de cumelatieve stand in het klassement
	 * @return Integer.parseInt(getCumKlassement());
	 */
	public int getCumKlassementInt() {
		return Integer.parseInt(getCumKlassement());
	}
	/**
	 * Geeft de totale looptijd van dit team terug
	 * @return String met de totale looptijd van dit team
	 */
	public String getTotaalTijd() {
		String totaaltijd = "00:00:00";
		if (looptijden != null && looptijden.size() > 0 )
			totaaltijd = String.valueOf(looptijden.get(looptijden.size()-1).getCumtotaaltijd());
		return totaaltijd;
	}
	
	@Override
	public String toString(){
		return "Team#: "+getStartnummer()+" group#: "+getStartGroep()+" naam: "+getNaam()+'\n';
	}

	public void setKlassement(String klassement) {
		this.klassement = klassement;
	}

	public String getKlassement() {
		if(klassement.equals("A")){
			return "Algemeen";
		}else if(klassement.equals("U")){
			return "Universiteitscompetitie";
		}else{
			return klassement;
		}
	}
}
