package it.unicam.dmr.doit.progetto;

import java.util.Set;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;

/**
 * Questa interfaccia ha la responsabilita' di rappresentare un generico
 * progetto, e come tale deve avere un id, nome, stato, fase e tags.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface IProgetto {

	public int getId();

	public String getNome();

	public Stato getStato();

	public Fase getFase();

	public void nextFase() throws NextFaseException;

	Set<Tag> getTags();

}
