package it.unicam.dmr.doit.utenti.ruoli;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.progetto.Progetto;

@Entity
public class Progettista extends Ruolo {
	
	@NotNull
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "progettista_progetto", joinColumns = {
			@JoinColumn(name = "progettista_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "progetto_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private Set<Progetto> candidature = new HashSet<>();
	
	public Progettista() {
		setRuolo(TipologiaRuolo.ROLE_PROGETTISTA);
	}

	public void setCandidature(Set<Progetto> candidature) {
		this.candidature = candidature;
	}	

	@Override
	public Collection<Progetto> getProgettiPersonali() {
		return candidature;
	}
}

