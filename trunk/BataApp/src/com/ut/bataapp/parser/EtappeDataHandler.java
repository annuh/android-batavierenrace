package com.ut.bataapp.parser;

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

import com.ut.bataapp.R;

public class EtappeDataHandler extends DefaultHandler{
	
	private Context mContext;
	
	private boolean etappe;
	private boolean id;
	private boolean limitTijd;
	private boolean universiteitLimitTijd;
	private boolean open;
	private boolean uitersteStartTijd;
	
	public EtappeDataHandler(Context context){
		mContext = context;
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName,Attributes atts) throws SAXException{
		if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_etappe)));
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException{
		
	}
	
	@Override
	public void startDocument() throws SAXException{
	}
}
