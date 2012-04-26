package com.ut.bataapp.objects;

public class KlassementItem{

	private int plaats = -1;
	private int teamStartNummer = -1;
	private int teamStartGroep = -1;
	private String teamNaam = "";
	private String tijd = "";
	
	public KlassementItem(){}

	public void setPlaats(int plaats) {this.plaats = plaats;}
	public void setTeamNaam(String teamNaam){ this.teamNaam = teamNaam;}
	public void appendTijd(String tijd){ this.tijd = this.tijd.concat(tijd); }
	public void setTeamStartNummer(int teamStartNummer){ this.teamStartNummer = teamStartNummer;}
	public void setTeamStartGroep(int teamStartGroep){ this.teamStartGroep = teamStartGroep;}
	public void appendTeamNaam(String teamNaam) { this.teamNaam = this.teamNaam.concat(teamNaam); }
	
	public int getPlaats() { return plaats; }
	public String getTeamNaam(){ return teamNaam;}
	public String getTijd(){ return tijd;}
	public int getTeamStartNummer(){ return teamStartNummer;}
	public int getTeamStartGroep() {return teamStartGroep;}
	
	public String toString(){
		return "KlassementInfo: "+getTeamNaam()+", "+getTeamStartNummer()+", "+getTijd();
	}

}