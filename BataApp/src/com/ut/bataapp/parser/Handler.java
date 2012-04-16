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

public class Handler extends DefaultHandler {
	private String path;
	public int status = Response.NOK_NO_DATA;
	private boolean parsed = false;// only for parse method!!

	public Handler(String path) {
		this.path = path;
	}

	public Handler() {
	}
	
	public boolean parse(boolean suppressdownload) {
		boolean result = false;
		try {
			InputSource input = getInputSource(suppressdownload);
			if(status != Response.NOK_NO_DATA && (!parsed || status == Response.OK_UPDATE)){
			//if (!parsed && !(status == Response.OK_NO_UPDATE || status == Response.NOK_NO_DATA)) {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				xr.setContentHandler(this);
				xr.parse(input);
				parsed = true;
				result = true;
			}
		} catch (SAXException e) {
			Log.d("handler", "SAXEception: " + e.getMessage());
		} catch (MalformedURLException e) {
			Log.d("handler", "MalformedUrlException: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			Log.d("handler", "ParserConfigException: " + e.getMessage());
		} catch (IOException e) {
			Log.d("handler", "IOException: " + e.getMessage());
		}
		Log.d("parser", "parse geeft als resultaat:" + result + " bij klasse: "
				+ this.getClass());
		return result;
	}

	/**
	 * Deze functie levert een inputsource voor de parser
	 * 
	 * @param path
	 *            het pad naar de file zoals bijvoorbeeld: "/etappes.xml"
	 * @throws IOException
	 * 
	 */
	public InputSource getInputSource() throws IOException{
		return this.getInputSource(false);
	}
	public InputSource getInputSource(boolean suppressdownload) throws IOException {
		File sdFile = getFile(path);
		InputSource result = null;
		if (sdFile == null) {
			try{
				result = new InputSource(new URL(api.getURL() + path).openStream());
				status = Response.OK_UPDATE;
			}catch (IOException e){
				Log.d("parser","IOException bij geen sd kaart en geen internet: "+e.toString());
				status = Response.NOK_NO_DATA;
			}
		} else {
			if (sdFile.exists()) {
				if (isNewest(path) || suppressdownload) {
					status = Response.OK_NO_UPDATE;
					result = new InputSource(new FileInputStream(sdFile));
				} else {
					if (downloadToSD(path)) {
						status = Response.OK_UPDATE;
						// Het is gelukt om de file te downloaden, gebruik dit
						// bestand.
						result = new InputSource(new FileInputStream(sdFile));
					} else {
						// Het is niet gelukt de file te downloaden, gebruik
						// oude versie
						status = Response.NOK_OLD_DATA;
						result = new InputSource(new FileInputStream(sdFile));
					}
				}
			} else {
				if (downloadToSD(path)) {
					// Het is gelukt om de file te downloaden, gebruik dit
					// bestand.
					result = new InputSource(new FileInputStream(sdFile));
					status = Response.OK_UPDATE;
				} else {
					status = Response.NOK_NO_DATA;
					// Het is niet gelukt de file te downloaden, dus de server werkt niet, error versturen!!
				}
			}
		}
		return result;
	}

	private File getFile(String path) {
		File result = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// De locatie van de file is de root van de sdkaart+dexmlmap+het
			// gegeven path.
			File map = new File(Environment.getExternalStorageDirectory().getPath() + api.getSDmap());
			// Maak directory aan als die nog niet bestaat
			if (!map.exists()) {
				Log.d("parser", "dts:xmlMap bestaat nog niet");
				map.mkdir();
			}

			result = new File(map.getPath() + '/' + path);
			if (!result.getParentFile().exists()) {
				result.getParentFile().mkdir();
			}
			// sdFile is de file waar de nieuw file heengeschreven word.
			// "externe" media is gemount dus we kunnen lezen en schrijven naar
			// file.
			// Activity act = new Activity();
			// result = new
			// File(act.getExternalFilesDir(api.getSDmap()).getPath()+path);
			// result = new
			// File(Environment.getExternalStorageDirectory().getPath()+api.getSDmap()+path);
			Log.d("parser", "getfile" + result.getPath()+ " exists: "+  result.exists());
		}
		return result;
	}

  /**
	 * Deze functie controleerd of de huidige versie van een bestand de nieuwste
	 * is.
	 * @require getFile(path) != null
	 */
	private boolean isNewest(String path) {
		boolean result = true;
		long timestampsd = getFile(path).lastModified();
		long timestampwww = 0;
		
		try {
			URL url = new URL(api.getURL() + path);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			timestampwww = urlConnection.getLastModified();
		} catch (MalformedURLException e) {result = false;
		} catch (IOException e) {result = false;}
		
		if(timestampwww>timestampsd || timestampwww == 0){
			result = false;
		}
		Log.d("handler","timestamp sd: "+timestampsd+" voor file: "+path);
		Log.d("handler","timestamp www: "+timestampwww+" voor file: "+path);
		Log.d("handler","isNewest("+path+") = "+result);
		return result;
	}

	/**
	 * Deze methode probeert de file gegeven door path te downloaden naar de
	 * sdcard.
	 * 
	 * @result true als het downloaden gelukt is, false als het dat niet is
	 */
	private boolean downloadToSD(String path) {
		boolean result = false;
		try {
			Log.d("parser", "downloadtosd: "+api.getURL() + path);
			URL url = new URL(api.getURL() + path);// de file die gedownload
													// moet worden
			// nieuwe verbinding:
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// setup connectie:
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			// connect!
			urlConnection.connect();

			File sdFile = getFile(path+"t");
			// sdFile is de file waar de nieuw file heengeschreven word.

			FileOutputStream fileOutput = new FileOutputStream(sdFile);

			// InputStream vanaf internet
			InputStream inputStream = urlConnection.getInputStream();

			// aanmaken leesbuffer.
			byte[] buffer = new byte[1024];
			int bufferLength = 0; // tijdelijke lengte van de buffer

			// lees door de input buffer en schrijf naar de File.
			while ((bufferLength = inputStream.read(buffer)) > 0) {
				fileOutput.write(buffer, 0, bufferLength);
			}
			
			File tempfile = getFile(path);
			sdFile.renameTo(tempfile);
			long temp = urlConnection.getLastModified();
			tempfile.setLastModified(temp);

			// Sluit de outputSream en httpconnection
			fileOutput.close();
			urlConnection.disconnect();
			
			result = true;
		} catch (MalformedURLException mue) {
			Log.d("parser", "malformedurl: " + mue.toString());
		} catch (IOException ioe) {
			Log.d("parser", "ioexeption: " + ioe.toString());
		}
		Log.d("parser", "result: " + result);

		return result;
	}

	public int getStatus() {
		return status;
	}
}