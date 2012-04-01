package com.ut.bataapp.weer;

import java.util.Date;

/**
 * Interface voor verstrekken van weergegevens.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public interface WeerProvider {
	public static final int NIJMEGEN = 0, GROENLO = 1, ENSCHEDE = 2;
	
	public String getVerwachting() throws WeerException;	
	public WeerInfo getHuidig(int loc) throws WeerException;
	public WeerInfo getVerwachting(Date date) throws WeerException;
}
