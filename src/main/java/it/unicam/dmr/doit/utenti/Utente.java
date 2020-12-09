package it.unicam.dmr.doit.utenti;

import java.util.Objects;

import it.unicam.dmr.doit.GestoreRichiesteProposte;
import it.unicam.dmr.doit.PropostaDiPartecipazione;

public class Utente {

	private final String identificativo;
	private final String nome;
	private final String cognome;
	private final GestoreRichiesteProposte grp = new GestoreRichiesteProposte();
	
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
	
	//TODO creare una classe astratta
	public void riceviProposte(PropostaDiPartecipazione pp) {
		grp.riceviProposta(pp);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " ["+ parametriFormattati() +"]";
	}	
}
