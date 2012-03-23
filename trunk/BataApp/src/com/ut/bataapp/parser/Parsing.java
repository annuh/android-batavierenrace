/*
 * Versie: v3
 * Date: 08-03-12 12:05
 * By: Jochem Elsinga
 * Update: made methods static
 * Update: Added ArrayList<Klassement> parseKlassement();
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

import com.ut.bataapp.objects.*;

public class Parsing{
	public String abc = "edit";
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
			URL url = new URL("http://api.batavierenrace.nl/xml/2011/ploegen.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			TeamHandler teamHandler = new TeamHandler();
			xr.setContentHandler(teamHandler);
			xr.parse(new InputSource(url.openStream()));

			teams = teamHandler.getParsedData();
		}catch(SAXException e){
			teams.add(new Team(0,0,"Er is iets fout met SAX : "+e.toString()));
			return teams;
		}catch(MalformedURLException e){
			teams.add(new Team(0,0,"Er is iets fout met URL: "+e.toString()));
			return teams;
		}catch(ParserConfigurationException e) {
			teams.add(new Team(0,0,"Er is iets fout met Parser: "+e.toString()));
			return teams;
		}catch(IOException e){
			teams.add(new Team(0,0,"Er is iets fout met IO: "+e.toString()));
			return teams;
		}catch(Exception e){
			teams.add(new Team(0,0,"Er is iets fout iets heel anders: "+e.toString()));
			return teams;
		}
		if(teams.isEmpty()){
			teams.add(new Team(0,0,"Ik haal niks binnen =( asdsabadf"));
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
   /**
	* Deze functie levert een inputsource voor de parser
	* @param path het pad naar de file zoals bijvoorbeeld: "/etappes.xml"
	 * @throws IOException 
	*
	*/
	public static InputSource getInputSource(String path) throws IOException{
	File sdRoot = Environment.getExternalStorageDirectory();
	File sdFile = new File(sdRoot,path);
	InputSource result = null;
	if(sdFile.exists()){
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
		if(downloadToSD(path)){
			//Het is gelukt om de file te downloaden, gebruik dit bestand.
			result = new InputSource(new FileInputStream(sdFile));	
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
	* http://www.androidsnippets.com/download-an-http-file-to-sdcard-with-progress-notification
	* http://www.vogella.de/articles/AndroidFileSystem/article.html
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
		
		File sdRoot = Environment.getExternalStorageDirectory();
		File sdFile = new File(sdRoot,path);
		//this will be used to write the downloaded data into the file we created
	    
	    FileOutputStream fileOutput = new FileOutputStream(sdFile);

	    //this will be used in reading the data from the internet
	    InputStream inputStream = urlConnection.getInputStream();

	    //this is the total size of the file
	    int totalSize = urlConnection.getContentLength();
	    //variable to store total downloaded bytes
	    int downloadedSize = 0;

	    //create a buffer...
	    byte[] buffer = new byte[1024];
	    int bufferLength = 0; //used to store a temporary size of the buffer

	    //now, read through the input buffer and write the contents to the file
	    while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
	         //add the data in the buffer to the file in the file output stream (the file on the sd card
	         fileOutput.write(buffer, 0, bufferLength);
	         //add up the size so we know how much is downloaded
	         downloadedSize += bufferLength;
	         //this is where you would do something to report the prgress, like this maybe
	         //updateProgress(downloadedSize, totalSize);
	        }
	        //close the output stream when done
	        fileOutput.close();

		result = true;	
	}catch(MalformedURLException mue){
		Log.d("parser","malformedurl");
	}catch(IOException ioe){
		Log.d("parser","ioexeption");
	}
	Log.d("parser","result"+ result);

	return result;	
	}
}
