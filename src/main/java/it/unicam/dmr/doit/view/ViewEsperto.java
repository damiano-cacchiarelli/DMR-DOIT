package it.unicam.dmr.doit.view;

import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerEsperto;
import it.unicam.dmr.doit.progetto.Progetto;

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
		//DA RIMPIAZZARE System.out.println(progetto.getDettagli());
		System.out.println(progetto.toString());
		//if(progetto.getCandidati().size() != 0) {
			System.out.println("Voi valutare i candidati del progetto?Y/N");
			String res = s.nextLine();
			if(res.equalsIgnoreCase("Y")) {
				System.out.println("Valuta Progettista non disponibile.");
			}	
		//}else System.out.println("Nessun progettista si è candidato al progetto.");
		System.out.println("Inserire una recensione: ");
		String recensione = s.nextLine();
		esperto.rilasciaValutazione(recensione, progetto);
	}
}