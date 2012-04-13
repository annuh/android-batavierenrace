package com.ut.bataapp.weer;

import android.graphics.Bitmap;

/**
 * Klasse voor het representeren van verwachte weer voor een bepaalde plaats.
 * Simpelweg een record met drie velden: verwachte minimum- en maximumtemperatuur en een grafische beschrijving. 
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerInfoVerwachting extends WeerInfo {
	/* verwachte minimum- en maximiumtemperatuur */
	private byte mMinTemp, mMaxTemp;
	
	/**
	 * Creeren van representatie van verwachte weer (voor een bepaaalde plaats, voor een bepaalde dag).
	 * @param minTemp minimumtemperatuur
	 * @param maxTemp maximumtemperatuur
	 * @param desc grafische beschrijving
	 * @require desc != null
	 * @ensure getDesc() == desc && getMinTemp() == minTemp && getMaxTemp() == maxTemp
	 */
	WeerInfoVerwachting(byte minTemp, byte maxTemp, Bitmap desc) {
		super(desc);
		mMinTemp = minTemp;
		mMaxTemp = maxTemp;
	}
	
	/**
	 * Geeft maximumtemperatuur terug.
	 * @return maximumtemperatuur
	 */
	public byte getMaxTemp() {
		return mMaxTemp;
	}
	
	/**
	 * Geeft minimumtemperatuur terug.
	 * @return minimumtemperatuur
	 */
	public byte getMinTemp() {
		return mMinTemp;
	}
}
