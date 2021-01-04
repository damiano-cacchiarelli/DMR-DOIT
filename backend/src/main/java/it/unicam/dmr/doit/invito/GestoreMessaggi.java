package it.unicam.dmr.doit.invito;

import java.util.List;
import java.util.function.Predicate;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Iscritto;

public interface GestoreMessaggi<M extends Messaggio> /* extends GestoreId */ {

	public List<M> getMessaggi(Predicate<? super M> filtro);

	public void riceviMessaggio(M messaggio);

	public void eliminaMessaggio(String idMessaggio);

	public void eliminaMessaggio(String idMessaggio, boolean entrambi);

	public void inviaMessaggio(Iscritto destinatario, String contenuto, Progetto progetto,
			TipologiaInvito tipologiaInvito);

	public void inviaMessaggio(Iscritto destinatario, M messaggio);

	public M getMessaggio(String idMessaggio);

	public Iscritto getIscritto();
}
