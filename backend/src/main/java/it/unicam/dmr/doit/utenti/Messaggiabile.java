package it.unicam.dmr.doit.utenti;

import it.unicam.dmr.doit.invito.GestoreMessaggi;
import it.unicam.dmr.doit.invito.Messaggio;

/**
 * Questa interfaccia rappresenta un entita' che puo' deve gestire un messaggio
 * {@code M}, quidni ha la capacita' di effettuare le operazioni di un
 * {@code GestoreMessaggi<M>}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 *
 * @param <M> tipo di {@code Messaggio} da gestire
 */
public interface Messaggiabile<M extends Messaggio> {

	public GestoreMessaggi<M> getGestoreMessaggi();
}
