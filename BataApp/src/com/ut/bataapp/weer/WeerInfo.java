package com.ut.bataapp.weer;

import android.graphics.Bitmap;

/**
 * Klasse voor het representeren van weerinformatie. Simpelweg een record met twee velden: temperatuur en
 * URL van grafische beschrijving.
 * Onderdeel van ontwerpproject BataApp. 
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerInfo {
	private byte mTemp; 
	private Bitmap mDesc;
	
	WeerInfo(byte temp, Bitmap desc) {
		this.mTemp = temp;
		this.mDesc = desc;
	}
	
	public byte getTemp() {
		return mTemp;
	}
	
	public Bitmap getDesc() {
		return mDesc;
	}
}