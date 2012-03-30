package com.ut.bataapp.parser;

import java.util.ArrayList;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;

public class EtappeUitslagHandler extends Handler{

	private ArrayList<Looptijd> uitslagen;
	
	public EtappeUitslagHandler(String path) {
		super(path);
	}

	public Response getParsedData() {
		return new Response(uitslagen,this.status);
	}

}
