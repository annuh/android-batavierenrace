package com.ut.bataapp.parser;

import java.util.ArrayList;
import java.util.TreeSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ut.bataapp.objects.Klassement;

public class KlassementHandler extends DefaultHandler{

	private ArrayList<Klassement> klassement;
	private TreeSet<String> kNamen = new TreeSet<String>();
	private boolean klas;
	private boolean klassementNaam;
	private boolean teamNaam;
	private boolean positie;
	
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(klas){
			
		}
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.klassement = new ArrayList<Klassement>();
	}
	
	public ArrayList<Klassement> getParsedData() {
		return klassement;
	}

}
