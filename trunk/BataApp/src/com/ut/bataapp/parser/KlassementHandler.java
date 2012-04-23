package com.ut.bataapp.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.KlassementItem;
import com.ut.bataapp.objects.Response;

public class KlassementHandler extends Handler{

	private Klassement klassement;
	private KlassementItem item = new KlassementItem();
	private String naam;
	
	private boolean klassnaam;
	private boolean readNaam; //true als de klassnaam == naam anders false;
	private boolean plaats;
	private boolean bron;
	private boolean positie;
	private boolean teamnr;
	private boolean teamnaam;
	private boolean startgroep;
	private boolean tijd;
	
	public KlassementHandler(String path,String naam){
		super(path);
		this.naam = naam;
	}
	
	public Response<Klassement> getParsedData() {
		return new Response<Klassement>(klassement,this.status);
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("naam")) klassnaam = true;
		else if(localName.equals("bronetappe")) bron = true;
		else if(localName.equals("plaats")) plaats = true;
		else if(localName.equals("positie")) positie = true;
		else if(localName.equals("teamnr")) teamnr = true;
		else if(localName.equals("teamnaam")) teamnaam = true;
		else if(localName.equals("startgroep")) startgroep = true;
		else if(localName.equals("tijd")) tijd = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("naam")) klassnaam = false;
		else if(localName.equals("plaats")){
			if(readNaam){
				klassement.addKlassementInfo(item);
			}
		}
		else if(localName.equals("bronetappe")) bron = false;
		else if(localName.equals("positie")) positie = false;
		else if(localName.equals("teamnr")) teamnr = false;
		else if(localName.equals("teamnaam")) teamnaam = false;
		else if(localName.equals("startgroep")) startgroep = false;
		else if(localName.equals("tijd")) tijd = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(klassnaam){
			if(new String(ch,start,length).equals(naam))readNaam = true;
			else readNaam = false;	
		}
		else if(bron){
			klassement.setTotEtappe(Integer.parseInt(new String(ch,start,length)));
		}
		if(readNaam){
			if(plaats){
				item =  new KlassementItem();
				plaats = false;
			}
			if(positie)	item.setPlaats(Integer.parseInt(new String(ch,start,length)));
			else if(teamnr) item.setTeamStartNummer(Integer.parseInt(new String(ch,start,length)));
			else if(teamnaam) item.setTeamNaam(new String(ch,start,length));
			else if(startgroep) item.setTeamStartGroep(Integer.parseInt(new String(ch,start,length)));
			else if(tijd) item.setTijd(new String(ch,start,length));
		}
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.klassement = new Klassement();
		klassement.setNaam(this.naam);
	}
}
