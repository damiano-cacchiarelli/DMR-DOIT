package it.unicam.dmr.doit.utenti;

import java.util.Objects;

public class Utente implements IUtente {

	private final String identificativo;
	private final String nome;
	private final String cognome;
	
	public Utente(String identificativo, String nome, String cognome) {
		Objects.requireNonNull(identificativo, "Identificativo nullo.");
		Objects.requireNonNull(nome, "Nome nullo.");
		Objects.requireNonNull(cognome, "Cognome nullo.");
		
		this.identificativo = identificativo;
		this.nome = nome;
		this.cognome = cognome;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	protected String parametriFormattati() {
		return "identificativo=" + identificativo + ", nome=" + nome + ", cognome=" + cognome;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " ["+ parametriFormattati() +"]";
	}	
}
