package it.unicam.dmr.doit.dataTransferObject.progetto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta un {@code Progetto}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class ProgettoDto {
	
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String nome;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String obiettivi;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String requisiti;
	
	private Set<TagDto> tags = new HashSet<>();
	
	public ProgettoDto() {}
	
	public ProgettoDto(@NotNull @NotBlank String nome, @NotNull @NotBlank String obiettivi,
			@NotNull @NotBlank String requisiti, Set<TagDto> tags) {
		super();
		this.nome = nome;
		this.obiettivi = obiettivi;
		this.requisiti = requisiti;
		this.tags = tags;
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
}
