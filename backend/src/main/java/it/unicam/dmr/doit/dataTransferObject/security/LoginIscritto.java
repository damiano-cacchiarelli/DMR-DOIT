package it.unicam.dmr.doit.dataTransferObject.security;

import javax.validation.constraints.NotBlank;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta un iscritto che deve essere autenticato.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class LoginIscritto {
	@NotBlank(message = Utils.nonVuoto)
	private String identificativo;
	@NotBlank(message = Utils.nonVuoto)
	private String password;
	
	public LoginIscritto() { }
	
	public LoginIscritto(String identificativo, String password) {
		this.identificativo = identificativo;
		this.password = password;
	}

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
