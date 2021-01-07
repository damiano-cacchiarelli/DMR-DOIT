package it.unicam.dmr.doit.dataTransferObject.progetto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

public class ValutazioneDto {
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String recensione;

	private Set<ValutazioneProgettistaDto> valutazioniCandidati = new HashSet<>();
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private int idProgetto;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String idIscritto;

	public ValutazioneDto() {

	}

	public ValutazioneDto(@NotNull @NotBlank String recensione, Set<ValutazioneProgettistaDto> valutazioniCandidati,
			@NotNull @NotBlank int idProgetto, @NotNull @NotBlank String idIscritto) {
		super();
		this.recensione = recensione;
		this.valutazioniCandidati = valutazioniCandidati;
		this.idProgetto = idProgetto;
		this.idIscritto = idIscritto;
	}

	public String getRecensione() {
		return recensione;
	}

	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}

	public Set<ValutazioneProgettistaDto> getValutazioniCandidati() {
		return valutazioniCandidati;
	}

	public void setValutazioniCandidati(Set<ValutazioneProgettistaDto> valutazioniCandidati) {
		this.valutazioniCandidati = valutazioniCandidati;
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public String getIdIscritto() {
		return idIscritto;
	}
	
	public void setIdIscritto(String idIscritto) {
		this.idIscritto = idIscritto;
	}
}
