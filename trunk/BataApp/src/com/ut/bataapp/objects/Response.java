package com.ut.bataapp.objects;

public class Response<T>{

	public final static int OK_UPDATE = 1;
	public final static int OK_NO_UPDATE = 2;
	public final static int NOK_NO_DATA = 3;
	public final static int NOK_OLD_DATA = 4;
	
	private T response;
	private int status;
	
	public Response(T response, int status){
		this.response = response;
		this.status = status;
	}
	
	public T getResponse(){
		return response;
	}
	
	public int getStatus(){
		return status;
	}
		
}
