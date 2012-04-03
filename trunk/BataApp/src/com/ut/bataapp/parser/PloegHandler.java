package com.ut.bataapp.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Looptijd;

public class PloegHandler extends Handler{
	
	private boolean ploeg;
	private boolean startnummer;
	private boolean naam;
	private boolean startgroep;
	
	private boolean uitslag;
	private boolean foutcode;
	private boolean tijd;
	private boolean etappe;
	
	private boolean snelheid;
	private boolean etappeStand;
	private boolean cumulatieveStand;

	private String teamNaam;
	private int startNummer;
	private int startGroep;
	private Looptijd looptijd;
	private ArrayList<Looptijd> uitslagen;
	
	public PloegHandler(String path){
		super(path);
	}
	public Response getParsedData(){
		return new Response(uitslagen,this.status);
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("ploeg")) this.ploeg = true;
		else if(localName.equals("startnummer")) this.startnummer = true;
		else if(localName.equals("naam")) this.naam = true;
		else if(localName.equals("startgroep")) this.startgroep = true;
		else if(localName.equals("uitslag")) this.uitslag = true;
		else if(localName.equals("foutcode")) this.foutcode = true;
		else if(localName.equals("klassementstijd")) this.tijd = true;
		else if(localName.equals("etappenummer")) this.etappe = true;
		else if(localName.equals("snelheid")) this.snelheid = true;
		else if(localName.equals("etappe")) this.etappeStand = true;
		else if(localName.equals("cumulatief")) this.cumulatieveStand = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("ploeg")) this.ploeg = false;
		else if(localName.equals("startnummer")) this.startnummer = false;
		else if(localName.equals("naam")) this.naam = false;
		else if(localName.equals("startgroep")) this.startgroep = false;
		else if(localName.equals("uitslag")) uitslagen.add(looptijd);
		else if(localName.equals("foutcode")) this.foutcode = false;
		else if(localName.equals("klassementstijd")) this.tijd = false;
		else if(localName.equals("etappenummer")) this.etappe = false;
		else if(localName.equals("snelheid")) this.snelheid = false;
		else if(localName.equals("etappe")) this.etappeStand = false;
		else if(localName.equals("cumulatief")) this.cumulatieveStand = false;
	}

	@Override
	public void characters(char ch[], int start, int length){
		if(startnummer) startNummer = Integer.parseInt(new String(ch,start,length));
		else if(naam) teamNaam = new String(ch,start,length);
		else if(startgroep) startGroep = Integer.parseInt(new String(ch,start,length));
		else if(uitslag){
			looptijd = new Looptijd();
			looptijd.setTeamNaam(teamNaam);looptijd.setTeamStartnummer(startNummer);looptijd.setTeamStartgroep(startGroep);
			uitslag = false;
		}
		else if(foutcode) looptijd.setFoutcode(new String(ch,start,length));
		else if(tijd) looptijd.setTijd(new String(ch,start,length));
		else if(etappe) looptijd.setEtappe(Integer.parseInt(new String(ch,start,length)));
		else if(snelheid) looptijd.setSnelheid(new String(ch,start,length));
		else if(etappeStand) looptijd.setEtappeStand(Integer.parseInt(new String(ch,start,length)));
		else if(cumulatieveStand) looptijd.setCumulatieveStand(Integer.parseInt(new String(ch,start,length)));
	}

	@Override
	public void startDocument() throws SAXException{
		this.uitslagen = new ArrayList<Looptijd>();
	}

}