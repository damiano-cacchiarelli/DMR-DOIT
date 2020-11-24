package it.unicam.dmr.doit;

import java.util.ArrayList;

public class ControllerEsperto {

	private final Doit doit;
	private final Esperto esperto;

	public ControllerEsperto(Doit doit, Esperto esperto) {
		this.doit = doit;
		this.esperto = esperto;
	}

	public ArrayList<Integer> valutaProgetto() {
		return esperto.getProgettiDaValutare();
	}

	public Progetto selezionaProgetto(int idProgetto) {
		return doit.getVetrina().getProgetto(idProgetto);
	}
	
	public void rilasciaValutazione(String recensione, Progetto p) {
		p.setValutazione(new Valutazione(recensione));
		p.setStato(Stato.VALUTATO);
	}
	
	/*
	public void rilasciaValutazione(Valutazione valutazione, Progetto p) {
		p.setValutazione(valutazione);
	}*/
	
	@Override
	public String toString() {
		return esperto.toString();
	}
}
