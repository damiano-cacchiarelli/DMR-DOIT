package it.unicam.dmr.doit.controller;

import java.util.List;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.utenti.Esperto;

public class ControllerEsperto implements IController {

	private final Doit doit;
	private final Esperto esperto;

	public ControllerEsperto(Doit doit, Esperto esperto) {
		this.doit = doit;
		this.esperto = esperto;
	}

	public List<Invito> richiesteDiValutazione() {
		return esperto.getRichiesteValutazione();
	}
	
	public Progetto getProgetto(int idInvito) {
		Invito i = esperto.getGestoreMessaggi().getMessaggio(idInvito);
		return i.getProgetto();
	}

	public Progetto selezionaProgetto(int idProgetto) {
		return doit.getVetrina().getProgetto(idProgetto);
	}
	
	public void rilasciaValutazione(String recensione, Progetto p) {
		p.setValutazione(new Valutazione(recensione));
		p.setStato(Stato.VALUTATO);
	}
	
	@Override
	public String toString() {
		return esperto.toString();
	}
}
