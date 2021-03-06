package it.unicam.dmr.doit.dataTransferObject.progetto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.progetto.InterfaceTag;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta un {@code Tag}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class TagDto implements InterfaceTag {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String nome;

	public TagDto() {
	}

	public TagDto(@NotNull @NotBlank String nome) {
		super();
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
