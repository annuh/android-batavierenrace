package com.ut.bataapp.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Response;

import android.os.Environment;
import android.util.Log;

public abstract class Handler extends DefaultHandler{
String location;
public int status = Response.NOK_NO_DATA;
private boolean parsed=false;//only for parse method!!
public Handler(String path){
	this.location = path;
}
public Handler(){}

public boolean parse(){
	boolean result = false;
	try{
		InputSource input = getInputSource(location);
		if(!parsed||!(status==Response.OK_NO_UPDATE)){
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		xr.setContentHandler(this);
		xr.parse(input);
		parsed = true;
		}
		result = true;
	}catch(SAXException e){
		Log.d("SimpleEtappe","SAXEception: "+e.getMessage());
	}catch(MalformedURLException e){
		Log.d("SimpleEtappe","MalformedUrlException: "+e.getMessage());
	}catch(ParserConfigurationException e){
		Log.d("SimpleEtappe","ParserConfigException: "+e.getMessage());
	}catch(IOException e){
		Log.d("SimpleEtappe","IOExcpetion: "+e.getMessage());
	}
	Log.d("parser","parse geeft als resultaat:"+result+ " bij klasse: "+this.getClass());
	return result;
}
/**
* Deze functie levert een inputsource voor de parser
* @param path het pad naar de file zoals bijvoorbeeld: "/etappes.xml"
 * @throws IOException 
*
*/
private InputSource getInputSource(String path) throws IOException{
File sdFile = getFile(path);
InputSource result = null;
if(sdFile == null){
	status = Response.NOK_OLD_DATA;
	result = new InputSource(new URL(api.getURL()+path).openStream());
}else{
if(sdFile.exists()){
	if(isNewest(path)){
		status = Response.OK_NO_UPDATE;
		result = new InputSource(new FileInputStream(sdFile));
	}
	else{
		if(downloadToSD(path)){
			status = Response.OK_UPDATE;
			//Het is gelukt om de file te downloaden, gebruik dit bestand.
			result = new InputSource(new FileInputStream(sdFile));	
		}else{
	    	//Het is niet gelukt de file te downloaden, gebruik oude versie
			status = Response.NOK_OLD_DATA;
	    	result = new InputSource(new FileInputStream(sdFile));
		}
	}
}
else{
	if(downloadToSD(path)){
		//Het is gelukt om de file te downloaden, gebruik dit bestand.
		result = new InputSource(new FileInputStream(sdFile));	
		status = Response.OK_UPDATE;
	}else{
		status = Response.NOK_NO_DATA;
	    //Het is niet gelukt de file te downloaden, wat nu?
	}	
}
}
return result;

}
private File getFile(String path){
	File result = null;
	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
	    //"externe" media is gemount dus we kunnen lezen en schrijven naar file.
		//Activity act = new Activity();
		//result =  new File(act.getExternalFilesDir(api.getSDmap()).getPath()+path);
		result = new File(Environment.getExternalStorageDirectory().getPath()+api.getSDmap()+path);
	}
	Log.d("parser","test"+result.getPath());
		return result;
}

/**
* Deze functie controleerd of de huidige versie van een bestand de nieuwste is.
*/
private boolean isNewest(String path){
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
private boolean downloadToSD(String path){
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
	
    
	File sdFile = getFile(path);
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
