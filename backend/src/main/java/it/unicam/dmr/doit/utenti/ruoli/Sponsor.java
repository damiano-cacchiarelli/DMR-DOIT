package it.unicam.dmr.doit.utenti.ruoli;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import it.unicam.dmr.doit.progetto.Progetto;

/**
 * Questa classe estende {@code Ruolo} e rappresenta il ruolo
 * {@code TipologiaRuolo.ROLE_SPONSOR}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Sponsor extends Ruolo {
	
	@Transient
	private List<Progetto> progettiFinanziati = new LinkedList<>();
	
	public Sponsor() {
		setRuolo(TipologiaRuolo.ROLE_SPONSOR);
	}
	
	@Override
	public Collection<Progetto> getProgettiPersonali() {
		return progettiFinanziati;
	}
}

