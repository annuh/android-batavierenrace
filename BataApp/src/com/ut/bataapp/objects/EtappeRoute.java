package com.ut.bataapp.objects;

import java.util.ArrayList;

public class EtappeRoute {
	
	private String TekstVoorTabel="";
	private ArrayList<String[]> tabel;
	private String TekstNaTabel="";
	
	public EtappeRoute(String TekstVoorTabel, ArrayList<String[]> tabel, String TekstNaTabel){
		
		this.TekstVoorTabel = TekstVoorTabel;
		this.tabel = tabel;
		this.TekstNaTabel = TekstNaTabel;		
	
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
