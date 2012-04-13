package com.ut.bataapp.weer;

import android.graphics.Bitmap;

/**
 * Klasse voor het representeren van huidige weersituatie voor een bepaalde plaats.
 * Simpelweg een record met twee velden: huidige temperatuur en een grafische beschrijving. 
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerInfoHuidig extends WeerInfo {
	/* huidige temperatuur */
	private byte mTemp;
	
	/**
	 * Creeren van representatie van huidige weersituatie voor een bepaalde plaats.
	 * @param temp huidige temperatuur
	 * @param desc grafische beschrijving
	 * @require desc != null
	 * @ensure getDesc() == desc && getTemp() == temp
	 */
	WeerInfoHuidig(byte temp, Bitmap desc) {
		super(desc);
		mTemp = temp;
	}
	
	/**
	 * Geeft huidige temperatuur terug.
	 * @return huidige temperatuur
	 */
	public byte getTemp() {
		return mTemp;
	}
}
