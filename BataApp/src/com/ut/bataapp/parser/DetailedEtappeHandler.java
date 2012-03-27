package com.ut.bataapp.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ut.bataapp.objects.Etappe;

public class DetailedEtappeHandler extends DefaultHandler{

	private int etappeNummer;
	private int currentNummer;
	private Etappe etappe;
	
	private boolean eta;
	private boolean id;
	private boolean van;
	private boolean naar;
	private boolean afstand;
	private boolean geslacht;
	private boolean omschrijving;
	
	private boolean record;
	private boolean record_jaar;
	private boolean record_ploeg;
	private boolean record_snelheid;
	private boolean record_tijd;
	
	public DetailedEtappeHandler(int id) {
		etappeNummer = id;
	}
	
	public Etappe getParsedData() {
		return etappe;
	}

	@Override
	public void startElement(String nameSpaceURI, String localName, String qName,Attributes atts) throws SAXException{
		if(localName.equals("etappe")) this.eta = true;
		if(localName.equals("volgnummer")) this.id =true;
		if(localName.equals("van")) this.van =true;
		if(localName.equals("naar")) this.naar =true;
		if(localName.equals("afstand")) this.afstand =true;
		if(localName.equals("type")) this.geslacht =true;
		if(localName.equals("omschrijving")) this.omschrijving =true;
		if(localName.equals("jaar")) this.record_jaar =true;
		if(localName.equals("ploeg")) this.record_ploeg =true;
		if(localName.equals("snelheid")) this.record_snelheid =true;
		if(localName.equals("tijd")) this.record_tijd =true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("etappe")) this.eta = false;
		if(localName.equals("volgnummer")) this.id =false;
		if(localName.equals("van")) this.van =false;
		if(localName.equals("naar")) this.naar =false;
		if(localName.equals("afstand")) this.afstand =false;
		if(localName.equals("type")) this.geslacht =false;
		if(localName.equals("omschrijving")) this.omschrijving =false;
		if(localName.equals("jaar")) this.record_jaar =false;
		if(localName.equals("ploeg")) this.record_ploeg =false;
		if(localName.equals("snelheid")) this.record_snelheid =false;
		if(localName.equals("tijd")) this.record_tijd =false;
	}
	
	@Override 
	public void characters(char ch[], int start, int length) throws SAXException{
		if(eta){
			if(id) currentNummer = Integer.parseInt(new String(ch,start,length));
			else if(currentNummer == etappeNummer){
				if(van) etappe.setVan(new String(ch,start,length));
				else if(naar) etappe.setNaar(new String(ch,start,length));
				else if(afstand) etappe.setAfstand(Integer.parseInt(new String(ch,start,length)));
				else if(geslacht) etappe.setGeslacht(ch[0]);
				else if(omschrijving) etappe.setOmschrijving(new String(ch,start,length));
				else if(record_jaar) etappe.setRecordJaar(new String(ch,start,length));
				else if(record_ploeg) etappe.setRecordTeam(new String(ch,start,length));
				else if(record_snelheid) etappe.setRecordSnelheid(new String(ch,start,length));
				else if(record_tijd) etappe.setRecordTijd(new String(ch,start,length));
			}
		}
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.etappe = new Etappe(etappeNummer);
	}
}
