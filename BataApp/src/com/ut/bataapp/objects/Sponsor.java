package com.ut.bataapp.objects;

public class Sponsor {
	
	private String id;
	private String naam;
	private String branche;
	private String regio;
	private String link;
	private String omschrijving;
	private String afbeelding;

	public Sponsor(String id, String naam, String branche, String regio, String link, String omschrijving, String afbeelding) {
		this.id = id;
		this.naam = naam;
		this.branche = branche;
		this.regio = regio;
		this.link = link;
		this.omschrijving = omschrijving;
		this.afbeelding = afbeelding;
	}
	
	public String getId(){
		return this.id;
	}

	public String getNaam() {
		return naam;
	}
	
	public String getBrache(){
		return this.branche;
	}
	
	public String getRegio(){
		return this.regio;
	}
	
	public String getLink(){
		return this.link;
	}
	
	public String getOmschrijving(){
		return this.omschrijving;
	}
	
	public String getAfbeeldingLink(){
		return this.afbeelding;
	}
}