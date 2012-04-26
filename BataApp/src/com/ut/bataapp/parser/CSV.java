package com.ut.bataapp.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.util.Log;

import com.ut.bataapp.objects.EtappeRoute;

public class CSV {
	
	public EtappeRoute parse(InputStream csv){
	
		Scanner scanner = new Scanner(csv,"ISO-8859-1");
		String TekstVoorTabel = "";
		boolean tabelBegonnen = false;
		String newLine = "";
		
		while(!tabelBegonnen){
			
			newLine = scanner.nextLine();
			Log.i("debugger", newLine);
			if(newLine.equals("Afst;Omschrijving;Locatie")){
				tabelBegonnen = true;
			} else{
				if(!TekstVoorTabel.equals("")) TekstVoorTabel += '\n';
				String[] zin = newLine.split(";");
				TekstVoorTabel += zin[0];
			}
			
		}
		
		boolean tabelEinde = false;
		ArrayList<String[]> tabel = new ArrayList<String[]>();
		String TekstNaTabel = "";
		
		while(!tabelEinde){
			newLine = scanner.nextLine();
			Log.i("debugger", newLine);
			
			String[] rij = newLine.split(";");
			
			if(rij.length == 1 || (rij[1].equals("") && rij[2].equals(""))){
				tabelEinde = true;
				TekstNaTabel += rij[0];
			} else{
				tabel.add(rij);
			}			
			
		}
		
		while(scanner.hasNextLine()){
			newLine = scanner.nextLine();
			if(!TekstNaTabel.equals("")) TekstNaTabel += '\n';
			String[] zin = newLine.split(";");
			TekstNaTabel += zin[0];			
		}
		
		return new EtappeRoute(TekstVoorTabel, tabel, TekstNaTabel);		
		
	}

}
