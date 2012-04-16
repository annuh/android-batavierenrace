package com.ut.bataapp.parser;

import java.util.ArrayList;

public class EtappeRoute {
	
	private int nummer;
	private String TekstVoorTabel;
	private ArrayList<String[]> tabel;
	private String TekstNaTabel;
	
	public EtappeRoute(int nummer, String TekstVoorTabel, ArrayList<String[]> tabel, String TekstNaTabel){
		
		this.nummer = nummer;
		this.TekstVoorTabel = TekstVoorTabel;
		this.tabel = tabel;
		this.TekstNaTabel = TekstNaTabel;		
	
	}
	
	public int getNummer(){
		return this.nummer;
	}
	
	public String getVoorTabelTekst(){
		return this.TekstVoorTabel;	
	}
	
	public ArrayList<String[]> getTabel(){
		return this.tabel;
	}
	
	public String getNaTabelTekst(){
		return this.TekstNaTabel;		
	}	

}
