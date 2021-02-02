package it.unicam.dmr.doit.utenti.ruoli;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Valutazione;

/**
 * Questa classe estende {@code Ruolo} e rappresenta il ruolo
 * {@code TipologiaRuolo.ROLE_ESPERTO}. Un esperto ha il {@code rango} che
 * rappresenta la qualita' dell'attivita' di valutazione dei {@code Progetti}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Esperto extends Ruolo {

	@NotNull(message = Utils.nonNullo)
	private int rango;

	@NotNull(message = Utils.nonNullo)
	@JsonManagedReference
	@OneToMany(mappedBy = "esperto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Valutazione> valutazioniEffettuate = new HashSet<>();

	public Esperto() {
		setRuolo(TipologiaRuolo.ROLE_ESPERTO);
	}

	public Esperto(@NotNull int rango) {
		this.rango = rango;
		setRuolo(TipologiaRuolo.ROLE_ESPERTO);
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public int getRango() {
		return rango;
	}

	public void setRango(int rango) {
		this.rango = rango;
	}

	//@JsonIgnore
	@Override
	public Collection<Progetto> getProgettiPersonali() {
		Collection<Progetto> progettiValutati =valutazioniEffettuate.stream().map(v -> v.getProgetto()).collect(Collectors.toList());
		return progettiValutati;
	}

	public Set<Valutazione> getValutazioniEffettuate() {
		return valutazioniEffettuate;
	}

}