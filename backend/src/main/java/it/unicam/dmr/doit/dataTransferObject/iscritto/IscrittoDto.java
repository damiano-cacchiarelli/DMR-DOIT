package it.unicam.dmr.doit.dataTransferObject.iscritto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

public class IscrittoDto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String identificativo;

	@Email(message = Utils.nonValido)
	private String email;

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String password;
	
	public IscrittoDto() {
	}

	public IscrittoDto(@NotNull @NotBlank String identificativo, @Email String email,
			@NotNull @NotBlank String password) {
		super();
		this.identificativo = identificativo;
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public String getPassword() {
		return password;
	}
}
