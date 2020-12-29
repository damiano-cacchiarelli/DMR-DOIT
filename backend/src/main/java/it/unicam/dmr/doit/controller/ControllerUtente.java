package it.unicam.dmr.doit.controller;

import java.util.List;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Iscritto;

public class ControllerUtente {

	private final Doit doit;
	private final Iscritto iscritto;

	public ControllerUtente(Doit doit, Iscritto iscritto) {
		this.doit = doit;
		this.iscritto = iscritto;
	}
	
	public List<Progetto> ricercaProgetto(String nome){
		return doit.getVetrina().cercaProgetto(nome);
	}

	public Progetto selezionaProgetto(int idProgetto) {
		return doit.getVetrina().getProgetto(idProgetto); 
	}
}
