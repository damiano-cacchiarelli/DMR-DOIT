package it.unicam.dmr.doit.utenti.ruoli;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.progetto.Progetto;

@Entity
public class Esperto extends Ruolo {

	@NotNull
	private int rango;

	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "proposte", joinColumns = @JoinColumn(name = "id_iscritto"), inverseJoinColumns = @JoinColumn(name = "id_progetto"))
	private Set<Progetto> progettiValutati = new HashSet<>();

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

	public void setProgettiValutati(Set<Progetto> progettiValutati) {
		this.progettiValutati = progettiValutati;
	}

	@Override
	public Collection<Progetto> getProgettiPersonali() {
		return progettiValutati;
	}
}
