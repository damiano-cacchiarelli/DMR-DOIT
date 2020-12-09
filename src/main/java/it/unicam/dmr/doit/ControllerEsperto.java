package it.unicam.dmr.doit;

import java.util.List;

import it.unicam.dmr.doit.utenti.Esperto;
import it.unicam.doit.invito.Invito;
import it.unicam.doit.invito.TipologiaInvito;
import it.unicam.doit.progetto.Progetto;
import it.unicam.doit.progetto.Stato;
import it.unicam.doit.progetto.Valutazione;

public class ControllerEsperto {

	private final Doit doit;
	private final Esperto esperto;

	public ControllerEsperto(Doit doit, Esperto esperto) {
		this.doit = doit;
		this.esperto = esperto;
	}

	public List<Invito> valutaProgetto() {
		return esperto.getGestoreMessaggi().getMessaggi(i -> i.getTipologiaInvito() == TipologiaInvito.VALUTAZIONE);
		//return esperto.getProgettiDaValutare();
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
