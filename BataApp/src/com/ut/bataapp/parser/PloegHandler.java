package com.ut.bataapp.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

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
	
	private Team team;
	private ArrayList<Looptijd> uitslagen;
	
	public PloegHandler(String path){
		super(path);
	}
	public Response getParsedData(){
		if(uitslagen.isEmpty()){
			uitslagen.add(new Looptijd(team,new Etappe(1),"123","abc"));
		}
		return new Response(uitslagen,this.status);
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("ploeg")) this.ploeg = true;
		if(localName.equals("startnummer")) this.startnummer = true;
		if(localName.equals("naam")) this.naam = true;
		if(localName.equals("startgroep")) this.startgroep = true;
		if(localName.equals("uitslag")) this.uitslag = true;
		if(localName.equals("foutcode")) this.foutcode = true;
		if(localName.equals("klassementstijd")) this.tijd = true;
		if(localName.equals("etappenummer")) this.etappe = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("ploeg")) this.ploeg = false;
		if(localName.equals("startgnummer")) this.startnummer = false;
		if(localName.equals("naam")) this.naam = false;
		if(localName.equals("startgroep")) this.startgroep = false;
		if(localName.equals("uitslag")) this.uitslag = false;
		if(localName.equals("foutcode")) this.foutcode = false;
		if(localName.equals("klassementstijd")) this.tijd = false;
		if(localName.equals("etappenummer")) this.etappe = false;
	}

	@Override
	public void characters(char ch[], int start, int length){
		if(ploeg){ 
			team = new Team();
			ploeg = false;
		}
		if(startnummer){ 
			team.setStartnummer(Integer.parseInt(new String(ch,start,length)));
			startnummer = false;
		}
		if(naam){ 
			team.setNaam(new String(ch,start,length));
			naam = false;
		}
		if(startgroep){ 
			team.setStartGroep(Integer.parseInt(new String(ch,start,length)));
			startgroep =false;
		}
		if(uitslag){
			uitslagen.add(new Looptijd(team,null,null,null));
			uitslag =false;
		}
		if(foutcode){ 
			uitslagen.get(uitslagen.size()-1).setFoutcode(new String(ch,start,length));
			foutcode = false;
		}
		if(tijd){ 
			uitslagen.get(uitslagen.size()-1).setTijd(new String(ch,start,length));
			tijd = false;
		}if(etappe) uitslagen.get(uitslagen.size()-1).setEtappe(new Etappe(Integer.parseInt(new String(ch,start,length))));
	}

	@Override
	public void startDocument() throws SAXException{
		this.uitslagen = new ArrayList<Looptijd>();
	}

}