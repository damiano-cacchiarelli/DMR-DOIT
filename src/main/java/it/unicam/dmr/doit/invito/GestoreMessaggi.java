package it.unicam.dmr.doit.invito;

import java.util.List;
import java.util.function.Predicate;

import it.unicam.dmr.doit.progetto.Progetto;

public interface GestoreMessaggi<M extends Messaggio> /*extends GestoreId*/{
	
	public List<M> getMessaggi(Predicate<? super M> filtro);
	
	public void riceviMessaggio(M messaggio);
	
	public void eliminaMessaggio(int idMessaggio);
	
	public void inviaMessaggio(GestoreMessaggi<? super M> destinatario, String contenuto, Progetto progetto, TipologiaInvito tipologiaInvito);

	public M getMessaggio(int idMessaggio);
	
	public String getId();
}
