package it.unicam.dmr.doit.dataTransferObject;


import java.util.Date;

import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.progetto.Progetto;

public class InvitoDto {

	@NotNull
	private  String contenuto;
	@NotNull
	private  Date data;
	@NotNull
	private  TipologiaInvito tipologiaInvito;
	@NotNull
	private  String idMittente;
	@NotNull
	private  String idDestinatario;
	//@NotNull
	private  Progetto progetto;
	
	public InvitoDto() {}
	
	public InvitoDto(@NotNull String contenuto, @NotNull Date data, @NotNull TipologiaInvito tipologiaInvito,
			@NotNull String idMittente, @NotNull String idDestinatario,  Progetto progetto) {
		
		this.contenuto = contenuto;
		this.data = data;
		this.tipologiaInvito = tipologiaInvito;
		this.idMittente = idMittente;
		this.idDestinatario = idDestinatario;
		this.progetto = progetto;
	}



	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipologiaInvito getTipologiaInvito() {
		return tipologiaInvito;
	}

	public void setTipologiaInvito(TipologiaInvito tipologiaInvito) {
		this.tipologiaInvito = tipologiaInvito;
	}

	public String getIdMittente() {
		return idMittente;
	}

	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}
	
	
}
