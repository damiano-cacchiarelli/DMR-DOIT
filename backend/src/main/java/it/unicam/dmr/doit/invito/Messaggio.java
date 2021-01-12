package it.unicam.dmr.doit.invito;

import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Interfaccia che rappresenta un messaggio che ciene inviato tra due
 * {@code Iscritti}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface Messaggio {
	public String getId();
	
	public Iscritto getMittente();

	public Iscritto getDestinatario();
}
