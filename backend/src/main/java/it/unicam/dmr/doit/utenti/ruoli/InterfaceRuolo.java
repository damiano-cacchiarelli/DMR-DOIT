package it.unicam.dmr.doit.utenti.ruoli;

import java.util.Collection;

import it.unicam.dmr.doit.progetto.Progetto;

/**
 * Questa interfaccia ha la responsabilita' di rappresentare un generico ruolo.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface InterfaceRuolo {
	public TipologiaRuolo getRuolo();

	public Collection<Progetto> getProgettiPersonali();
}
