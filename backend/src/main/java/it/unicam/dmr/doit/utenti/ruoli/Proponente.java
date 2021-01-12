package it.unicam.dmr.doit.utenti.ruoli;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import it.unicam.dmr.doit.progetto.Progetto;

/**
 * Questa classe estende {@code Ruolo} e rappresenta il ruolo
 * {@code TipologiaRuolo.ROLE_PROPONENTE}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Proponente extends Ruolo {
		
	@OneToMany(mappedBy = "proponente", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<Progetto> proposte = new HashSet<>();
	
	public Proponente() {
		setRuolo(TipologiaRuolo.ROLE_PROPONENTE);
	}
	
	@Override
	public Set<Progetto> getProgettiPersonali() {
		return proposte;
	}
}