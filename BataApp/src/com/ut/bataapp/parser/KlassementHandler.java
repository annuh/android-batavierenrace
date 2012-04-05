package com.ut.bataapp.parser;

import java.util.ArrayList;
import java.util.TreeSet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Klassement.KlassementInfo;
import com.ut.bataapp.objects.Response;

public class KlassementHandler extends Handler{

	private ArrayList<Klassement> klassementen;
	private Klassement klassement;
	private KlassementInfo info;
	private int currentPos = -1;
	
	private boolean klass;
	private boolean klassnaam;
	private boolean plaats;
	private boolean positie;
	private boolean teamnr;
	private boolean teamnaam;
	private boolean tijd;
	
	public KlassementHandler(String path){
		super(path);
	}
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("klassement")) klass = true;
		else if(localName.equals("klassementnaam")) klassnaam = true;
		else if(localName.equals("plaats")) plaats = true;
		else if(localName.equals("positie")) positie = true;
		else if(localName.equals("teamnr")) teamnr = true;
		else if(localName.equals("teamnaam")) teamnaam = true;
		else if(localName.equals("tijd")) tijd = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("klassement")){
			klassementen.add(klassement);
			Log.d("Klassement","new klassement aan klassementen toegevoegd: "+klassement.toString());
		}
		else if(localName.equals("klassementnaam")) klassnaam = false;
		else if(localName.equals("plaats")){
			if(info==null){
				Log.d("klassement","er wordt niks in info geschreven");
			}else if(currentPos<0){
				Log.d("klassement","er is geen geldige positie");
			}else{
				klassement.addTeam(info, currentPos);
				Log.d("Klassement","new info aan klassement toegevoegd: "+info.toString()+" met positie: "+currentPos);
				currentPos = -1;
			}
		}
		else if(localName.equals("positie")) positie = false;
		else if(localName.equals("teamnr")) teamnr = false;
		else if(localName.equals("teamnaam")) teamnaam = false;
		else if(localName.equals("tijd")) tijd = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(klass){
			klassement = new Klassement();
			klass = false;
		}
		else if(klassnaam) klassement.setNaam(new String(ch,start,length));
		else if(plaats){
			info =  klassement.new KlassementInfo();
			plaats = false;
		}
		else if(positie) currentPos = Integer.parseInt(new String(ch,start,length));
		else if(teamnr) info.setTeamStartNummer(Integer.parseInt(new String(ch,start,length)));
		else if(teamnaam) info.setTeamNaam(new String(ch,start,length));
		else if(tijd) info.setTijd(new String(ch,start,length));
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.klassementen = new ArrayList<Klassement>();
	}
	
	public Response getParsedData() {
		return new Response(klassementen,this.status);
	}

}
