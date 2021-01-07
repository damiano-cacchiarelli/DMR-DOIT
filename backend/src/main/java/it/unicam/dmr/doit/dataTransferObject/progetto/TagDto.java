package it.unicam.dmr.doit.dataTransferObject.progetto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

public class TagDto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String nome;
	@NotNull(message = Utils.nonNullo)
	private String descrizione;

	public TagDto() {
	}

	public TagDto(@NotNull @NotBlank String nome, @NotNull String descrizione) {
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

}
