package it.unicam.dmr.doit.dataTransferObject.progetto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta una {@code Valutazione}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class ValutazioneDto {
	
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String idInvito;
	
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String recensione;

	private Set<ValutazioneProgettistaDto> valutazioniCandidati = new HashSet<>();
	
	@NotNull(message = Utils.nonNullo)
	private int idProgetto;

	public ValutazioneDto() {

	}

	public ValutazioneDto(@NotNull @NotBlank String recensione, Set<ValutazioneProgettistaDto> valutazioniCandidati,
			@NotNull int idProgetto) {
		super();
		this.recensione = recensione;
		this.valutazioniCandidati = valutazioniCandidati;
		this.idProgetto = idProgetto;
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
	
	public String getIdInvito() {
		return idInvito;
	}
	
	public void setIdInvito(String idInvito) {
		this.idInvito = idInvito;
	}

}
