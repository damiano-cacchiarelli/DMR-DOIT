package it.unicam.dmr.doit.utenti;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@Entity
public class Persona extends Iscritto {
	
	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String cognome;
	@NotNull
	private String cittadinanza;
	@NotNull
	private String sesso;
	@NotNull
	private String telefono;
	
	public Persona() {}

	public Persona(String identificativo, @NotNull @NotBlank String email, @NotNull @NotBlank String password, @NotNull @NotBlank String nome, @NotNull @NotBlank String cognome, @NotNull String cittadinanza,
			@NotNull String sesso, @NotNull String telefono) {
		super(identificativo, email, password);
		this.nome = nome;
		this.cognome = cognome;
		this.cittadinanza = cittadinanza;
		this.sesso = sesso;
		this.telefono = telefono;
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
	
	@Override
	public List<TipologiaRuolo> getTipoRuoliPossibili() {
		return List.of(TipologiaRuolo.ROLE_PROGETTISTA, TipologiaRuolo.ROLE_ESPERTO, TipologiaRuolo.ROLE_SPONSOR);
	}
}
