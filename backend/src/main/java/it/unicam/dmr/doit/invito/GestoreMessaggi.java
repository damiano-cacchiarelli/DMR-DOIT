package it.unicam.dmr.doit.invito;

import java.util.List;
import java.util.function.Predicate;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Questa interfaccia rappresenta il gestore dei {@code Messaggi} ed ha la
 * responsabilita'  di ottenere tutti i messaggi, inviare, ricevere, eliminare un
 * {@code Messaggio}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface GestoreMessaggi<M extends Messaggio> {

	/**
	 * @param filtro
	 * @return una lista di messaggi che soddisfano il filtro passato.
	 */
	public List<M> getMessaggi(Predicate<? super M> filtro);

	/**
	 * Aggiunge il messaggio alla lista dei messaggi ricevuti.
	 * 
	 * @param messaggio
	 */
	public void riceviMessaggio(M messaggio) throws ExistingElementException;

	/**
	 * Elimina il messaggio che ha l'id corrispondente.
	 * 
	 * @param idMessaggio
	 */
	public void eliminaMessaggio(String idMessaggio);

	/**
	 * Elimina tutti i messaggi che hanno l'id corrispondente. Se si tenta di
	 * eliminare un messaggio ricevuto, il mittente sara' ancora in possesso del
	 * messaggio.
	 * 
	 * @param idMessaggio
	 * @param entrambi    definisce se il messaggio deve essere eliminato da
	 *                    mittente e destinatario. Solo il mittente puo' eliminare
	 *                    per entrambi.
	 */
	public void eliminaMessaggio(String idMessaggio, boolean entrambi);

	/**
	 * Invia un messaggio al destinatario. Il messsaggio viene creato al suo interno.
	 * 
	 * @param destinatario
	 * @param contenuto
	 * @param progetto
	 * @param tipologiaInvito
	 */
	public void inviaMessaggio(Iscritto destinatario, String contenuto, Progetto progetto,
			TipologiaInvito tipologiaInvito) throws ExistingElementException;

	/**
	 * Invia un messaggio al destinatario.
	 * 
	 * @param destinatario
	 * @param messaggio
	 */
	public void inviaMessaggio(Iscritto destinatario, M messaggio) throws ExistingElementException;

	/**
	 * @param idMessaggio
	 * @return il messaggio corrispondente all'id.
	 */
	public M getMessaggio(String idMessaggio);

	/**
	 * @return il proprietario del gestore messaggi.
	 */
	public Iscritto getIscritto();
}
