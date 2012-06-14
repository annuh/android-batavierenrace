package com.ut.bataapp.objects;

/**
 * Instanties van deze klasse representeren de etappes in het systeem. 
 */
public class Etappe {
	private int id;
	private String van;
	private String naar;
	private int afstand;
	private char geslacht;
	private String omschrijving;
	private String record_jaar;
	private String record_ploeg;
	private String record_snelheid;
	private String record_tijd;
	private String opentijd = "";
	private String uitersteStarttijd = "";
	private String limiettijd = "";
	private String universteitsLimietstijd = "";
	
	public int[] minHeight = {0, 20, 6, 7, 8, 11, 6, 9, 10, 11, 11, 17, 15, 14, 15, 13, 12, 13, 11, 12, 14, 14, 20, 24, 26, 26};
	public int[] maxHeight = {0, 32, 96, 14, 15, 17, 26, 16, 25, 18, 27, 21, 28, 20, 20, 27, 25, 22, 60, 20, 22, 26, 36, 47, 47, 47};
	
	/**
	 * Constructor
	 * @param id, nummer van de etappe
	 * @param afstand, afstand van de etappe
	 * @param geslacht, geslacht van de etappe
	 * @param record_team, team met snelste tijd
	 * @param record_tijd, snelste tijd
	 * @param filename_hoogteverschil - Filename van afbeelding hoogteverschil
	 */
	public Etappe(int id, String van, String naar, int afstand, char geslacht, String omschrijving,
			String record_jaar, String record_ploeg, String record_snelheid, String record_tijd) {
		this.id = id;
		this.van = van;
		this.naar = naar;
		this.geslacht = geslacht;
		this.afstand = afstand;
		this.omschrijving = omschrijving;
		this.record_jaar = record_jaar;
		this.record_ploeg = record_ploeg;
		this.record_snelheid = record_snelheid;
		this.record_tijd = record_tijd;
	}
	
	/**
	 * Kleine contructor met alleen id parameter
	 * @param id, nummer van de etappe
	 */
	public Etappe(int id){
		this.id = id;
	}
	
	/*Setters*/
	public void setVan(String van){ this.van = van;}
	public void setNaar(String naar){ this.naar = naar;}
	public void setAfstand(int afstand){ this.afstand = afstand;}
	public void setGeslacht(char geslacht){ this.geslacht = geslacht;}
	public void setOmschrijving (String omschrijving) {this.omschrijving = omschrijving;}
	public void setRecordJaar(String recordJaar){ this.record_jaar = recordJaar;}
	public void setRecordTeam(String recordPloeg){ this.record_ploeg = recordPloeg;}
	public void setRecordSnelheid(String recordSnelheid){ this.record_snelheid = recordSnelheid;}
	public void setRecordTijd(String recordTijd){ this.record_tijd = recordTijd;}
	public void setOpenTijd(String opentijd){ this.opentijd = opentijd;}
	public void setUitersteStartTijd(String uitersteStarttijd){ this.uitersteStarttijd = uitersteStarttijd;}
	public void setLimeitTijd(String limiettijd){ this.limiettijd = limiettijd;}
	public void setUniversteitsLimietTijd(String universteitsLimietstijd){ this.universteitsLimietstijd = universteitsLimietstijd;}
	
	/*Getters*/
	public int getId(){ return id;}
	public String getVan(){ return van;}
	public String getNaar() {return naar;}
	public int getAfstand(){ return afstand;}
	public char getGeslacht(){ return geslacht;}
	public String getOmschrijving() { return omschrijving;}
	public String getRecordJaar() {return record_jaar;}
	public String getRecordTeam(){ return record_ploeg;}
	public String getRecordSnelheid() {return record_snelheid;}
	public String getOpenTijd(){ return this.opentijd;}
	public String getUitersteStartTijd(){ return this.uitersteStarttijd;}
	public String getLimietTijd(){ return this.limiettijd;}
	public String getUniversteitsLimietTijd(){ return this.universteitsLimietstijd;}
	public String getRecordTijd() {
		String format = String.format("%%0%dd", 2);  
		long millis = Long.parseLong(record_tijd);
		int seconds = (int) (millis / 1000) % 60 ;
		int minutes = (int) ((millis / (1000*60)) % 60);
		int hours   = (int) ((millis / (1000*60*60)) % 24);
		return String.format(format, hours)+ ":" + String.format(format, minutes) +":"+ String.format(format, seconds);
		}
	
	/**
	 * String representatie van een etappe.
	 */
	public String toString(){
		String statement;
		if(van!=null) statement = "Etappe#: "+getId()+" geslacht: "+getGeslacht()+" van-naar: "+getVan()+"-"+getNaar()+" record jaar: "+getRecordJaar()+'\n';
		else statement = "Etappe#: "+getId()+" afstand: "+getAfstand()+" geslacht: "+getGeslacht() +'\n';
		return statement;
	}
}
