package com.ut.bataapp.parser;

import java.util.ArrayList;
import java.util.TreeSet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Response;

public class KlassementHandler extends Handler{

	private ArrayList<Klassement> klassement;
	private TreeSet<String> kNamen = new TreeSet<String>();
	private boolean klas;
	private boolean klassementNaam;
	private boolean teamNaam;
	private boolean positie;
	
	public KlassementHandler(String path){
		super(path);
	}
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
	
	public Response getParsedData() {
		return new Response(klassement,this.status);
	}

}
