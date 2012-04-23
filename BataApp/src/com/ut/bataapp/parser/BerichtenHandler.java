package com.ut.bataapp.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.text.Html;
import android.util.Log;

import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Response;

public class BerichtenHandler extends Handler{
	private boolean item;
	private boolean id;
	private boolean titel;
	private boolean bericht;
	private boolean empty = false;
	
	private ArrayList<Bericht> berichten;
	
	public BerichtenHandler(String path){
		super(path);
	}
	
	public Response<ArrayList<Bericht>> getParsedData(){
		return new Response<ArrayList<Bericht>>(berichten,this.status);
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
		else if(bericht) {
			String toev = new String(ch,start,length);
			if(toev.contains("[b]")){
				toev = toev.replaceAll("\\[b\\]", "<b>");
				toev = toev.replaceAll("\\[/b\\]", "</b>");
			}else if(toev.contains("[link=")){
				toev = toev.replaceAll("\\[/link\\]", "</a>");
				toev = toev.replaceAll("\\[link=", "<a href=");
				toev = toev.replaceAll("]", ">");
			}
			Log.d("handler","toev: "+toev);
			if(toev.length()==1 && !empty){
				toev = "<br />";
				empty = true;
			}else{
				empty = false;
			}
			berichten.get(berichten.size()-1).appendBericht(toev);
		}
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.berichten = new ArrayList<Bericht>();
	}
}
