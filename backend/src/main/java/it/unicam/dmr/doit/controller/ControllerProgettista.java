package it.unicam.dmr.doit.controller;

import java.util.List;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.progetto.Fase;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Progettista;
import it.unicam.dmr.doit.utenti.Proponente;
import it.unicam.dmr.doit.utenti.curriculum.Competenze;
import it.unicam.dmr.doit.utenti.curriculum.Curriculum;
import it.unicam.dmr.doit.utenti.curriculum.DatiPersonali;

public class ControllerProgettista implements IController {
	
	private final Doit doit;
	private final Progettista progettista;
	
	public ControllerProgettista(Doit doit, Progettista progettista) {
		this.doit = doit;
		this.progettista = progettista;
	}
	
	public void competenzeAggiornate(String lingue, String settore) {
		Competenze c = progettista.getCurriculum().getCompetenze();
		c.setLingue(lingue);
		c.setSettore(settore);
	}
	
	public void datiPersonaliAggiornati(String nome, String cognome) {
		DatiPersonali datiPersonali = progettista.getCurriculum().getDatiPersonali();
		datiPersonali.setNome(nome);
		datiPersonali.setCognome(cognome);
	}
	
	public Curriculum getCurriculum() {
		return progettista.getCurriculum();
	}
	
	@Override
	public String toString() {
		return progettista.toString();
	}

	public List<Invito> listaInviti() {
		return progettista.getGestoreMessaggi().getMessaggi(i -> i.getTipologiaInvito() == TipologiaInvito.PROPOSTA);
	}

	public Invito getInvito(int idInvito) {
		return progettista.getGestoreMessaggi().getMessaggio(idInvito);
	}

	public void accettaRichiesta(int idInvito) {
		Invito i = progettista.getGestoreMessaggi().getMessaggio(idInvito);
		i.getProgetto().getGestoreCandidati().confermaCandidato(progettista.getIdentificativo());
		eliminaRichiesta(idInvito);	
	}
	
	public void eliminaRichiesta(int idInvito) {
		progettista.getGestoreMessaggi().eliminaMessaggio(idInvito);
	}

	public List<Progetto> getProgettiDisponibiliPerCandidatura() {
		return doit.getVetrina().getProgetti(p -> p.getFase() == Fase.INIZIO);
	}

	public void candidatiAlProgetto(int idProgetto, String contenuto) {
		Progetto p = doit.getVetrina().getProgetto(idProgetto);
		p.getGestoreCandidati().aggiungiCandidato(progettista);
		Proponente proponente = p.getProponente();
		progettista.getGestoreMessaggi().inviaMessaggio(
				proponente.getGestoreMessaggi(), contenuto, p, TipologiaInvito.RICHIESTA);
	}
}
