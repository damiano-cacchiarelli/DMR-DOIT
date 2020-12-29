package it.unicam.dmr.doit.view;

import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerProponente;

public class ViewProponente {

	private ControllerProponente proponente;
	
	public ViewProponente(ControllerProponente proponente) {
		this.proponente = proponente;
	}

	public void proponiProgetto(Scanner s) {
		System.out.println("Proponi un progetto!");
		System.out.println("Inserisci id progetto: ");
		int id = Integer.parseInt(s.nextLine());
		System.out.println("Inserisci nome progetto: ");
		String nome = s.nextLine();
		System.out.println("Inserisci obiettivi progetto: ");
		String obiettivi = s.nextLine();
		System.out.println("Inserisci requisiti progetto: ");
		String requisiti = s.nextLine();
		proponente.compilaProposta(id, nome, obiettivi, requisiti);

		System.out.println("Si vuole invitare un progettista?[Y/N] ");
		while(s.nextLine().equalsIgnoreCase("y")) {
			invitaProgettista(s, id);
			System.out.println("Si vuole invitare un progettista?[Y/N] ");
		}
		
		System.out.println("Si vuole far valutare il progetto?[Y/N] ");
		if (s.nextLine().equalsIgnoreCase("y")) {
			permetteValutazioneProgetto(s, id);
		}
		System.out.println("Proposta salvata!");
	}

	public void permetteValutazioneProgetto(Scanner s, int idProgetto) {
		System.out.println("Esperti consigliati dal sistema");
		//proponente.getEspertiConsigliati(idProgetto).forEach(System.out::println);
		System.out.println("Inserire id esperto: ");
		String idEsperto = s.nextLine();
		System.out.println("Inserire il contenuto dell'invito: ");
		String contenuto = s.nextLine();
		try {
			proponente.permetteValutazioneProgetto(idProgetto, idEsperto, contenuto);
			System.out.println("Richiesta di valutazione inviata.");
		} catch (Exception e) {
			System.err.println("Richiesta di valutazione fallita: " + e.getMessage());
		}
	}

	public void invitaProgettista(Scanner s, int idProgetto) {
		System.out.println("Inserire id progettista: ");
		String idProgettista = s.nextLine();
		System.out.println("Inserisci id proposta: ");
		int idProposta = Integer.parseInt(s.nextLine());
		System.out.println("Inserisci contenuto proposta: ");
		String contenuto = s.nextLine();
		try {
			proponente.invitaProgettista(idProgettista, idProgetto, idProposta, contenuto);
			System.out.println("Richiesta di partecipazione inviata.");
		} catch (Exception e) {
			System.err.println("Richiesta di partecipazione fallita: " + e.getMessage());
		}
	}
	
	public void passaAFaseSuccessiva(Scanner s, int idProgetto) {
		try {
			System.out.println("Operazioni non disponibili nella prossima fase: "+ proponente.passaAFaseSuccessivaOperazioni(idProgetto));
			System.out.println("Vuoi continuare?[Y/N]");
			String res = s.nextLine();
			if(res.equalsIgnoreCase("Y")) {
				proponente.passaAFaseSuccessiva(idProgetto);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
