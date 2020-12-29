package it.unicam.dmr.doit.utenti;

import it.unicam.dmr.doit.invito.Invito;

public interface Utente extends Messaggiabile<Invito> {
	
	public String getIdentificativo();
	public String getEmail();
	
}
