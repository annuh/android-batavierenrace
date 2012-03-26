/*
 * Versie: v3
 * Date: 08-03-12 12:05
 * By: Jochem Elsinga
 * Update: made methods static
 * Update: Added ArrayList<Klassement> parseKlassement();
 * Update: Added ArrayList<Team> parseTeam();
 */

package com.ut.bataapp.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.Environment;
import android.util.Log;

import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.*;

public class Parsing{
	public String abc = "edit";
	
	//
	public static ArrayList<Uitslag> parseUitslag(final int id){
		ArrayList<Uitslag> uitslagen = new ArrayList<Uitslag>();
			try{
				InputSource input = getInputSource("ploeguitslag/"+id+".xml");
				//URL url = new URL("http://bata-dev.snt.utwente.nl/~jorne/xml_2011/ploeguitslag/"+id+".xml");
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				
				PloegHandler ploegHandler = new PloegHandler();
				xr.setContentHandler(ploegHandler);
				//xr.parse(new InputSource(url.openStream()));
				xr.parse(input);
				
				uitslagen = ploegHandler.getParsedData();
			}catch(SAXException e){
				Log.d("Uitslag","SAXEception: "+e.getMessage());
			}catch(MalformedURLException e){
				Log.d("Uitslag","MalformedUrlException: "+e.getMessage());
			}catch(ParserConfigurationException e){
				Log.d("Uitslag","ParserConfigException: "+e.getMessage());
			}catch(IOException e){
				Log.d("Uitslag","IOExcpetion: "+e.getMessage());
			}
		return uitslagen;
	}
	//Functie gebruikt om de KlassementXML te parse naar een ArrayList<Klassement>
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
			InputSource input = getInputSource("ploegen.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			TeamHandler teamHandler = new TeamHandler();
			xr.setContentHandler(teamHandler);
			xr.parse(input);

			teams = teamHandler.getParsedData();
		}catch(SAXException e){
			Log.d("Ploegen","SAXEception: "+e.getMessage());
		}catch(MalformedURLException e){
			Log.d("Ploegen","MalformedUrlException: "+e.getMessage());
		}catch(ParserConfigurationException e){
			Log.d("Ploegen","ParserConfigException: "+e.getMessage());
		}catch(IOException e){
			Log.d("Ploegen","IOExcpetion: "+e.getMessage());
		}
		return teams;
	}
	
	//Functie gebruikt om de EtappesXML te parse naar een ArrayList<Etappe>
	public static ArrayList<Etappe> parseEtappe(){
		ArrayList<Etappe> etappes = new ArrayList<Etappe>();
		try{
			InputSource input = getInputSource("etappes.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			EtappeHandler etappeHandler = new EtappeHandler();
			xr.setContentHandler(etappeHandler);
			xr.parse(input);
			
			etappes = etappeHandler.getParsedData();
			
		}catch(SAXException e){
			Log.d("Etappe","SAXEception: "+e.getMessage());
		}catch(MalformedURLException e){
			Log.d("Etappe","MalformedUrlException: "+e.getMessage());
		}catch(ParserConfigurationException e){
			Log.d("Etappe","ParserConfigException: "+e.getMessage());
		}catch(IOException e){
			Log.d("Etappe","IOExcpetion: "+e.getMessage());
		}
		return etappes;
	}
	
   /**
	* Deze functie levert een inputsource voor de parser
	* @param path het pad naar de file zoals bijvoorbeeld: "/etappes.xml"
	 * @throws IOException 
	*
	*/
	public static InputSource getInputSource(String path) throws IOException{
	//De locatie van de file is de root van de sdkaart+dexmlmap+het gegeven path.
	File sdFile = new File(Environment.getExternalStorageDirectory().getPath()+api.getSDmap()+path);
	Log.d("parser","gis:"+sdFile.getPath());
	InputSource result = null;
	if(sdFile.exists()){
		Log.d("parser","gis:file exist");
		if(isNewest(path)){
			result = new InputSource(new FileInputStream(sdFile));
		}
		else{
			if(downloadToSD(path)){
				//Het is gelukt om de file te downloaden, gebruik dit bestand.
				result = new InputSource(new FileInputStream(sdFile));	
			}else{
		    	//Het is niet gelukt de file te downloaden, gebruik oude versie
		    	result = new InputSource(new FileInputStream(sdFile));
			}
		}
	}
	else{
		Log.d("parser","gis:file not exist");
		if(downloadToSD(path)){
			//Het is gelukt om de file te downloaden, gebruik dit bestand.
			result = new InputSource(new FileInputStream(sdFile));	
			Log.d("parser","gis:file gedownload");
		}else{
		    //Het is niet gelukt de file te downloaden, wat nu?
		}	
	}
	
	return result;
	
	}
	
	
	/**
	* Deze functie controleerd of de huidige versie van een bestand de nieuwste is.
	*/
	public static boolean isNewest(String path){
		/**boolean result = false;
		if(#seconds from lastupdate of updatefile >api.getThreshold()){
			downloadToSD("update.xml");
		}
		if(#timestamp voor path in update.xml <= timestamp in path op SD){
			return true;
		}
		return result;*/
		return true;
	}
	
	
	/**
	* Deze methode probeert de file gegeven door path te downloaden naar de sdcard.
	* @result true als het downloaden gelukt is, false als het dat niet is
	*/
	public static boolean downloadToSD(String path){
	boolean result = false;
	try{
		Log.d("parser",api.getURL()+path);
		URL url = new URL(api.getURL()+path);//de file die gedownload moet worden
		//nieuwe verbinding:
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		//setup connectie:
	    urlConnection.setRequestMethod("GET");
	    urlConnection.setDoOutput(true);

	    // connect!
	    urlConnection.connect();
		
	    //De locatie van de file is de root van de sdkaart+dexmlmap+het gegeven path.
	    File xmlMap = new File(Environment.getExternalStorageDirectory().getPath()+api.getSDmap());
	    //Maak directory aan als die nog niet bestaat
	    if(!xmlMap.exists()){
	    	Log.d("parser","dts:xmlMap bestaat nog niet");
	    	xmlMap.mkdir();
	    }
	    
		File sdFile = new File(xmlMap.getPath()+'/'+path);
		if(!sdFile.getParentFile().exists()){
			sdFile.getParentFile().mkdir();
		}
		//sdFile is de file waar de nieuw file heengeschreven word.
	    
	    FileOutputStream fileOutput = new FileOutputStream(sdFile);

	    //InputStream vanaf internet
	    InputStream inputStream = urlConnection.getInputStream();

	    //aanmaken leesbuffer.
	    byte[] buffer = new byte[1024];
	    int bufferLength = 0; //tijdelijke lengte van de buffer

	    //lees door de input buffer en schrijf naar de File.
	    while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
	         fileOutput.write(buffer, 0, bufferLength);
	    }
	    //Sluit de outputSream
	    fileOutput.close();

		result = true;	
	}catch(MalformedURLException mue){
		Log.d("parser","malformedurl: "+mue.toString());
	}catch(IOException ioe){
		Log.d("parser","ioexeption: "+ioe.toString());
	}
	Log.d("parser","result: "+ result);

	return result;	
	}
}
