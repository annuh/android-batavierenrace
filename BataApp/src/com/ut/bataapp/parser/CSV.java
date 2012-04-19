package com.ut.bataapp.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import com.ut.bataapp.objects.EtappeRoute;

public class CSV {
	
	public EtappeRoute parse(InputStream csv){
				
		Scanner scanner = new Scanner(csv);
		
		String firstLine = scanner.nextLine();
		Scanner scannerFirstLine = new Scanner(firstLine);
		int etappeNummer = scannerFirstLine.nextInt();
		
		String TekstVoorTabel = "";
		boolean tabelBegonnen = false;
		String newLine = "";
		
		while(!tabelBegonnen){
			
			newLine = scanner.nextLine();
			if(newLine.equals("Afst;Omschrijving;Locatie")){
				tabelBegonnen = true;
			} else{
				String[] zin = newLine.split(";");
				TekstVoorTabel += zin[0];
			}
			
		}
		
		boolean tabelEinde = false;
		ArrayList<String[]> tabel = new ArrayList<String[]>();
		String TekstNaTabel = "";
		
		while(!tabelEinde){
			newLine = scanner.nextLine();
			
			String[] rij = newLine.split(";");
			
			if(rij[1].equals("") && rij[2].equals("")){
				tabelEinde = true;
				TekstNaTabel += rij[0];
			} else{
				tabel.add(rij);
			}			
			
		}
		
		while(scanner.hasNextLine()){
			newLine = scanner.nextLine();
			String[] zin = newLine.split(";");
			TekstNaTabel += zin[0];			
		}
		
		return new EtappeRoute(etappeNummer, TekstVoorTabel, tabel, TekstNaTabel);
	}

}
