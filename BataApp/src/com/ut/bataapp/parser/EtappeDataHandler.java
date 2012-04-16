package com.ut.bataapp.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

import com.ut.bataapp.R;
import com.ut.bataapp.objects.Etappe;

public class EtappeDataHandler extends DefaultHandler{
	
	private Context mContext;
	private Etappe etappe;
	private boolean gotEtappe;
	
	private boolean id;
	private boolean limitTijd;
	private boolean universiteitLimitTijd;
	private boolean open;
	private boolean uitersteStartTijd;
	
	public EtappeDataHandler(Context context,Etappe etappe){
		mContext = context;
		this.etappe = etappe;
	}
	
	public Etappe getParsedData(){
		return this.etappe;
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName,Attributes atts) throws SAXException{
		if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_id))) this.id = true;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_limiettijd))) this.limitTijd = true;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_uinversiteitlimiettijd))) this.universiteitLimitTijd = true;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_open))) this.open = true;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_uiterstestarttijd))) this.uitersteStartTijd = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_id))) this.id = false;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_limiettijd))) this.limitTijd = false;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_uinversiteitlimiettijd))) this.universiteitLimitTijd = false;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_open))) this.open = false;
		else if(localName.equals(mContext.getResources().getString(R.string.tag_etappeinfo_uiterstestarttijd))) this.uitersteStartTijd = false;
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException{
		if(id){
			gotEtappe = etappe.getId()==Integer.parseInt(new String(ch,start,length));
			}
		if(gotEtappe){
			if(this.limitTijd) etappe.setLimeitTijd(new String(ch,start,length));
			else if(this.universiteitLimitTijd) etappe.setUniversteitsLimietTijd(new String(ch,start,length));
			else if(this.open) etappe.setOpenTijd(new String(ch,start,length));
			else if(this.uitersteStartTijd) etappe.setUitersteStartTijd(new String(ch,start,length));
		}
	}
}
