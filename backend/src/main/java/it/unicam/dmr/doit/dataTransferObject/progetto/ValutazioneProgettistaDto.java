package it.unicam.dmr.doit.dataTransferObject.progetto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta una {@code ValutazioneProgettista}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
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
