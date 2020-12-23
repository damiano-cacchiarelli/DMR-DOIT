package it.unicam.dmr.doit.view;

import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerEsperto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Progettista;

public class ViewEsperto {
	
	private ControllerEsperto esperto;
	
	public ViewEsperto(ControllerEsperto esperto) {
		this.esperto = esperto;
	}
	
	public void valutaProgetto(Scanner s) {
		System.out.println("Valuta un progetto. Inviti disponibili: " + esperto.richiesteDiValutazione().toString());
		System.out.println("Inserire l'id dell'invito.");
		int idInvito = Integer.parseInt(s.nextLine());
		Progetto progetto = esperto.getProgetto(idInvito);
		System.out.println(progetto.getDettagli());
		if(progetto.getGestoreCandidati().getCandidati().size() != 0) {
			System.out.println("Voi valutare i candidati del progetto?Y/N");
			String res = s.nextLine();
			if(res.equalsIgnoreCase("Y")) {
				progetto.getGestoreCandidati().getCandidati().forEach(p -> valutaProgettista(p));
			}	
		}else System.out.println("Nessun progettista si è candidato al progetto.");
		System.out.println("Inserire una recensione: ");
		String recensione = s.nextLine();
		esperto.rilasciaValutazione(recensione, progetto);
	}
	
	public void valutaProgettista(Progettista p) {
		System.out.println("Valuta progettista non disponibile!");
	}
}