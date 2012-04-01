package com.ut.bataapp.weer;

import android.net.Uri;

/**
 * Klasse voor het representeren van weerinformatie. Simpelweg een record met twee velden: temperatuur en
 * grafische beschrijving.
 * Onderdeel van ontwerpproject BataApp. 
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerInfo {
	private String temp, url;
	
	public WeerInfo(String temp, String url) {
		this.temp = temp;
		this.url = url;
	}
	
	public String getTemp() {
		return temp;
	}
	
	public String getURL() {
		return url;
	}
}