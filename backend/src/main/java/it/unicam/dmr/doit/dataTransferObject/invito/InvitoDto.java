package it.unicam.dmr.doit.dataTransferObject.invito;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.invito.TipologiaInvito;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta un {@code Invito}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class InvitoDto {

	@NotNull(message = Utils.nonNullo)
	private String contenuto;
	@NotNull(message = Utils.nonNullo)
	private TipologiaInvito tipologiaInvito;
	@NotNull(message = Utils.nonNullo)
	@NotEmpty(message = Utils.nonVuoto)
	private List<String> idDestinatario;
	@NotNull(message = Utils.nonNullo)
	private int idProgetto;

	public InvitoDto() {
	}

	public InvitoDto(@NotNull String contenuto, @NotNull TipologiaInvito tipologiaInvito,
			@NotNull @NotEmpty List<String> idDestinatario, @NotNull int idProgetto) {

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

	public List<String> getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(List<String> idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	@Override
	public String toString() {
		return "InvitoDto [contenuto=" + contenuto + ", tipologiaInvito=" + tipologiaInvito + ", idDestinatario="
				+ idDestinatario + ", idProgetto=" + idProgetto + "]";
	}
	
	
}
