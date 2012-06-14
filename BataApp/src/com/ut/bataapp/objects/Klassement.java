package com.ut.bataapp.objects;

import java.util.ArrayList;

/**
 * Instanties van deze klasse representeren de klassementen in het systeem.
 */
public class Klassement {

	String naam;
	int totEtappe = 0;
	ArrayList<KlassementItem> uitslag;

	/**
	 * Constructor
	 * @param naam - Naam van dit klassement
	 * @param uitslag - Uitslagen van dit klassement
	 */
	public Klassement(String naam, ArrayList<KlassementItem> uitslag) {
		this.naam = naam;
		this.uitslag = uitslag;
	}

	/**
	 * Constructor zonder parameters
	 * Creert een leeg klassement object.
	 */
	public Klassement(){
		this.uitslag = new ArrayList<KlassementItem>();
	}

	/*Setters*/
	public void setNaam(String naam){this.naam = naam;}
	public void setTotEtappe(int tot){this.totEtappe=tot;}
	/*Getters*/
	public String getNaam() {return naam;}
	public ArrayList<KlassementItem> getUitslag(){return uitslag;}
	public int getTotEtappe(){return totEtappe;}
	
	@Override
	public String toString(){
		return "klassement: "+getNaam();
	}

	/**
	 * Voegt een klassementitem aan dit klassement toe
	 */
	public void addKlassementInfo(KlassementItem info){
		uitslag.add(info);
	}
}