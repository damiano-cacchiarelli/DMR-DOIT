package it.unicam.dmr.doit.dataTransferObject.invito;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.invito.TipologiaRisposta;

public class RispostaInvitoDto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String idInvito;
	@NotNull(message = Utils.nonNullo)
	private TipologiaRisposta risposta;

	public RispostaInvitoDto() {
	}

	public RispostaInvitoDto(@NotNull @NotBlank String idInvito, @NotNull TipologiaRisposta risposta) {
		super();
		this.idInvito = idInvito;
		this.risposta = risposta;
	}

	public String getIdInvito() {
		return idInvito;
	}

	public void setIdInvito(String idInvito) {
		this.idInvito = idInvito;
	}

	public TipologiaRisposta getRisposta() {
		return risposta;
	}

	public void setRisposta(TipologiaRisposta risposta) {
		this.risposta = risposta;
	}
}
