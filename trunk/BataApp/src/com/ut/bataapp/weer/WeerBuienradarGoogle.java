package com.ut.bataapp.weer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ut.bataapp.R;

public class WeerBuienradarGoogle implements WeerProvider {
	public static final int MILLIS_IN_DAY = 60 * 60 * 24 * 1000;
	public static final byte MAX_DAYS_AHEAD = 3;
	public static final byte DIFF_CF = 32;
	public static final float FACTOR_CF  = 1.8F;
	
	private WeerInfo[] mHuidig = new WeerInfo[TOTAL];
	private WeerInfo[] mVerwachting = new WeerInfo[TOTAL];
	private String mAlgemeneVerwachting;
	
	private boolean mHasData = false;
	private Context mCtx;
	
	public WeerBuienradarGoogle(Context ctx) {
		mCtx = ctx;
	}
	
	private short diffDays(Date date) {
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    Date dateWithoutTime = cal.getTime();
	    return (short) ((date.getTime() - dateWithoutTime.getTime()) / MILLIS_IN_DAY); 
	}
	
	private byte convertFtoC(byte f) {
		return (byte) Math.round((f-DIFF_CF) / FACTOR_CF);
	}
	
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
	
	private String parseAlgemeneVerwachting(Document buienradar) {
		String tagVerwachtingTekst = mCtx.getResources().getString(R.string.tag_buienradar_verwachting_tekst);
		return buienradar.getElementsByTagName(tagVerwachtingTekst).item(0).getTextContent();
	}
	
	private WeerInfo parseGoogle(Document xml, String tagDag, String tagTemp, String tagIcon, String attrData, String urlPrefix, int dag, boolean fahrenheit) {	
		Element elemGoogleDag = (Element) xml.getElementsByTagName(tagDag).item(dag),
				elemGoogleDagTemp = (Element) elemGoogleDag.getElementsByTagName(tagTemp).item(0),
			    elemGoogleDagIcon = (Element) elemGoogleDag.getElementsByTagName(tagIcon).item(0);
		byte temp = Byte.parseByte(elemGoogleDagTemp.getAttribute(attrData));
		if (fahrenheit)
			temp = convertFtoC(temp);
		String iconURL = urlPrefix + elemGoogleDagIcon.getAttribute(attrData);
		return new WeerInfo(temp, getBitmap(iconURL));
	}

	public void refresh(Date datum) throws WeerException {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document buienradar = documentBuilder.parse(mCtx.getResources().getString(R.string.url_xml_buienradar));
			mAlgemeneVerwachting = parseAlgemeneVerwachting(buienradar);
			
			short diffDays = diffDays(datum);
			String[] googleURLs = mCtx.getResources().getStringArray(R.array.url_xml_google);
			String tagGoogleHuidig = mCtx.getResources().getString(R.string.tag_google_huidig),
				   tagGoogleHuidigTemp = mCtx.getResources().getString(R.string.tag_google_huidig_temp),
				   tagGoogleHuidigIcon = mCtx.getResources().getString(R.string.tag_google_huidig_icon),
				   tagGoogleVerwachting = mCtx.getResources().getString(R.string.tag_google_verwachting),
				   tagGoogleVerwachtingMax = mCtx.getResources().getString(R.string.tag_google_verwachting_max),
				   tagGoogleVerwachtingIcon = mCtx.getResources().getString(R.string.tag_google_verwachting_icon),
				   attrGoogleData = mCtx.getResources().getString(R.string.attr_google_data),
				   urlPrefix = mCtx.getResources().getString(R.string.url_google_icon_prefix);
			for (int i=0; i<TOTAL; i++) {
				Document xml = documentBuilder.parse(googleURLs[i]);
				mHuidig[i] = parseGoogle(xml, tagGoogleHuidig, tagGoogleHuidigTemp, tagGoogleHuidigIcon, attrGoogleData, urlPrefix, 0, false);
				if (diffDays <= MAX_DAYS_AHEAD)
					mVerwachting[i] = parseGoogle(xml, tagGoogleVerwachting, tagGoogleVerwachtingMax, tagGoogleVerwachtingIcon, attrGoogleData, urlPrefix, diffDays, true);
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
	
	public WeerInfo getVerwachting(int plaats) {
		return mVerwachting[plaats];
	}

	public WeerInfo getHuidig(int plaats) {
		return mHuidig[plaats];
	}
	
	public String getAlgemeneVerwachting() {
		return mAlgemeneVerwachting;
	}
	
	public byte getMax() {
		byte result = Byte.MIN_VALUE;
		for (int i=0; i<mVerwachting.length; i++)
			if (mVerwachting[i].getTemp() > result)
				result = mVerwachting[i].getTemp();
		return result;
	}
}