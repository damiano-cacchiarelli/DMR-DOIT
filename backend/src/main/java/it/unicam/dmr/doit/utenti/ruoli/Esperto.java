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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Valutazione;

@Entity
public class Esperto extends Ruolo {

	@NotNull
	private int rango;
	/*
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "proposte", joinColumns = @JoinColumn(name = "id_iscritto"), inverseJoinColumns = @JoinColumn(name = "id_progetto"))
	private Set<Progetto> progettiValutati = new HashSet<>();*/
	@NotNull
	@JsonManagedReference
	@OneToMany(mappedBy = "esperto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Valutazione> valutazioniEffettuate = new HashSet<>();

	public Esperto() {
		setRuolo(TipologiaRuolo.ROLE_ESPERTO);
	}

	public Esperto(@NotNull int rango) {
		this.rango = rango;
		setRuolo(TipologiaRuolo.ROLE_ESPERTO);
	}

	public int getRango() {
		return rango;
	}

	public void setRango(int rango) {
		this.rango = rango;
	}

	/*public void setProgettiValutati(Set<Progetto> progettiValutati) {
		this.progettiValutati = progettiValutati;
	}*/
	
	@JsonIgnore
	@Override
	public Collection<Progetto> getProgettiPersonali() {
		return valutazioniEffettuate.stream().map(v -> v.getProgetto()).collect(Collectors.toList());//progettiValutati;
	}

	public Set<Valutazione> getValutazioniEffettuate() {
		return valutazioniEffettuate;
	}

	public void setValutazioniEffettuate(Set<Valutazione> valutazioniEffettuate) {
		this.valutazioniEffettuate = valutazioniEffettuate;
	}
	
}