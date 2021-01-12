package it.unicam.dmr.doit.utenti;

import it.unicam.dmr.doit.invito.Invito;

/**
 * Questa interfaccia rappresenta un generico utente che presenta un
 * {@code identificativo} unvoco ed una {@code password} associata.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface Utente extends Messaggiabile<Invito> {

	public String getIdentificativo();

	public String getEmail();

}
