package it.unicam.dmr.doit.dataTransferObject.iscritto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IscrittoDto {

	@NotNull
	@NotBlank
	private String identificativo;

	@Email
	private String email;

	@NotNull
	@NotBlank
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
