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

import it.unicam.dmr.doit.utenti.Iscritto;

@Entity
@Table(name = "ruolo")
//TODO: @Inheritance strategia da modificare: crea una tabella per tutti i ruoli (?)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ruolo implements IRuolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipologiaRuolo ruolo;

	// 	@JsonBackReference è usato per impedire il ciclo infinito con la classe iscritto
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "iscritto_identificativo", nullable = false)
	private Iscritto iscritto;

	public Ruolo() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setIscritto(Iscritto iscritto) {
		this.iscritto = iscritto;
	}

	public Iscritto getIscritto() {
		return iscritto;
	}

	@Override
	public TipologiaRuolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(TipologiaRuolo ruolo) {
		this.ruolo = ruolo;
	}
	
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
}
