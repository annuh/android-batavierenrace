package com.ut.bataapp.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Response;

public class BerichtenHandler extends Handler{
	private boolean item;
	private boolean id;
	private boolean titel;
	private boolean bericht;
	
	private ArrayList<Bericht> berichten;
	
	public BerichtenHandler(String path){
		super(path);
	}
	
	public Response getParsedData(){
		return new Response(berichten,this.status);
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("item")) this.item = true;
		else if(localName.equals("id")) this.id = true;
		else if(localName.equals("titel")) this.titel = true;
		else if(localName.equals("bericht")) this.bericht = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("item")) this.item = false;
		else if(localName.equals("id")) this.id = false;
		else if(localName.equals("titel")) this.titel = false;
		else if(localName.equals("bericht")) this.bericht = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(item){
			berichten.add(new Bericht());
			item = false;
		}
		if(id) berichten.get(berichten.size()-1).setId(new String(ch,start,length));
		else if(titel) berichten.get(berichten.size()-1).setTitel(new String(ch,start,length));
		else if(bericht) berichten.get(berichten.size()-1).setBericht(new String(ch,start,length));
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.berichten = new ArrayList<Bericht>();
	}
}
