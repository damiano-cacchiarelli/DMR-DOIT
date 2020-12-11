package it.unicam.dmr.doit.controller;
import java.util.Objects;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.PropostaDiPartecipazione;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Messaggiabile;
import it.unicam.dmr.doit.utenti.Proponente;

public class ControllerProponente implements IController {
	
	private final Doit doit;
	private Proponente proponente;
	
	public ControllerProponente(Doit doit, Proponente proponente) {
		Objects.requireNonNull(doit, "Non e' possibile utilizzare un sistema nullo.");
		Objects.requireNonNull(proponente, "Non e' possibile utilizzare un progettista nullo.");
		this.doit = doit;
		this.proponente = proponente;
	}
	
	public void compilaProposta(int id, String nome, String obbiettivi, String requisiti) {
		Progetto p = new Progetto(id, nome, obbiettivi, requisiti);
		doit.getVetrina().salvaPropostaProgetto(p);
	}
	
	public void permetteValutazioneProgetto(int idProgetto, String idEsperto, String contenuto) {
		Messaggiabile<Invito> destinatario = doit.getUtenti().getEsperto(idEsperto); 
		Progetto progetto = doit.getVetrina().getProgetto(idProgetto);
		proponente.getGestoreMessaggi().inviaMessaggio(destinatario.getGestoreMessaggi(), contenuto, progetto, TipologiaInvito.VALUTAZIONE);
	}
	
	/*
	public void permetteValutazioneProgetto(String idEsperto, int idProgetto) {
		Esperto esperto = doit.getEsperto(idEsperto);
		Progetto p = doit.getVetrina().getProgetto(idProgetto);
		p.setStato(Stato.IN_VALUTAZIONE);
		esperto.riceviRichiestaValutazione(idProgetto);			
	}*/
	
	public void invitaProgettista(String idProgettista, int idProgetto, int idProposta, String contenuto) {
		PropostaDiPartecipazione pp = new PropostaDiPartecipazione(idProposta, idProgettista, contenuto, idProgetto);
		//doit.getUtenti().getProgettista(idProgettista).riceviProposte(pp);
	}
	
	@Override
	public String toString() {
		return proponente.toString();
	}
}
