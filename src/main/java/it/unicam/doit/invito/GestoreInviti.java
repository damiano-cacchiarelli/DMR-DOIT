package it.unicam.doit.invito;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unicam.doit.progetto.Progetto;

public class GestoreInviti implements GestoreMessaggi<Invito> {

	private List<Invito> inviti;
	private final String idProprietario; 
	
	public GestoreInviti(String idProprietario) {
		this.idProprietario = idProprietario;
		inviti = new ArrayList<>();
	}
	
	@Override
	public List<Invito> getMessaggi(Predicate<? super Invito> filtro) {
		return inviti.stream().filter(filtro).collect(Collectors.toList());
	}
	
//	public List<Invito> getInviti(TipologiaInvito tipologiaInvito){
//		return inviti.stream().filter(i -> i.getTipologiaInvito() == tipologiaInvito).collect(Collectors.toList());
//	}
	
	private void salvaInvito(Invito invito) {
		inviti.add(invito);
	}

	@Override
	public void riceviMessaggio(Invito messaggio) {
		//inserisci il nuovo messaggio nella lista dei messaggi non letti?
		salvaInvito(messaggio);
	}
	
	@Override
	public void eliminaMessaggio(int idMessaggio) {
		inviti.removeIf(i -> i.getId() == idMessaggio);		
	}
	
	@Override
	public void inviaMessaggio(GestoreMessaggi<? super Invito> destinatario, String contenuto, Progetto progetto,
			TipologiaInvito tipologiaInvito) {
		Invito invito = new Invito(idProprietario, destinatario.getId(), contenuto, progetto, tipologiaInvito);
		salvaInvito(invito);
		destinatario.riceviMessaggio(invito);
	}

	@Override
	public String getId() {
		return idProprietario;
	}
}
