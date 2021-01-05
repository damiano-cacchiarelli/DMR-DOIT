package it.unicam.dmr.doit.dataTransferObject.iscritto;


import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.invito.TipologiaInvito;

public class InvitoDto {

	@NotNull
	private  String contenuto;
	@NotNull
	private  Date data;
	@NotNull
	private  TipologiaInvito tipologiaInvito;
	@NotNull
	@NotBlank
	private  String idMittente;
	@NotNull
	@NotBlank
	private  String idDestinatario;
	@NotNull
	@NotBlank
	private  int idProgetto;
	
	public InvitoDto() {}
	
	public InvitoDto(@NotNull String contenuto, @NotNull Date data, @NotNull TipologiaInvito tipologiaInvito,
			@NotNull @NotBlank String idMittente, @NotNull @NotBlank String idDestinatario, @NotNull @NotBlank int idProgetto) {
		
		this.contenuto = contenuto;
		this.data = data;
		this.tipologiaInvito = tipologiaInvito;
		this.idMittente = idMittente;
		this.idDestinatario = idDestinatario;
		this.idProgetto = idProgetto;
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

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public int getIdProgetto() {
		return idProgetto;
	}	
}
