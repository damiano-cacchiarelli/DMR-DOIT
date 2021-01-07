package it.unicam.dmr.doit.progetto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class Tag {
	
	@Id
	@NotNull
	@NotBlank
	@Column(length = 64)
	private String nome;
	@NotNull
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Progetto> getProgetti() {
		return progetti;
	}

	public void setProgetti(Set<Progetto> progetti) {
		this.progetti = progetti;
	}

}
