package com.ut.bataapp.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;

public class EtappeUitslagHandler extends Handler{

	private Looptijd looptijd;
	private ArrayList<Looptijd> uitslagen;
	
	private boolean uitslag;
	private boolean foutcode;
	private boolean tijd;
	private boolean snelheid;
	
	private boolean ploeg;
	private boolean startnummer;
	private boolean naam;
	private boolean startgroep;

	private int etappeID;
	
	public EtappeUitslagHandler(String path,int id) {
		super(path);
		etappeID = id;
	}
	
	public Response getParsedData() {
		return new Response(uitslagen,this.status);
	}


	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("uitslag")) this.uitslag = true;
		if(localName.equals("foutcode")) this.foutcode = true;
		if(localName.equals("startnummer")) this.startnummer = true;
		if(localName.equals("naam")) this.naam = true;
		if(localName.equals("startgroep")) this.startgroep = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("foutcode")) this.foutcode = false;
		if(localName.equals("startnummer")) this.startnummer = false;
		if(localName.equals("naam")) this.naam = false;
		if(localName.equals("startgroep")) this.startgroep = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(uitslag){
			looptijd = new Looptijd();
			uitslag = false;
		}
	}
	
	@Override
	public void startDocument() throws SAXException{
		
	}
}
