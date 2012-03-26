package com.ut.bataapp.objects;

public class Response {

	public final static int OK_UPDATE = 1;
	public final static int OK_NO_UPDATE = 2;
	public final static int NOK_NO_DATA = 3;
	public final static int NOK_OLD_DATA = 4;
	
	private Object response;
	private int status;
	
	public Response(Object response, int status){
		this.response = response;
		this.status = status;
	}
	
	public Object getResponse(){
		return response;
	}
	
	public int getStatus(){
		return status;
	}
		
}
