package com.ut.bataapp.parser;

import java.util.ArrayList;
import com.ut.bataapp.objects.Looptijd;

public class EtappeUitslagHandler extends Handler{

	private ArrayList<Looptijd> uitslagen;
	
	public EtappeUitslagHandler(String path) {
		super(path);
	}

	public ArrayList<Looptijd> getParsedData() {
		return uitslagen;
	}

}
