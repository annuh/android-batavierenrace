/*
 * Versie: v1
 * Date: 07-03-12 15:57
 * By: Jochem Elsinga
 * Created TeamHandler
 */

package com.ut.bataapp.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.*;
import com.ut.bataapp.objects.Team;

public class SimpleTeamHandler extends DefaultHandler2{

	private boolean ploeg;
	private boolean startnummer;
	private boolean startgroep;
	private boolean naam;
	private boolean dontRead;
	
	private ArrayList<Team> teams;
	
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("p")) this.ploeg = true;
		else if(localName.equals("s")) this.startnummer = true;
		else if(localName.equals("n")) this.naam = true;
		else if(localName.equals("g")) this.startgroep = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{		
		if(localName.equals("s")) this.startnummer = false;
		else if(localName.equals("n")) this.naam = false;
		else if(localName.equals("g")) this.startgroep = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(ploeg){
			teams.add(new Team());
			ploeg = false;
		}
		if(startgroep) teams.get(teams.size()-1).setStartGroep(Integer.parseInt(new String(ch,start,length)));
		else if(naam) teams.get(teams.size()-1).setNaam(new String(ch,start,length));
		else if(startnummer) teams.get(teams.size()-1).setStartnummer(Integer.parseInt(new String(ch,start,length)));
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.teams = new ArrayList<Team>();
		teams.add(0,new Team(0,0,"TEST!!"));
	}
}
