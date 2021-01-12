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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.progetto.Progetto;

/**
 * Questa classe estende {@code Ruolo} e rappresenta il ruolo
 * {@code TipologiaRuolo.ROLE_PROGETTISTA}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Progettista extends Ruolo {
	
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "progettista_progetto", joinColumns = {
			@JoinColumn(name = "progettista_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "progetto_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private Set<Progetto> candidature = new HashSet<>();
	
	public Progettista() {
		setRuolo(TipologiaRuolo.ROLE_PROGETTISTA);
	}	

	@Override
	public Collection<Progetto> getProgettiPersonali() {
		return candidature;
	}
}

