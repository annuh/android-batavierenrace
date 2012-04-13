package com.ut.bataapp.parser;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;

public class EtappeUitslagHandler extends Handler{

	private Looptijd looptijd;
	private int etappeID;
	private ArrayList<Looptijd> uitslagen;
	
	private boolean uitslag;
	private boolean foutcode;
	private boolean tijd;
	private boolean snelheid;
	
	private boolean cumulatieveStand;
	private boolean etappeStand;
	
	private boolean startnummer;
	private boolean naam;
	private boolean startgroep;
	
	public EtappeUitslagHandler(String path,int id) {
		super(path);
		etappeID = id;
	}
	
	public Response<ArrayList<Looptijd>> getParsedData() {
		return new Response<ArrayList<Looptijd>>(uitslagen,this.status);
	}

	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("uitslag")) this.uitslag = true;
		if(localName.equals("foutcode")) this.foutcode = true;
		if(localName.equals("startnummer")) this.startnummer = true;
		if(localName.equals("naam")) this.naam = true;
		if(localName.equals("startgroep")) this.startgroep = true;
		if(localName.equals("klassementstijd")) this.tijd = true;
		if(localName.equals("etappe")) this.etappeStand = true;
		if(localName.equals("cumulatief")) this.cumulatieveStand = true;
		if(localName.equals("snelheid")) this.snelheid = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("uitslag")) uitslagen.add(looptijd);
		if(localName.equals("foutcode")) this.foutcode = false;
		if(localName.equals("startnummer")) this.startnummer = false;
		if(localName.equals("naam")) this.naam = false;
		if(localName.equals("startgroep")) this.startgroep = false;
		if(localName.equals("klassementstijd")) this.tijd = false;
		if(localName.equals("etappe")) this.etappeStand = false;
		if(localName.equals("cumulatief")) this.cumulatieveStand = false;
		if(localName.equals("snelheid")) this.snelheid = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(uitslag){
			looptijd = new Looptijd();
			looptijd.setEtappe(etappeID);
			uitslag = false;
		}
		else if(foutcode) looptijd.setFoutcode(new String(ch,start,length));
		else if(startnummer) looptijd.setTeamStartnummer(Integer.parseInt(new String(ch,start,length)));
		else if(naam) looptijd.setTeamNaam(new String(ch,start,length));
		else if(startgroep) looptijd.setTeamStartgroep(Integer.parseInt(new String(ch,start,length)));
		else if(tijd) looptijd.setTijd(new String(ch,start,length));
		else if(etappeStand) looptijd.setEtappeStand(Integer.parseInt(new String(ch,start,length)));
		else if(cumulatieveStand) looptijd.setCumulatieveStand(Integer.parseInt(new String(ch,start,length)));
		else if(snelheid) looptijd.setSnelheid(new String(ch,start,length));
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.uitslagen = new ArrayList<Looptijd>();
	}
}
