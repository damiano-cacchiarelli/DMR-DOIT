package it.unicam.dmr.doit;

import java.util.Scanner;

import it.unicam.doit.progetto.Progetto;

public class ViewEsperto {
	
	private ControllerEsperto esperto;
	
	public ViewEsperto(ControllerEsperto esperto) {
		this.esperto = esperto;
	}
	
	public void valutaProgetto(Scanner s) {
		System.out.println("Valuta un progetto. Progetti disponibili: " + esperto.valutaProgetto().toString());
		System.out.println("Inserire l'id del progetto da valutare: ");
		int idProgetto = Integer.parseInt(s.nextLine());
		Progetto progetto = esperto.selezionaProgetto(idProgetto);
		System.out.println(progetto.toString());
		System.out.println("Inserire una recensione: ");
		String recensione = s.nextLine();
		esperto.rilasciaValutazione(recensione, progetto);
	}
}