package it.unicam.dmr.doit.dataTransferObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IscrittoDto {

	@NotNull
	@NotBlank
	private String identificativo;

	@NotNull
	@NotBlank
	private String email;

	@NotNull
	@NotBlank
	private String password;

	public IscrittoDto() {}
	
	public IscrittoDto(@NotNull @NotBlank String identificativo, @NotNull @NotBlank String email, @NotNull @NotBlank String password) {
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
