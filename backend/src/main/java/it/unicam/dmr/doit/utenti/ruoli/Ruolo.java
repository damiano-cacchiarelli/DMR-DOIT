package it.unicam.dmr.doit.utenti.ruoli;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Questa classe implementa {@code IRuolo} e rappresenta un ruolo (definito da
 * {@code TipologiaRuolo}) di un {@code Iscritto}. Ad ogni ruolo e' associata la
 * lista dei {@code Proigetti} di cui fa parte o ha fatto parte. <br>
 * Per la creazione dei vari ruoli si utilizza il metodo {@code Factory}
 * {@link Ruolo.create(TipologiaRuolo)}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
@Table(name = "ruolo")
//TODO: @Inheritance strategia da modificare: crea una tabella per tutti i ruoli (?)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ruolo implements IRuolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = Utils.nonNullo)
	@Enumerated(EnumType.STRING)
	private TipologiaRuolo ruolo;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "iscritto_identificativo", nullable = false)
	private Iscritto iscritto;

	public Ruolo() {
	}

	// ================================================================================
	// Metodi
	// ================================================================================

	public static Ruolo create(TipologiaRuolo tipologiaRuolo) {
		switch (tipologiaRuolo) {
		case ROLE_PROPONENTE:
			return new Proponente();
		case ROLE_PROGETTISTA:
			return new Progettista();
		case ROLE_ESPERTO:
			return new Esperto();
		case ROLE_SPONSOR:
			return new Sponsor();
		default:
			throw new IllegalArgumentException("Il ruolo '" + tipologiaRuolo + "' non esiste");
		}
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Iscritto getIscritto() {
		return iscritto;
	}
	
	public void setIscritto(Iscritto iscritto) {
		this.iscritto = iscritto;
	}

	@Override
	public TipologiaRuolo getRuolo() {
		return ruolo;
	}
	
	public void setRuolo(TipologiaRuolo ruolo) {
		this.ruolo = ruolo;
	}

	// ================================================================================
	// Equals & HashCode
	// ================================================================================

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Ruolo))
			return false;
		Ruolo other = (Ruolo) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
