package it.unicam.dmr.doit.dataTransferObject.security;

/**
 * Questa classe rappresenta il token che deve essere inviato in rete.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class JwtDto {

	private String token;
	
	public JwtDto() {}
	
	public JwtDto(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	
}