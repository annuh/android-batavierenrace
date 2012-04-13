package com.ut.bataapp.weer;

import android.graphics.Bitmap;

/**
 * (Abstracte) klasse voor het representeren van weerinformatie (voor een bepaalde plaats, voor een bepaalde dag).
 * Simpelweg een record met een veld: een grafische beschrijving. 
 * Onderdeel van ontwerpproject BataApp. 
 * @author Danny Bergsma
 * @version 0.1
 */
public abstract class WeerInfo { 
	/* grafische beschrijving 
	 * @invariant mDesc != null 
	 */
	private Bitmap mDesc;
	
	/**
	 * Creeren van representatie van weerinformatie (voor een bepaalde plaats, voor een bepaalde dag).
	 * @param desc grafische beschrijving
	 * @require desc != null
	 * @ensure getDesc() == desc
	 */
	protected WeerInfo(Bitmap desc) {
		this.mDesc = desc;
	}
	
	/**
	 * Geeft grafische beschrijving terug.
	 * @return grafische beschrijving
	 * @ensure result != null
	 */
	public Bitmap getDesc() {
		return mDesc;
	}
}