package com.ut.bataapp.objects;

import java.util.ArrayList;

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
	
	public static ArrayList<Sponsor> getSponsors(){
		ArrayList<Sponsor> sponsors = new ArrayList<Sponsor>();
		sponsors.add(new Sponsor("1", "Susa", "Uitzendbureau", "Heel Nederland", "www.susa.nl", "Studenten uitzendbureau SUSA heeft volop bijbanen, studentenwerk en vakantiewerk. Schrijf je nu in voor een bijbaan die past bij jou!", "sponsor_susa"));
		sponsors.add(new Sponsor("2", "Ministerie van Defensie", "Beveiliging", "Heel Nederland", "www.defensie.nl", "Organisatie, nieuws, banen en overzichten van taken, lopende operaties en krijgsmachtonderdelen.", "sponsor_mindef"));
		sponsors.add(new Sponsor("3", "Autodrop", "Uitzendbureau", "Heel Nederland", "www.susa.nl", "Nieuw van Autodrop: centre filled! Vloeibare drop en snoep in verschillende smaken… Heb je ze al geproefd? Absurd lekker!", "sponsor_autodrop"));
		sponsors.add(new Sponsor("4", "Student Union", "Universiteit Twente", "Enschede", "www.studentunion.utwente.nl", "De Student Union is er om jouw studententijd optimaal te laten verlopen. Door middel van allerlei samenwerkingsverbanden ondersteunen we verenigingen en proberen we zoveel mogelijk kansen voor studenten te creëren.", "sponsor_su"));
		sponsors.add(new Sponsor("5", "Eurosport Borne", "Sportwinkel", "Borne (ov)", "www.eurosportborne.nl", "Wij Eurosport leveren clubkleding en materialen aan onder meer verenigingen en evenementen. Shirts, trainingspakken, ballen, doelen, warmloopshirts, trainingsmaterialen, niets is te gek.", "sponsor_eurosport"));
		sponsors.add(new Sponsor("6", "Enschede Promotie ", "marketing", "Enschede", "www.uitinenschede.nl", "Enschede Promotie ziet als haar kerntaken het bevorderen van het (dag)toerisme, het profileren van de culturele activiteiten van Enschede en het vermarkten van Enschede als een aantrekkelijke woon- en werkstad. Enschede Promotie ontwikkelt een strategisch (marketing)plan om de stad intern en extern sterker te positioneren.", "sponsor_uitinenschede"));
		sponsors.add(new Sponsor("7", "E-matching", "Datingsite", "Heel Nederland", "www.e-matching.nl", "Online dating voor hoger opgeleiden. De datingsite waar hbo'ers en academici een duurzame relatie vinden, bekend van radio en TV (durft ú het aan...).", "sponsor_ematch"));
		sponsors.add(new Sponsor("8", "Pentair x-flow", "High tech", "Enschede", "www.x-flow.com", "Pentair X-Flow membranes are used in the field of ultrafiltration in capillary form, and microfiltration and ultrafiltration in tubular form, which makes selective filtration possible. ", "sponsor_pentair"));
		sponsors.add(new Sponsor("9", "Grolsch", "Productie bier", "Enschede", "www.grolsch.nl", "Grolsch brouwt uitsluitend bier en richt zich op het premium segment van de markt. Het sterke merk Grolsch staat daarbij centraal. De kracht van het merk vindt zijn oorsprong in 'Vakmanschap is Meesterschap' en in de unieke beugelfles.", "sponsor_grolsch"));
		sponsors.add(new Sponsor("10", "Autorent", "Verhuur autos", "Twente", "www.bleekergroep.nl/autorent_twente", "AutoRent Twente is dé auto verhuurder in Oost-Nederland, met vestigingen in Almelo, Enschede, Hengelo en Oldenzaal. AutoRent Twente is onderdeel van AutoRent Europa Service, dat met 54 vestigingen de grootste autoverhuurder van Nederland is.", "sponsor_autorent"));
		sponsors.add(new Sponsor("11", "Studentenfiets", "Fietswinkel", "Enschede", "www.studentfiets-enschede.nl", "Studentfiets Enschede is een jong en bloeiend bedrijf. We richten ons op de verkoop van fietsen, speciaal voor studenten. Dat wil zeggen: goedkope, betrouwbare en vooral praktische fietsen! Naast de verkoop van fietsen is het ook mogelijk om alleen onderdelen bij ons te kopen.", "sponsor_studentenfiets"));
		return sponsors;
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