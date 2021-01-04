package it.unicam.dmr.doit.controller.Delete;
import java.util.List;
import java.util.Objects;

import javax.management.OperationsException;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.progetto.Fase;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.utenti.Messaggiabile;
import it.unicam.dmr.doit.utenti.ruoli.Esperto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;

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
		//Progetto p = new Progetto(proponente, id, nome, obbiettivi, requisiti);
		//doit.getVetrina().salvaPropostaProgetto(p);
	}
	
	public void permetteValutazioneProgetto(int idProgetto, String idEsperto, String contenuto) throws OperationsException {
		//precondizioni
		Progetto progetto = doit.getVetrina().getProgetto(idProgetto);
		if(progetto.getFase() == Fase.INIZIO && progetto.getStato() != Stato.IN_VALUTAZIONE) {
			progetto.setStato(Stato.IN_VALUTAZIONE);
			//Messaggiabile<Invito> destinatario = doit.getUtenti().getEsperto(idEsperto); 
			//proponente.getGestoreMessaggi().inviaMessaggio(destinatario.getGestoreMessaggi(), contenuto, progetto, TipologiaInvito.VALUTAZIONE);	
		}else throw new OperationsException("Il progetto non può esser valutato per i seguenti motivi: è già in valutazione o non è nella fase iniziale/inizio");
	}
	
	public void invitaProgettista(String idProgettista, int idProgetto, int idProposta, String contenuto) {
		//Progettista progettista = doit.getUtenti().getProgettista(idProgettista);
		Progetto progetto = doit.getVetrina().getProgetto(idProgetto);
		if(!progetto.getGestoreCandidati().progettistaPresente(idProgettista)) {
			//proponente.getGestoreMessaggi().inviaMessaggio(progettista.getGestoreMessaggi(), contenuto, progetto, TipologiaInvito.PROPOSTA);
			//progetto.getGestoreCandidati().aggiungiCandidato(progettista);
		}else throw new IllegalStateException("Il progettista è già presente nel progetto.");
	}
	
	public List<Esperto> getEspertiConsigliati(int idProgetto) {
		return null;//doit.getUtenti().getEspertiConsigliati(idProgetto);
	}
	
	@Override
	public String toString() {
		return proponente.toString();
	}

	public String passaAFaseSuccessivaOperazioni(int idProgetto) throws NextFaseException {
		Progetto p = doit.getVetrina().getProgetto(idProgetto);
		if(p.getFase() == Fase.PUBBLICAZIONE) 
			throw new NextFaseException("Sei nell'ultima fase!");
		return p.getFase().operazioniFase();
	}
	
	public void passaAFaseSuccessiva(int idProgetto) throws NextFaseException {
		Progetto p = doit.getVetrina().getProgetto(idProgetto);
		p.nextFase();
	}
}
