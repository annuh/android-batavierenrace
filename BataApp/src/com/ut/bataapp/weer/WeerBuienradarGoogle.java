package com.ut.bataapp.weer;

import com.ut.bataapp.*;

import android.content.Context;
import android.graphics.*;

import java.io.*;
import java.net.*;
import java.util.Calendar;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Weerprovider die gebruikmaakt van de gegevens van Buienradar en Google.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerBuienradarGoogle implements WeerProvider {
	/* maximaal aantal dagen verwachting */
	private static final byte MAX_DAYS_AHEAD = 3;
	
	/* Huidige weersituatie van alle plaatsen.
	 * @invariant mHuidig != null && mHuidig.length == TOTAL
	 * @invariant hasData() ==> voor alle 0 <= i < mHuidig.length: mHuidig[i] != null
	 */
	private WeerInfoHuidig[] mHuidig = new WeerInfoHuidig[TOTAL];
	/* Verwachting voor alle plaatsen, behorend bij de bij refresh() opgegeven datum.
	 * Elementen kunnen ook bij hasData() == true false zijn, omdat de bij refresh() opgegeven datum te ver vooruit ligt
	 * @invariant mVerwachting != null && mVerwachting.length == TOTAL
	 */
	private WeerInfoVerwachting[] mVerwachting = new WeerInfoVerwachting[TOTAL];
	/* Tekstuele verwachting.
	 * @invariant mHasData ==> mAlgemeneVerwachting != null
	 */
	private String mAlgemeneVerwachting;
	
	/* Geeft aan of deze weerprovider data heeft, dus of refresh() (succesvol) is aangeroepen. */
	private boolean mHasData = false;
	/* De te gebruiken context (voor getResources()).
	 * @invariant mCtx != null
	 */
	private Context mCtx;
	
	/**
	 * Creeert een nieuwe WeerBuienradarGoogle
	 * @param ctx context
	 * @require ctx != null  
	 */
	public WeerBuienradarGoogle(Context ctx) {
		mCtx = ctx;
	}
	
	/* Haalt de bitmap op van url en geeft deze terug. Kan null zijn, dan kan er geen bitmap worden opgehaald op url.
	 * @param url locatie waarvandaan bitmap gedownload moet worden
	 * @return bitmap op url of null wanneer er geen bitmap opgehaald kan worden op url
	 * @require url != null
	 */
	private Bitmap getBitmap(String url) {
		Bitmap result;
		try {
			result = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
		} catch (MalformedURLException e) {
			result = null;
		} catch (IOException e) {
			result = null;
		}
		return result;
	}
	
	/* Parset algemene (tekstuele) verwachting uit buienradar en geeft deze terug. Kan null returnen, dan is er
	 * geen algemene tekstuele verwachting uit de XML te halen.
	 * @param buienradar de te parsen buienradar-XML
	 * @return algemene (tekstuele) verwachting of null wanner geen algemene tekstuele verwachting uit de XML is te halen
	 * @require buienradar != null
	 */
	private String parseAlgemeneVerwachting(Document buienradar) {
		String tagVerwachtingTekst = mCtx.getResources().getString(R.string.tag_buienradar_verwachting_tekst);
		NodeList nodeList = buienradar.getElementsByTagName(tagVerwachtingTekst);
		return ((nodeList.getLength() > 0) ? nodeList.item(0).getTextContent(): null);
	}
	
	/* Parset specifieke weerinfo uit xml op basis van de argumenten en geeft deze terug. Kan null zijn, dan is er geen
	 * weerinfo op basis van de argumenten uit de xml te halen.
	 * @param xml de te parsen Google-Weather-XML
	 * @param tagDag elementtag behorend bij de dag
	 * @param tagTemp1 elementtag behorend bij de eerste temperatuur (min/huidig)
	 * @param tagTemp2 elementtag behorend bij de eerste temperatuur (max/huidig)
	 * @param tagIcon elementtag behorend bij grafische beschrijving
	 * @param attrData attribuutnaam van data
	 * @param urlPrefix prefix voor url's van grafische beschrijvingen
	 * @param dag geeft de dag aan waarvan de weerinfo opgehaald moet worden (0: vandaag, 1: morgen, 2: overmorgen, ...)
	 * @param fahrenheit geeft aan of de temperaturen uit de XML in fahrenheit zijn
	 * @return weerinfo uit xml gehaald op basis van de argumenten
	 * @require xml != null && tagDag != null && tagTemp1 != null && tagTemp2 != null && tagIcon != null && attrData != null && urlPrefix != null
	 * @require 0 <= dag <= MAX_DAYS_AHEAD
	 */
	private WeerInfo parseGoogle(Document xml, String tagDag, String tagTemp1, String tagTemp2, String tagIcon, String attrData, String urlPrefix, int dag, boolean fahrenheit) {	
		WeerInfo result = null;
		try {
			Element elemGoogleDag = (Element) xml.getElementsByTagName(tagDag).item(dag),
					elemGoogleDagTemp1 = (Element) elemGoogleDag.getElementsByTagName(tagTemp1).item(0),
					elemGoogleDagTemp2 = (Element) elemGoogleDag.getElementsByTagName(tagTemp2).item(0),
				    elemGoogleDagIcon = (Element) elemGoogleDag.getElementsByTagName(tagIcon).item(0);
			byte temp1 = Byte.parseByte(elemGoogleDagTemp1.getAttribute(attrData)), temp2 = Byte.parseByte(elemGoogleDagTemp2.getAttribute(attrData));
			if (fahrenheit) {
				temp1 = Utils.convertFtoC(temp1);
				temp2 = Utils.convertFtoC(temp2);
			}
			String iconURL = urlPrefix + elemGoogleDagIcon.getAttribute(attrData);
			Bitmap bitmap = getBitmap(iconURL);
			if (bitmap != null)
				if (tagTemp1.equals(tagTemp2))
					result = new WeerInfoHuidig(temp1, bitmap);
				else
					result = new WeerInfoVerwachting(temp1, temp2, bitmap);
		} catch (ArrayIndexOutOfBoundsException e) {}
		return result;
	}
	
	public void refresh(Calendar datum) throws WeerException {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document buienradar = documentBuilder.parse(mCtx.getResources().getString(R.string.url_xml_buienradar));
			mAlgemeneVerwachting = parseAlgemeneVerwachting(buienradar);
			if (mAlgemeneVerwachting != null) {
				short diffDays = Utils.diffDays(datum);
				String[] googleURLs = mCtx.getResources().getStringArray(R.array.url_xml_google);
				String tagGoogleHuidig = mCtx.getResources().getString(R.string.tag_google_huidig),
					   tagGoogleHuidigTemp = mCtx.getResources().getString(R.string.tag_google_huidig_temp),
					   tagGoogleHuidigIcon = mCtx.getResources().getString(R.string.tag_google_huidig_icon),
					   tagGoogleVerwachting = mCtx.getResources().getString(R.string.tag_google_verwachting),
					   tagGoogleVerwachtingMin = mCtx.getResources().getString(R.string.tag_google_verwachting_min),
					   tagGoogleVerwachtingMax = mCtx.getResources().getString(R.string.tag_google_verwachting_max),
					   tagGoogleVerwachtingIcon = mCtx.getResources().getString(R.string.tag_google_verwachting_icon),
					   attrGoogleData = mCtx.getResources().getString(R.string.attr_google_data),
					   urlPrefix = mCtx.getResources().getString(R.string.url_google_icon_prefix);
				for (int i=0; i<TOTAL; i++) {
					Document xml = documentBuilder.parse(googleURLs[i]);
					WeerInfoHuidig huidig = (WeerInfoHuidig) parseGoogle(xml, tagGoogleHuidig, tagGoogleHuidigTemp, tagGoogleHuidigTemp, tagGoogleHuidigIcon, attrGoogleData, urlPrefix, 0, false);
					if (huidig != null)
						mHuidig[i] = huidig;
					else
						throw new WeerException(mCtx.getResources().getString(R.string.error_cant_parse_xml));
					if (0 <= diffDays && diffDays <= MAX_DAYS_AHEAD) {
						WeerInfoVerwachting verwachting = (WeerInfoVerwachting) parseGoogle(xml, tagGoogleVerwachting, tagGoogleVerwachtingMin, tagGoogleVerwachtingMax, tagGoogleVerwachtingIcon, attrGoogleData, urlPrefix, diffDays, true);
						if (verwachting != null)
							mVerwachting[i] = verwachting;
						else
							throw new WeerException(mCtx.getResources().getString(R.string.error_cant_parse_xml));
					}
				}
			}
			mHasData = true;
		} catch (ParserConfigurationException e) {
			throw new WeerException(mCtx.getResources().getString(R.string.error_cant_start_parser) + e);
		} catch (SAXException e) {
			throw new WeerException(mCtx.getResources().getString(R.string.error_cant_parse_xml) + e);
		} catch (IOException e) {
			throw new WeerException(mCtx.getResources().getString(R.string.error_cant_download_xml) + e);
		}
	}
	
	public boolean hasData() {
		return mHasData;
	}
	
	public WeerInfoVerwachting getVerwachting(int plaats) {
		return mVerwachting[plaats];
	}

	public WeerInfoHuidig getHuidig(int plaats) {
		return mHuidig[plaats];
	}
	
	public String getAlgemeneVerwachting() {
		return mAlgemeneVerwachting;
	}
	
	public byte getMax() {
		byte result = Byte.MIN_VALUE;
		for (int i=0; i<mVerwachting.length; i++)
			if (mVerwachting[i] != null && mVerwachting[i].getMaxTemp() > result)
				result = mVerwachting[i].getMaxTemp();
		return result;
	}
}