package it.unicam.dmr.doit.dataTransferObject.invito;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.invito.TipologiaInvito;

public class InvitoDto {

	@NotNull
	private String contenuto;
	@NotNull
	private TipologiaInvito tipologiaInvito;
	@NotNull
	@NotBlank
	private String idDestinatario;
	@NotNull
	private int idProgetto;

	public InvitoDto() {
	}

	public InvitoDto(@NotNull String contenuto, @NotNull TipologiaInvito tipologiaInvito,
			@NotNull @NotBlank String idDestinatario, @NotNull int idProgetto) {

		this.contenuto = contenuto;
		this.tipologiaInvito = tipologiaInvito;
		this.idDestinatario = idDestinatario;
		this.idProgetto = idProgetto;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public TipologiaInvito getTipologiaInvito() {
		return tipologiaInvito;
	}

	public void setTipologiaInvito(TipologiaInvito tipologiaInvito) {
		this.tipologiaInvito = tipologiaInvito;
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
