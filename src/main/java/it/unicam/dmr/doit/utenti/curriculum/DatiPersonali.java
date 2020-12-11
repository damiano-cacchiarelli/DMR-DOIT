package it.unicam.dmr.doit.utenti.curriculum;

public class DatiPersonali {

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

	public DatiPersonali(String nome, String cognome, String email, String professione) {
		
		this(nome,cognome, email, professione,"","","","","","");
		
	}

	public DatiPersonali(String nome, String cognome, String email,String professione, String cittadinanza, String sesso, String telefono,
			 String sitoWeb, String gitHub, String linkedIn) {

		this.nome = nome;
		this.cognome = cognome;
		this.cittadinanza = cittadinanza;
		this.email = email;
		this.sesso = sesso;
		this.telefono = telefono;
		this.professione = professione;
		this.sitoWeb = sitoWeb;
		this.gitHub = gitHub;
		this.linkedIn = linkedIn;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getProfessione() {
		return professione;
	}

	public void setProfessione(String professione) {
		this.professione = professione;
	}

	public String getSitoWeb() {
		return sitoWeb;
	}

	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}

	public String getGitHub() {
		return gitHub;
	}

	public void setGitHub(String gitHub) {
		this.gitHub = gitHub;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

}
