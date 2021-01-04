package it.unicam.dmr.doit.dataTransferObject.security;

import javax.validation.constraints.NotBlank;

public class LoginIscritto {
	@NotBlank
	private String identificativo;
	@NotBlank
	private String password;

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
