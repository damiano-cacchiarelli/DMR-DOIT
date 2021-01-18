package it.unicam.dmr.doit.progetto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe implementa {@code IValutazione} e rappresenta una valutazione
 * di un {@code Progettista} in riferimento ad un {@code Progetto}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class ValutazioneProgettista implements InterfaceValutazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Lob
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String recensione;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String identificativoProgettista;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "valutazione_progetto", nullable = false)
	private Valutazione valutazioneProgetto;

	public ValutazioneProgettista() {
	}

	public ValutazioneProgettista(@NotNull @NotBlank String recensione,
			@NotNull @NotBlank String identificativoProgettista, Valutazione valutazioneProgetto) {
		super();
		this.recensione = recensione;
		this.identificativoProgettista = identificativoProgettista;
		this.valutazioneProgetto = valutazioneProgetto;
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public int getId() {
		return id;
	}

	public String getIdentificativoProgettista() {
		return identificativoProgettista;
	}

	public Valutazione getValutazioneProgetto() {
		return valutazioneProgetto;
	}

	@Override
	public String getRecensione() {
		return recensione;
	}

}
