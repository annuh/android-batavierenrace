package com.ut.bataapp.weer;

import java.util.Date;

/**
 * Interface voor verstrekken van weergegevens.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public interface WeerProvider {
	public static final byte NIJMEGEN = 0, RUURLO = 1, ENSCHEDE = 2;
	public static final byte TOTAL = 3;
	
	public boolean hasData();
	public void refresh(Date datum) throws WeerException;
	public String getAlgemeneVerwachting();	
	public WeerInfo getHuidig(int plaats);
	public WeerInfo getVerwachting(int plaats);
	public byte getMax();
}
