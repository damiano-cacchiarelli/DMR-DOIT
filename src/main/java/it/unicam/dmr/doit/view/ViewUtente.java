package it.unicam.dmr.doit.view;

import java.util.List;
import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerUtente;
import it.unicam.dmr.doit.progetto.Progetto;

public class ViewUtente {
	
	private ControllerUtente utente;
	
	public ViewUtente(ControllerUtente utente) {
		this.utente = utente;
	}

	public void ricercaProgetto(Scanner s) {
		System.out.println("Inserire il nome del progetto");
		String nome = s.nextLine();
		List<Progetto> listaProgetti = utente.ricercaProgetto(nome);
		
		if(listaProgetti.size() != 0) {
			System.out.println("Lista dei progetti: ");
			listaProgetti.forEach(p -> System.out.print("id "+ p.getId() +" nome "+p.getNome()+" | "));
			System.out.println("\nInserire (Selezionare) l'id di un progetto: ");
			int idProgetto = Integer.parseInt(s.nextLine());
			Progetto p = utente.selezionaProgetto(idProgetto);
			System.out.println(p.getDettagli());
		}else System.out.println("Progetto non trovato.");
	}
}
