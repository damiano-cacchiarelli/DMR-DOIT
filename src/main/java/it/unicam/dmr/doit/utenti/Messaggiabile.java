package it.unicam.dmr.doit.utenti;

import it.unicam.doit.invito.GestoreMessaggi;
import it.unicam.doit.invito.Messaggio;

public interface Messaggiabile<M extends Messaggio> {

	public GestoreMessaggi<M> getGestoreMessaggi();
}
