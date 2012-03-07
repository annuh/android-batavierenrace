package com.ut.bataapp.objects;

public class Bericht {
	
	private String code = "";
	private String afzender = "";
	private String bericht = "";
	
	public Bericht(String code, String afzender, String bericht){
		
		this.code = code;
		this.afzender = afzender;
		this.bericht = bericht;
		
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getAfzender(){
		return this.afzender;
	}
	
	public String getBericht(){
		return this.bericht;
	}

}
