package it.unicam.dmr.doit.dataTransferObject.security;

import javax.validation.constraints.NotBlank;

import it.unicam.dmr.doit.controller.Utils;

public class LoginIscritto {
	@NotBlank(message = Utils.nonVuoto)
	private String identificativo;
	@NotBlank(message = Utils.nonVuoto)
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
