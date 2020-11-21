package it.unicam.dmr.doit;
import java.util.Scanner;

import java.util.Objects;

public class IProponente {
	
	private final Doit doit;
	private Proponente proponente;
	
	public IProponente(Doit doit, Proponente proponente) {
		Objects.requireNonNull(doit, "Non e' possibile utilizzare un siatema nullo.");
		Objects.requireNonNull(proponente, "Non e' possibile utilizzare un progettista nullo.");
		this.doit = doit;
		this.proponente = proponente;
	}

	public void invitaProgettista(int idProgetto) {
		String idP = selezionaProgettista();
		PropostaDiPartecipazione pp = creaProposta(idP, idProgetto);
		doit.getProgettista(idP).riceviProposte(pp);
	}
	

	private PropostaDiPartecipazione creaProposta(String idProgettista, int idProgetto) {
		Scanner s = new Scanner(System.in);
		System.out.println("Inserisci id proposta: ");
		int idProposta = s.nextInt();
		System.out.println("Inserisci contenuto proposta: ");
		String contenuto = s.nextLine();
		s.close();
		return new PropostaDiPartecipazione(idProposta, idProgettista, contenuto, idProgetto);
	}

	public String selezionaProgettista() {
		Scanner s = new Scanner(System.in);
		System.out.println("Inserisci id Proponente: ");
		String idP = s.nextLine();
		s.close();
		return idP;
	}
	
	public void permetteValutazioneProgetto(int idProgetto) {
		Esperto e = doit.getEsperto(selezionaEsperto());
		Progetto p = doit.getVetrina().getProgetto(idProgetto);
		p.setStato(Stato.IN_VALUTAZIONE);
		e.riceviRichiestaValutazione(idProgetto);
		System.out.println("Richiesta di valutazione inviata");
		
	}

	private String selezionaEsperto() {
		Scanner s = new Scanner(System.in);
		System.out.println("Inserisci id Esperto: ");
		String idE = s.nextLine();
		s.close();
		return idE;	
	}
	
	public void proporreProgetto() {
		System.out.println("Proponi un progetto!");
		Scanner s = new Scanner(System.in);
		System.out.println("Inserisci id progetto: ");
		int id = s.nextInt();
		System.out.println("Inserisci nome progetto: ");
		String nome = s.nextLine();
		System.out.println("Inserisci obiettivi progetto: ");
		String obiettivi = s.nextLine();
		System.out.println("Inserisci requisiti progetto: ");
		String requisiti = s.nextLine();
		System.out.println("Si vuole far valutare il progetto?[Y/N] ");
		String r = s.nextLine();
		Progetto p = compilaProposta(id, nome, obiettivi, requisiti);
		doit.getVetrina().salvaPropostaProgetto(p);
		if(r == "Y") {
			permetteValutazioneProgetto(id);
		}else {
			while(true) {
				System.out.println("Si vuole invitare un progettista?[Y/N] ");
				String r1 = s.nextLine();
				if(r1 == "Y") {
					invitaProgettista(id);
				}else {
					break;
				}
			}
		}
		s.close();
		System.out.println("Proposta salvata!");
	}
	
	private Progetto compilaProposta(int id,String nome, String obiettivi, String requisiti) {
		//TODO vari controlli
		return new Progetto(id, nome, obiettivi, requisiti);
	}

	/*
	private void visualizzaProgettisti() {
		System.out.println("Progettisti: \n");
		System.out.println("   > *PROGETTISTA*");
	}
	
	private void visualizzaEsperti() {
		System.out.println("Esperti: \n");
		System.out.println("   > *ESPERTO*");
	}
*/
}
