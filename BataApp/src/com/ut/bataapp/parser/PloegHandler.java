package com.ut.bataapp.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;

public class PloegHandler extends Handler{
	
	private boolean ploeg;
	private boolean startnummer;
	private boolean naam;
	private boolean startgroep;
	
	private boolean uitslag;
	private boolean foutcode;
	private boolean klassement;
	private boolean tijd;
	private boolean etappe;
	private boolean bronetappe;
	
	private boolean snelheid;
	private boolean etappeStand;
	private boolean cumulatieveStand;
	private boolean cumklassementtijd;

	private String teamNaam = "";
	private String teamKlassement ="";
	private int startNummer;
	private int startGroep;
	private int totBronEtappe;
	private Looptijd looptijd;
	private Team team;
	
	public PloegHandler(String path){
		super(path);
	}
	public Response<Team> getParsedData(){
		return new Response<Team>(team,this.status);
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("ploeg")) this.ploeg = true;
		else if(localName.equals("bronetappe")) this.bronetappe = true;
		else if(localName.equals("startnummer")) this.startnummer = true;
		else if(localName.equals("naam")) this.naam = true;
		else if(localName.equals("startgroep")) this.startgroep = true;
		else if(localName.equals("uitslag")) this.uitslag = true;
		else if(localName.equals("klassement")) this.klassement = true;
		else if(localName.equals("foutcode")) this.foutcode = true;
		else if(localName.equals("klassementstijd")) this.tijd = true;
		else if(localName.equals("etappenummer")) this.etappe = true;
		else if(localName.equals("snelheid")) this.snelheid = true;
		else if(localName.equals("etappe")) this.etappeStand = true;
		else if(localName.equals("cumulatief")) this.cumulatieveStand = true;
		else if(localName.equals("cumklassementstijd")) this.cumklassementtijd = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("ploeg")){
			team.setNaam(teamNaam);
			team.setStartGroep(startGroep);
			team.setStartnummer(startNummer);
			team.setKlassementTotEtappe(totBronEtappe);
			team.setKlassement(teamKlassement);
			this.ploeg = false;
		}
		else if(localName.equals("bronetappe")) this.bronetappe = true;
		else if(localName.equals("startnummer")) this.startnummer = false;
		else if(localName.equals("naam")) this.naam = false;
		else if(localName.equals("startgroep")) this.startgroep = false;
		else if(localName.equals("uitslag")) team.addLooptijd(looptijd);
		else if(localName.equals("foutcode")) this.foutcode = false;
		else if(localName.equals("klassement")) this.klassement = false;
		else if(localName.equals("klassementstijd")) this.tijd = false;
		else if(localName.equals("etappenummer")) this.etappe = false;
		else if(localName.equals("snelheid")) this.snelheid = false;
		else if(localName.equals("etappe")) this.etappeStand = false;
		else if(localName.equals("cumulatief")) this.cumulatieveStand = false;
		else if(localName.equals("cumklassementstijd")) this.cumklassementtijd = false;
	}

	@Override
	public void characters(char ch[], int start, int length){
		if(startnummer) startNummer = Integer.parseInt(new String(ch,start,length));
		else if(naam) teamNaam = teamNaam.concat(new String(ch,start,length));
		else if(startgroep) startGroep = Integer.parseInt(new String(ch,start,length));
		else if(uitslag){
			looptijd = new Looptijd();
			looptijd.setTeamNaam(teamNaam);
			looptijd.setTeamStartnummer(startNummer);
			looptijd.setTeamStartgroep(startGroep);
			uitslag = false;
		}
		else if(bronetappe) this.totBronEtappe = Integer.parseInt(new String(ch,start,length));
		else if(foutcode) looptijd.setFoutcode(new String(ch,start,length));
		else if(klassement) team.setKlassement(new String(ch,start,length));
		else if(tijd) looptijd.setTijd(new String(ch,start,length));
		else if(etappe) looptijd.setEtappe(Integer.parseInt(new String(ch,start,length)));
		else if(snelheid) looptijd.setSnelheid(new String(ch,start,length));
		else if(etappeStand) looptijd.setEtappeStand(Integer.parseInt(new String(ch,start,length)));
		else if(cumulatieveStand) looptijd.setCumulatieveStand(Integer.parseInt(new String(ch,start,length)));
		else if(cumklassementtijd) looptijd.setCumtotaaltijd(new String(ch,start,length));
	}

	@Override
	public void startDocument() throws SAXException{
		this.team = new Team();
	}

	@Override
	public InputSource getInputSource() throws IOException {
		synchronized (PloegHandler.class) { // kan ook vanuit de AsyncTask-thread van BackgroundUpdater worden aangeroepen (tegelijk met een AsyncTask-thread uit een activity)
			return super.getInputSource();
		}
	}
}