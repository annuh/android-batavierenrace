package com.ut.bataapp.parser;

import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;

import com.ut.bataapp.objects.Looptijd;

public class EtappeUitslagHandler extends DefaultHandler{

	private ArrayList<Looptijd> uitslagen;
	
	public ArrayList<Looptijd> getParsedData() {
		return uitslagen;
	}

}
