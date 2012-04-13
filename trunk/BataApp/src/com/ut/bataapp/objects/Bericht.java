package com.ut.bataapp.objects;

public class Bericht {
	
	public static final int CUSTOM = 3;
	public static final int GROEN = 2;
	public static final int GEEL = 1;
	public static final int ROOD = 0;
	
	private String id = "";
	private int code;
	private String titel = "";
	private String bericht = "";
	private String datum = "";
	
	public Bericht(){
		this("",CUSTOM,"","","");
	}
	public Bericht(String id, int code, String titel, String bericht, String datum){	
		this.id = id;
		this.code = code;
		this.titel = titel;
		this.bericht = bericht;
		this.datum = datum;
	}
	
	public void setId(String id){this.id = id;}
	public void setTitel(String titel){this.titel = titel;}
	public void setBericht(String bericht){this.bericht = bericht;}

	
	public String getId(){ return this.id; }
	public int getCode(){ return this.code; }
	public String getTitel(){ return this.titel; }	
	public String getBericht(){ return this.bericht; }	
	public String getDatum(){ return this.datum; }
	

}
