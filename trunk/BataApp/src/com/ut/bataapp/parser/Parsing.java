/*
 * Versie: v2
 * Date: 07-03-12 15:18
 * By: Jochem Elsinga
 * Update: Added ArrayList<Team> parseTeam();
 */

package com.ut.bataapp.parser;

import java.io.IOException;
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
import android.widget.TextView;

import com.ut.bataapp.objects.*;

public class Parsing extends Activity{

	ArrayList<Etappe> etappes = new ArrayList<Etappe>();
	ArrayList<Team> teams = new ArrayList<Team>();
	
	public ArrayList<Team> parseTeam(){
		try{
			URL url = new URL("http://api.batavierenrace.nl/xml/2011/ploegen.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			TeamHandler teamHandler = new TeamHandler();
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
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return teams;
	}
	
	public ArrayList<Etappe> parseEtappe(){
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
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText(etappes.toString());
		setContentView(tv);
	}
}
