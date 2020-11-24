package it.unicam.dmr.doit;

import java.util.Scanner;

public class ViewProgettista {

	private ControllerProgettista progettista;
	
	public ViewProgettista(ControllerProgettista progettista) {
		this.progettista = progettista;
	}
	
	public void modificaCompetenze(Scanner s) {
		System.out.println(progettista.getCurriculum());
		System.out.println("Vuoi modificare le tue competenze?[Y/N]: ");
		if (s.nextLine().equalsIgnoreCase("y")) {
			System.out.println("Inserisci nuove competenze:");
			String competenze = s.nextLine();
			System.out.println("Vuoi salvare le modifiche?[Y/N]: ");
			if (s.nextLine().equalsIgnoreCase("y")) {
				try {
					progettista.competenzeAggiornate(competenze);
					System.out.println("Competenze aggiornate!");
				} catch (Exception e) {
					System.err.println("Aggiornamento competenze fallito: " + e.getMessage());
				}
			}
		}
		System.out.println("Vuoi modificare i tui dati personali?[Y/N]: ");
		if (s.nextLine().equalsIgnoreCase("y")) {
			System.out.println("Inserisci nuovi dati personali:");
			String datiPersonali = s.nextLine();
			System.out.println("Vuoi salvare le modifiche?[Y/N]: ");
			if (s.nextLine().equalsIgnoreCase("y")) {
				try {
					progettista.datiPersonaliAggiornati(datiPersonali);
					System.out.println("Dati personali aggiornati!");
				} catch (Exception e) {
					System.err.println("Aggiornamento dati pernsonali fallito: " + e.getMessage());
				}
			}
		}
	}
}
