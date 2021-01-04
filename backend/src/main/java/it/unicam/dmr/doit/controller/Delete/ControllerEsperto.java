package it.unicam.dmr.doit.controller.Delete;

import java.util.List;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.utenti.ruoli.Esperto;

public class ControllerEsperto implements IController {

	private final Doit doit;
	private final Esperto esperto;

	public ControllerEsperto(Doit doit, Esperto esperto) {
		this.doit = doit;
		this.esperto = esperto;
	}

	public List<Invito> richiesteDiValutazione() {
		return null;//esperto.getRichiesteValutazione();
	}
	
	public Progetto getProgetto(int idInvito) {
		//TODO Invito i = esperto.getGestoreMessaggi().getMessaggio(idInvito);
		return null;//i.getProgetto();
	}

	public Progetto selezionaProgetto(int idProgetto) {
		return doit.getVetrina().getProgetto(idProgetto);
	}
	
	public void rilasciaValutazione(String recensione, Progetto p) {
		//p.aggiungiValutazione(new Valutazione(recensione));
		p.setStato(Stato.VALUTATO);
	}
	
	@Override
	public String toString() {
		return esperto.toString();
	}
}
