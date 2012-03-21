package com.ut.bataapp.objects;

public class MenuItemInfo {
	
	private int locationidentifier;
	private int imageidentifier;
	private String title;
	
	public MenuItemInfo(int locationidentifier, int imageidentifier, String title){
		
		this.locationidentifier = locationidentifier;
		this.imageidentifier = imageidentifier;
		this.title = title;
		
	}
	
	public int getLocationIdentifier(){
		return this.locationidentifier;
	}
	
	public int getImageIdentifier(){
		return this.imageidentifier;
	}
	
	public String getTitle(){
		return this.title;
	}

}
