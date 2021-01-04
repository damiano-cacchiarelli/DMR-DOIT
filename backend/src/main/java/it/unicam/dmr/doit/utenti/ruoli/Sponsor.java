package it.unicam.dmr.doit.utenti.ruoli;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import it.unicam.dmr.doit.progetto.Progetto;

@Entity
public class Sponsor extends Ruolo {
	
	/*ManyToMany con progetto*/
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

