package it.unicam.dmr.doit.progetto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe rappresenta una categoria (o etochetta) e come tale presenta un
 * nome ed una descrizione.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Tag implements InterfaceTag {

	@Id
	@Column(length = 64)
	private String nome;
	@NotNull(message = Utils.nonNullo)
	private String descrizione;

	@JsonBackReference
	@ManyToMany(mappedBy = "tags")
	private Set<Progetto> progetti = new HashSet<>();

	public Tag() {
	}

	public Tag(@NotNull String nome, String descrizione) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	@Override
	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public Set<Progetto> getProgetti() {
		return progetti;
	}

	// ================================================================================
	// Equals & HashCode
	// ================================================================================

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
