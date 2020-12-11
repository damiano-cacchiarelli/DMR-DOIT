package it.unicam.dmr.doit.view;

import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerProponente;

public class ViewProponente {

	private ControllerProponente proponente;
	
	public ViewProponente(ControllerProponente proponente) {
		this.proponente = proponente;
	}

	/** ProponenteView */
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

		System.out.println("Si vuole far valutare il progetto?[Y/N] ");
		if (s.nextLine().equalsIgnoreCase("y")) {
			permetteValutazioneProgetto(s, id);
		} else {
			while (s.hasNext()) {
				System.out.println("Si vuole invitare un progettista?[Y/N] ");
				if (s.nextLine().equalsIgnoreCase("y")) {
					invitaProgettista(s, id);
				} else {
					break;
				}
			}
		}
		System.out.println("Proposta salvata!");
	}

	/** ProponenteView */
	public void permetteValutazioneProgetto(Scanner s, int idProgetto) {
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

	/** ProponenteView */
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
}