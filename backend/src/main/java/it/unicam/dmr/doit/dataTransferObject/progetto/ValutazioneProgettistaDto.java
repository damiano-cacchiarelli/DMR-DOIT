package it.unicam.dmr.doit.dataTransferObject.progetto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

public class ValutazioneProgettistaDto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String recensione;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String identificativoProgettista;

	public ValutazioneProgettistaDto() {

	}

	public ValutazioneProgettistaDto(@NotNull @NotBlank String recensione,
			@NotNull @NotBlank String identificativoProgettista) {
		super();
		this.recensione = recensione;
		this.identificativoProgettista = identificativoProgettista;
	}

	public String getRecensione() {
		return recensione;
	}

	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}

	public String getIdentificativoProgettista() {
		return identificativoProgettista;
	}

	public void setIdentificativoProgettista(String identificativoProgettista) {
		this.identificativoProgettista = identificativoProgettista;
	}

}
