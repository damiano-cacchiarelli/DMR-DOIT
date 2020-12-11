package it.unicam.dmr.doit.utenti;

import it.unicam.dmr.doit.invito.GestoreMessaggi;
import it.unicam.dmr.doit.invito.Messaggio;

public interface Messaggiabile<M extends Messaggio> {

	public GestoreMessaggi<M> getGestoreMessaggi();
}
