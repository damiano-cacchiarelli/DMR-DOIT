package it.unicam.dmr.doit.view;

import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerProgettista;

public class ViewProgettista {

	private ControllerProgettista progettista;
	
	public ViewProgettista(ControllerProgettista progettista) {
		this.progettista = progettista;
	}
	
	public void modificaCompetenze(Scanner s) {
		System.out.println(progettista.getCurriculum());
		System.out.println("Vuoi modificare le tue competenze?[Y/N]: ");
		if (s.nextLine().equalsIgnoreCase("y")) {
			System.out.println("Inserisci lingue:");
			String lingue = s.nextLine();
			System.out.println("Inserisci settore:");
			String settore = s.nextLine();
			System.out.println("Vuoi salvare le modifiche?[Y/N]: ");
			if (s.nextLine().equalsIgnoreCase("y")) {
				try {
					progettista.competenzeAggiornate(lingue, settore);
					System.out.println("Competenze aggiornate!");
				} catch (Exception e) {
					System.err.println("Aggiornamento competenze fallito: " + e.getMessage());
				}
			}
		}
		System.out.println("Vuoi modificare i tui dati personali?[Y/N]: ");
		if (s.nextLine().equalsIgnoreCase("y")) {
			System.out.println("Inserisci nome:");
			String nome = s.nextLine();
			System.out.println("Inserisci cognome:");
			String cognome = s.nextLine();
			System.out.println("Vuoi salvare le modifiche?[Y/N]: ");
			if (s.nextLine().equalsIgnoreCase("y")) {
				try {
					progettista.datiPersonaliAggiornati(nome, cognome);
					System.out.println("Dati personali aggiornati!");
				} catch (Exception e) {
					System.err.println("Aggiornamento dati pernsonali fallito: " + e.getMessage());
				}
			}
		}
	}
	
	public void gestisciRichieste(Scanner s) {
		System.out.println("Visualizza richieste");
		if(progettista.listaInviti().size() == 0) {
			System.out.println("Nessun invito ricevuto.");
		}else {
			progettista.listaInviti().forEach(i -> System.out.print("id "+i.getId()+" nome progetto "+i.getProgetto().getNome()+" | "));
			System.out.println("\nInserire l'id dell'invito da visualizzare");	
			int idInvito = Integer.parseInt(s.nextLine());
			System.out.println(progettista.getInvito(idInvito).getInformazioni());
			
			System.out.println("Inserire A per accettare e R per rifiutare l'invito.");
			String res = s.nextLine();
			if(res.equalsIgnoreCase("A")) {
				progettista.accettaRichiesta(idInvito);
			}else {
				progettista.eliminaRichiesta(idInvito);
			}
		}
	}
	
	public void candidatiAlProgetto(Scanner s) {
		System.out.println("Progetti: ");
		progettista.getProgettiDisponibiliPerCandidatura().forEach(p -> System.out.println("id"+p.getId()+" nome "+p.getNome()+" | "));
		System.out.println("Inserire id progetto a cui candidarsi");
		int idProgetto = Integer.parseInt(s.nextLine());
		System.out.println("Inserire un messaggio da inviare al proponente del progetto");
		String contenuto = s.nextLine();
		progettista.candidatiAlProgetto(idProgetto, contenuto);
	}
}
