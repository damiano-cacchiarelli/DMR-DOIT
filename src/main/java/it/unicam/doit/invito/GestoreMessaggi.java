package it.unicam.doit.invito;

import java.util.List;
import java.util.function.Predicate;

import it.unicam.doit.progetto.Progetto;

public interface GestoreMessaggi<M extends Messaggio> /*extends GestoreId*/{
	
	public List<M> getMessaggi(Predicate<? super M> filtro);
	
	public void riceviMessaggio(M messaggio);
	
	public void eliminaMessaggio(int idMessaggio);
	
	public void inviaMessaggio(GestoreMessaggi<? super M> destinatario, String contenuto, Progetto progetto, TipologiaInvito tipologiaInvito);

	public String getId();
}
