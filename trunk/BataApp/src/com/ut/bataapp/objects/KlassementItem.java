package com.ut.bataapp.objects;

public class KlassementItem{

	private int plaats;
	private int teamStartNummer;
	private String teamNaam;
	private String tijd;
	
	public KlassementItem(){}

	public void setPlaats(int plaats) {this.plaats = plaats;}
	public void setTeamNaam(String teamNaam){ this.teamNaam = teamNaam;}
	public void setTijd(String tijd){ this.tijd = tijd;}
	public void setTeamStartNummer(int teamStartNummer){ this.teamStartNummer = teamStartNummer;}

	public int getPlaats() { return plaats; }
	public String getTeamNaam(){ return teamNaam;}
	public String getTijd(){ return tijd;}
	public int getTeamStartNummer(){ return teamStartNummer;}

	public String toString(){
		return "KlassementInfo: "+getTeamNaam()+", "+getTeamStartNummer()+", "+getTijd();
	}

}