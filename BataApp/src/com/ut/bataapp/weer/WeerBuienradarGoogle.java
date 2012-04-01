package com.ut.bataapp.weer;

import java.util.Calendar;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.content.Context;
import android.net.Uri;

import com.ut.bataapp.R;

public class WeerBuienradarGoogle implements WeerProvider {
	public static final int MILLIS_IN_DAY = 60 * 60 * 24 * 1000;
	public static final byte MAX_DAYS_AHEAD = 5;
	
	private Document mBuienradar, mGoogle;
	private Context mCtx;
	
	public WeerBuienradarGoogle(Document buienradar, Document google, Context ctx) {
		mBuienradar = buienradar;
		mGoogle = google;
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
		return (byte) Math.round((f-32) / 1.8);
	}
	
	@Override
	public WeerInfo getVerwachting(Date date) throws WeerException {
		String maxTemp, url;
		
		long diffDays = diffDays(date);
		if (diffDays == 0) {
			String tagGoogleVandaag = mCtx.getResources().getString(R.string.tag_google_vandaag),
			       tagGoogleVandaagHigh = mCtx.getResources().getString(R.string.tag_google_vandaag_high),
			       tagGoogleVandaagIcon = mCtx.getResources().getString(R.string.tag_google_vandaag_icon),
			       attrGoogleVandaagData = mCtx.getResources().getString(R.string.attr_google_vandaag_data);
			Element elemGoogleVandaag = (Element) mGoogle.getElementsByTagName(tagGoogleVandaag).item(0),
					elemGoogleVandaagHigh = (Element) elemGoogleVandaag.getElementsByTagName(tagGoogleVandaagHigh).item(0),
					elemGoogleVandaagIcon = (Element) elemGoogleVandaag.getElementsByTagName(tagGoogleVandaagIcon).item(0);
			maxTemp = (convertFtoC(Byte.parseByte(elemGoogleVandaagHigh.getAttribute(attrGoogleVandaagData))) + "");
			url = mCtx.getResources().getString(R.string.url_google_icon_prefix) + elemGoogleVandaagIcon.getAttribute(attrGoogleVandaagData);
		} else if (diffDays <= MAX_DAYS_AHEAD) {
			String tagVerwachtingMeerdaags = (mCtx.getResources().getString(R.string.tag_verwachting_meerdaags) + diffDays),
			       tagVerwachtingMeerdaagsMax = mCtx.getResources().getString(R.string.tag_verwachting_meerdaags_max),
			       tagVerwachtingMeerdaagsIcon = mCtx.getResources().getString(R.string.tag_verwachting_meerdaags_icon);
			Element elemVerwachtingMeerdaags = (Element) mBuienradar.getElementsByTagName(tagVerwachtingMeerdaags).item(0);
			maxTemp = elemVerwachtingMeerdaags.getElementsByTagName(tagVerwachtingMeerdaagsMax).item(0).getTextContent();
			url = elemVerwachtingMeerdaags.getElementsByTagName(tagVerwachtingMeerdaagsIcon).item(0).getTextContent();
		} else
			throw new WeerException(mCtx.getResources().getString(R.string.error_too_many_days_ahead));
		
		return new WeerInfo(maxTemp, url);
	}

	@Override
	public String getVerwachting() throws WeerException {
		String tagVerwachtingVandaag = mCtx.getResources().getString(R.string.tag_verwachting_vandaag),
			   tagVerwachtingVandaagTekst = mCtx.getResources().getString(R.string.tag_verwachting_vandaag_tekst);
		Element elemVerwachtingVandaag = (Element) mBuienradar.getElementsByTagName(tagVerwachtingVandaag).item(0);
		return elemVerwachtingVandaag.getElementsByTagName(tagVerwachtingVandaagTekst).item(0).getTextContent();
	}

	@Override
	public WeerInfo getHuidig(int loc) throws WeerException {
		String id = "",
			   tagHuidigTemp = mCtx.getResources().getString(R.string.tag_huidig_temp),
			   tagHuidigIcoon = mCtx.getResources().getString(R.string.tag_huidig_icoon);
		
		switch (loc) {
			case NIJMEGEN:	id = mCtx.getResources().getString(R.string.id_nijmegen);
							break;
			case GROENLO:	id = mCtx.getResources().getString(R.string.id_groenlo);
							break;
			case ENSCHEDE:	id = mCtx.getResources().getString(R.string.id_enschede);
							break;
		}		
		
		Element weerstation = mBuienradar.getElementById(id);
		String temp = weerstation.getElementsByTagName(tagHuidigTemp).item(0).getTextContent(),
		       url = weerstation.getElementsByTagName(tagHuidigIcoon).item(0).getTextContent();
		
		return new WeerInfo(temp, url);
	}
}
