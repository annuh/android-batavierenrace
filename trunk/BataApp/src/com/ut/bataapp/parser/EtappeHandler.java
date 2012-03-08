package com.ut.bataapp.parser;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.ut.bataapp.objects.Etappe;

//RECOMMIT AANPASSING VOOR BAS
public class EtappeHandler extends DefaultHandler{

	private boolean etappe;
	private boolean afstand;
	private boolean type;
	private boolean ploeg;
	private boolean tijd;
	
	private ArrayList<Etappe> etappes;
	
	public ArrayList<Etappe> getParsedData(){
		return etappes;
	}
	
	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equals("etappe")) this.etappe = true;
		else if(localName.equals("afstand")) this.afstand = true;
		else if(localName.equals("type")) this.type = true;
		else if(localName.equals("ploeg")) this.ploeg = true;
		else if(localName.equals("tijd")) this.tijd = true;
	}
	
	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
		if(localName.equals("Etappe")) this.etappe = false;
		else if(localName.equals("afstand")) this.afstand = false;
		else if(localName.equals("type")) this.type = false;
		else if(localName.equals("ploeg")) this.ploeg = false;
		else if(localName.equals("tijd")) this.tijd = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length){
		if(etappe){
			etappes.add(new Etappe(etappes.size()+1,0,'D',"0","0",null,null,null,null));
			etappe = false;
		}
		if(tijd) etappes.get(etappes.size()-1).setRecordTijd(new String(ch,start,length));
		else if(ploeg) etappes.get(etappes.size()-1).setRecordTeam(new String(ch,start,length));
		else if(type) etappes.get(etappes.size()-1).setGeslacht(ch[0]);
		else if(afstand) etappes.get(etappes.size()-1).setAfstand(Integer.parseInt(new String(ch,start,length)));
	}
	
	@Override
	public void startDocument() throws SAXException{
		this.etappes = new ArrayList<Etappe>();
	}
}