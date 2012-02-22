package com.ut.bataapp.objects;

import java.io.File;

public class Etappe {
	private int id;
	private int afstand;
	private char geslacht;
	private String record_team;
	private String record_tijd;
	private String filename_hoogteverschil;
	
	/**
	 * 
	 * @param id, nummer van de etappe
	 * @param afstand, afstand van de etappe
	 * @param geslacht, geslacht van de etappe
	 * @param record_team, team met snelste tijd
	 * @param record_tijd, snelste tijd
	 * @param filename_hoogteverschil - Filename van afbeelding hoogteverschil
	 */
	public Etappe(int id, int afstand, char geslacht, String record_team, String record_tijd, String filename_hoogteverschil) {
		this.id = id;
		this.afstand = afstand;
		this.geslacht = geslacht;
		this.record_team = record_team;
		this.record_tijd = record_tijd;
	}

	/**
	 * Kleine constructor
	 * @param id - ID van de etappe
	 * @param geslacht - Geslacht van de etappe
	 */
	public Etappe(int id, char geslacht) {
		this.id = id;
		this.geslacht = geslacht;
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
	
	public String getFilenameHoogteverschil(){
		return filename_hoogteverschil;
	}
}
