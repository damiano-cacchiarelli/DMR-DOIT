package it.unicam.dmr.doit.dataTransferObject.iscritto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PersonaDto extends IscrittoDto {

	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String cognome;
	@NotNull
	@NotBlank
	private String cittadinanza;
	@NotNull
	@NotBlank
	private String sesso;
	private String telefono;

	public PersonaDto() {super();}
	
	public PersonaDto(@NotNull @NotBlank String identificativo, @Email String email, @NotNull @NotBlank String password,
			@NotNull @NotBlank String nome, @NotNull @NotBlank String cognome, @NotNull @NotBlank String cittadinanza,
			@NotNull @NotBlank String sesso, String telefono) {
		super(identificativo, email, password);
		this.nome = nome;
		this.cognome = cognome;
		this.cittadinanza = cittadinanza;
		this.sesso = sesso;
		this.telefono = telefono;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}