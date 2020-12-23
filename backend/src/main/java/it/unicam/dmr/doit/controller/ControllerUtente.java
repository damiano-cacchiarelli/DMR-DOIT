package it.unicam.dmr.doit.controller;

import java.util.List;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Utente;

public class ControllerUtente {

	private final Doit doit;
	private final Utente utente;

	public ControllerUtente(Doit doit, Utente utente) {
		this.doit = doit;
		this.utente = utente;
	}
	
	public List<Progetto> ricercaProgetto(String nome){
		return doit.getVetrina().cercaProgetto(nome);
	}

	public Progetto selezionaProgetto(int idProgetto) {
		return doit.getVetrina().getProgetto(idProgetto); 
	}
}
