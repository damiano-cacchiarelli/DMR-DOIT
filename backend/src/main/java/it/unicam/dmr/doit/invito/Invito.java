package it.unicam.dmr.doit.invito;

import java.time.LocalDateTime;

import it.unicam.dmr.doit.progetto.Progetto;

public class Invito implements Messaggio {

	// Da cambiare con il gestore degli id
	private static int lastId = 1;
	
	private final String idMittente;
	private final String idDestinatario;
	private final Progetto progetto;
	private final String contenuto;
	private final int id;
	private final LocalDateTime data;
	private final TipologiaInvito tipologiaInvito;
	private TipologiaRisposta tipologiaRisposta;
	
	public Invito(String idMittente, String idDestinatario, String contenuto, Progetto progetto, TipologiaInvito tipologiaInvito) {
		// Controlli
		this.idMittente = idMittente;
		this.idDestinatario = idDestinatario;
		this.progetto = progetto;
		this.tipologiaInvito = tipologiaInvito;
		this.contenuto = contenuto;
		
		this.tipologiaRisposta = TipologiaRisposta.IN_ATTESA;
		this.data = LocalDateTime.now();
		this.id = lastId++;
	}

	public TipologiaRisposta getTipologiaRisposta() {
		return tipologiaRisposta;
	}

	public void setTipologiaRisposta(TipologiaRisposta tipologiaRisposta) {
		this.tipologiaRisposta = tipologiaRisposta;
	}

	public String getIdMittente() {
		return idMittente;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public TipologiaInvito getTipologiaInvito() {
		return tipologiaInvito;
	}
	
	public String getContenuto() {
		return contenuto;
	}

	@Override
	public String getInformazioni() {
		return toString();
	}

	@Override
	public String toString() {
		return "Invito [idMittente=" + idMittente + ", idDestinatario=" + idDestinatario + ", progetto=" + progetto
				+ ", contenuto=" + contenuto + ", id=" + id + ", data=" + data + ", tipologiaInvito=" + tipologiaInvito
				+ ", tipologiaRisposta=" + tipologiaRisposta + "]";
	}
}
