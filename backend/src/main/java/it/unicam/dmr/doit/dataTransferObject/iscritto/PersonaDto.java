package it.unicam.dmr.doit.dataTransferObject.iscritto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete, estende
 * la classe {@code IscrittoDto} e rappresenta una {@code Persona}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class PersonaDto extends IscrittoDto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String nome;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String cognome;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String cittadinanza;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String sesso;
	private String telefono;

	public PersonaDto() {
		super();
	}

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