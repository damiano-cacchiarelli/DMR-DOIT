package it.unicam.dmr.doit;
import java.util.ArrayList;
import java.util.Objects;

public class GestoreRichiesteProposte {
	
	ArrayList<PropostaDiPartecipazione> listaRichieste = new ArrayList<>();
	ArrayList<PropostaDiPartecipazione> listaProposte = new ArrayList<>();
	boolean on = true;
	
	//T utente;
	
	public GestoreRichiesteProposte() {
		//Objects.requireNonNull(utente, "Non e' possibile assegnare un gestore ad un utente nullo.");
		//this.utente = utente;
	}
	
	public ArrayList<PropostaDiPartecipazione> getListaRichieste() {
		return listaRichieste;
	}
	public ArrayList<PropostaDiPartecipazione> getListaProposte() {
		return listaProposte;
	}
	
	public void riceviProposta(PropostaDiPartecipazione pp) {
		Objects.requireNonNull(pp, "Non e' possibile inserire una proposta null.");
		if (on) {
			listaProposte.add(pp);
			System.out.println("Richiesta ricevuta");
		}else {
			System.out.println("Non è possibile inviare richieste a questo mittente");
		}
		
	}
	
	public <T extends Utente> void inviaProposta(PropostaDiPartecipazione pp, T utente) {
		Objects.requireNonNull(pp, "Non e' possibile inserire una proposta null.");
		Objects.requireNonNull(utente, "Il gestore e' null.");
		utente.riceviProposte(pp);
		
	}
	
	
}
