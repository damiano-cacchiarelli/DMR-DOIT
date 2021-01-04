package it.unicam.dmr.doit.progetto;

import java.util.Set;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;

public interface IProgetto {

	public int getId();

	public String getNome();
	
	public Stato getStato();
	
	public Fase getFase();
	
	public void nextFase() throws NextFaseException;

	Set<Tag> getTags();

}
