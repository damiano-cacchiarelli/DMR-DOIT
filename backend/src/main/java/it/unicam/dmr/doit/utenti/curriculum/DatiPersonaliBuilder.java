package it.unicam.dmr.doit.utenti.curriculum;

/*
 * DA ELIMINARE - Esempio di builder
 */
public class DatiPersonaliBuilder {

	private String nome;
	private String cognome;
	private String cittadinanza;
	private String email;
	private String sesso;
	private String telefono;
	private String professione;
	private String sitoWeb;
	private String gitHub;
	private String linkedIn;


	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setProfessione(String professione) {
		this.professione = professione;
	}

	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}

	public void setGitHub(String gitHub) {
		this.gitHub = gitHub;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	
	public DatiPersonali getResult() {
		return new DatiPersonali(nome, cognome, cittadinanza, email, sesso, telefono, professione, sitoWeb, gitHub, linkedIn);
	}

}
