package com.ut.bataapp.objects;

public class Etappe {
	private int id;
	private int afstand;
	private char geslacht;
	private String record_team;
	private String record_tijd;
	
	/**
	 * 
	 * @param id, nummer van de etappe
	 * @param afstand, afstand van de etappe
	 * @param geslacht, geslacht van de etappe
	 * @param record_team, team met snelste tijd
	 * @param record_tijd, snelste tijd
	 */
	public Etappe(int id, int afstand, char geslacht, String record_team, String record_tijd) {
		this.id = id;
		this.afstand = afstand;
		this.geslacht = geslacht;
		this.record_team = record_team;
		this.record_tijd = record_tijd;
	}
	
	public int getId(){
		return id;
	}

	public int getAfstand(){
		return afstand;
	}
	
	public char getGeslacht(){
		return geslacht;
	}
	
	public String getRecordTeam(){
		return record_team;
	}
	
	public String getRecordTijd() {
		return record_tijd;
	}
}
