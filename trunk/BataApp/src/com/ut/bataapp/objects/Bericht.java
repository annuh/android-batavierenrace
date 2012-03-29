package com.ut.bataapp.objects;

public class Bericht {
	
	public static final int GROEN = 2;
	public static final int GEEL = 1;
	public static final int ROOD = 0;
	
	private String id = "";
	private int code;
	private String afzender = "";
	private String titel = "";
	private String bericht = "";
	private String datum = "";
	
	public Bericht(String id, int code, String afzender, String titel, String bericht, String datum){
		
		this.id = id;
		this.code = code;
		this.afzender = afzender;
		this.titel = titel;
		this.bericht = bericht;
		this.datum = datum;
		
	}
	
	public String getId(){ return this.id; }
	public int getCode(){ return this.code; }	
	public String getAfzender(){ return this.afzender; }	
	public String getTitel(){ return this.titel; }	
	public String getBericht(){ return this.bericht; }	
	public String getDatum(){ return this.datum; }
	

}
