package com.ut.bataapp.weer;

/**
 * Klasse voor weergerelateerde excepties.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerException extends Exception {
	private static final long serialVersionUID = 3067233066673520883L;

	/**
	 * Constructor voor weergerelateerde exceptie, inclusief extra info.
	 * @param msg extra info
	 * @require msg != null
	 */
	public WeerException(String msg) {
		super(msg);
	}
}
