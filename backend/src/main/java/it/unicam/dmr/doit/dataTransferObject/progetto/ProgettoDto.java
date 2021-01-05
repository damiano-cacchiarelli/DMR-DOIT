package it.unicam.dmr.doit.dataTransferObject.progetto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProgettoDto {
	
	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String obiettivi;
	@NotNull
	@NotBlank
	private String requisiti;
	
	private Set<TagDto> tags = new HashSet<>();
	@NotNull
	@NotBlank
	private String idIscritto;	
	
	public ProgettoDto() {}
	
	public ProgettoDto(@NotNull @NotBlank String nome, @NotNull @NotBlank String obiettivi,
			@NotNull @NotBlank String requisiti, Set<TagDto> tags, @NotNull @NotBlank String idIscritto) {
		super();
		this.nome = nome;
		this.obiettivi = obiettivi;
		this.requisiti = requisiti;
		this.tags = tags;
		this.idIscritto =idIscritto;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObiettivi() {
		return obiettivi;
	}

	public void setObiettivi(String obiettivi) {
		this.obiettivi = obiettivi;
	}

	public String getRequisiti() {
		return requisiti;
	}

	public void setRequisiti(String requisiti) {
		this.requisiti = requisiti;
	}

	public Set<TagDto> getTags() {
		return tags;
	}

	public void setTags(Set<TagDto> tags) {
		this.tags = tags;
	}
	
	public String getIdIscritto() {
		return idIscritto;
	}
	
	public void setIdIscritto(String idIscritto) {
		this.idIscritto = idIscritto;
	}
	
}
