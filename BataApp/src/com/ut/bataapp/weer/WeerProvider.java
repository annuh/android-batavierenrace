package com.ut.bataapp.weer;

import java.util.Date;

/**
 * Interface voor verstrekken van weergegevens: huidige weergegevens en verwachting voor plaatsen,
 * plus tekstuele verwachting.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public interface WeerProvider {
	/** Constanten voor plaatsen */
	public static final byte NIJMEGEN = 0, RUURLO = 1, ENSCHEDE = 2;
	/** Totaal aantal plaatsen */
	public static final byte TOTAL = 3;
	
	/** 
	 * Geeft terug of deze weerprovider data heeft. Preconditie van diverse methodes.
	 * @return of deze weerprovider data heeft 
	 */
	public boolean hasData();
	
	/**
	 * Vernieuwt de data voor deze weerprovider: huidige weer en het verwachte weer op datum.
	 * @param datum de datum waarvan het verwachte weer moet worden opgehaald
	 * @require datum != null
	 * @ensure hasData()
	 * @throws WeerException wanneer de data niet vernieuwd kan worden, dan ook !hasData()
	 */
	public void refresh(Date datum) throws WeerException;
	
	/**
	 * Geeft de algemene (tekstuele) verwachting terug.
	 * @return algemene (tekstuele) verwachting
	 * @require hasData()
	 * @ensure result != null
	 */
	public String getAlgemeneVerwachting();
	
	/**
	 * Geeft de huidige weersituatie in plaats.
	 * @param plaats de plaats waarvan de huidige weersituatie bepaald moet worden
	 * @return huidige weersituatie in plaats
	 * @require hasData() && (plaats == NIJMEGEN || plaats == RUURLO || plaats = ENSCHEDE)
	 * @ensure result != null
	 */
	public WeerInfoHuidig getHuidig(int plaats);
	
	/**
	 * Geeft de verwachting terug voor plaats voor de bij refresh() opgegeven datum of null wanneer 
	 * er voor de bij refresh() opgegeven datum (nog) geen verwachting is.
	 * @param plaats de plaats waarvan de huidige weerverwachting bepaald moet worden
	 * @return de verwachting voor plaats voor de bij refresh() opgegeven datum of null wanneer er 
	 *         voor de bij refresh() opgegeven datum (nog) geen verwachting is
	 * @require hasData() && (plaats == NIJMEGEN || plaats == RUURLO || plaats = ENSCHEDE)
	 */
	public WeerInfoVerwachting getVerwachting(int plaats);
	
	/**
	 * Geeft de verwachte maximumtemperatuur terug voor alle plaatsen voor de bij refresh() opgegeven 
	 * datum of Byte.MIN_VALUE wanneer er voor de bij refresh() opgegeven datum (nog) geen verwachting is.
	 * @return de verwachte maximumtemperatuur voor alle plaatsen voor de bij refresh() opgegeven datum of 
	 *         Byte.MIN_VALUE wanneer er voor de bij refresh() opgegeven datum (nog) geen verwachting is
	 * @require hasData()
	 */
	public byte getMax();
}
