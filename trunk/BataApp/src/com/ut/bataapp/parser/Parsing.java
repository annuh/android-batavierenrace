/*
 * Versie: v3
 * Date: 08-03-12 12:05
 * By: Jochem Elsinga
 * Update: made methods static
 * Update: Added ArrayList<Klassement> parseKlassement();
 * Update: Added ArrayList<Team> parseTeam();
 */

package com.ut.bataapp.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.ut.bataapp.objects.*;

public class Parsing{

	//Functi gebruikt om de KlassementXML te parse naar een ArrayList<Klassement>
	public static ArrayList<Klassement> parseKlassement(){
		ArrayList<Klassement> klassement = new ArrayList<Klassement>();
		try{
			URL url = new URL("http://api.batavierenrace.nl/xml/2010/ask.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			KlassementHandler klassementHandler = new KlassementHandler();
			xr.setContentHandler(klassementHandler);
			xr.parse(new InputSource(url.openStream()));
			
			klassement = klassementHandler.getParsedData();
			
		}catch(SAXException e){
			e.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(ParserConfigurationException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return klassement;
	}
	
	//Functie gebruikt om de TeamsXML te parse naar een ArrayList<Team>
	public static ArrayList<Team> parseTeam(){
		ArrayList<Team> teams = new ArrayList<Team>();
		try{
			URL url = new URL("http://dl.dropbox.com/u/61224458/ploegensmall.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			SimpleTeamHandler teamHandler = new SimpleTeamHandler();
			xr.setContentHandler(teamHandler);
			xr.parse(new InputSource(url.openStream()));
			
			teams = teamHandler.getParsedData();
		}catch(SAXException e){
			e.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(ParserConfigurationException e) {
			e.printStackTrace();
		}catch(IOException e){
			teams.add(new Team(0,0,"Er is iets fout: "+e.toString()));
			return teams;
		}catch(Exception e){
			e.printStackTrace();
		}
		return teams;
	}
	
	//Functie gebruikt om de EtappesXML te parse naar een ArrayList<Etappe>
	public static ArrayList<Etappe> parseEtappe(){
		ArrayList<Etappe> etappes = new ArrayList<Etappe>();
		try{
			URL url = new URL("http://api.batavierenrace.nl/xml/2011/etappes.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			EtappeHandler etappeHandler = new EtappeHandler();
			xr.setContentHandler(etappeHandler);
			xr.parse(new InputSource(url.openStream()));
			
			etappes = etappeHandler.getParsedData();
			
		}catch(SAXException e){
			e.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(ParserConfigurationException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return etappes;
	}
}
