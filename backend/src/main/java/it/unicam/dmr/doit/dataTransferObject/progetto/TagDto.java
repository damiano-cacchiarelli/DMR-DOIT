package it.unicam.dmr.doit.dataTransferObject.progetto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TagDto {

	@NotNull
	@NotBlank
	private String nome;
	@NotNull
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
