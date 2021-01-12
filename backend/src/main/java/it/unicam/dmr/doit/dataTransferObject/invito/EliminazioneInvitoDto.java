package it.unicam.dmr.doit.dataTransferObject.invito;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta un invito che deve esser eliminato.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class EliminazioneInvitoDto {
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String idInvito;
	private boolean opzioni;

	public EliminazioneInvitoDto() {
	}

	public EliminazioneInvitoDto(@NotNull @NotBlank String idInvito, boolean opzioni) {
		super();
		this.idInvito = idInvito;
		this.opzioni = opzioni;
	}

	public String getIdInvito() {
		return idInvito;
	}

	public void setIdInvito(String idInvito) {
		this.idInvito = idInvito;
	}

	public boolean isOpzioni() {
		return opzioni;
	}

	public void setOpzioni(boolean opzioni) {
		this.opzioni = opzioni;
	}
}
