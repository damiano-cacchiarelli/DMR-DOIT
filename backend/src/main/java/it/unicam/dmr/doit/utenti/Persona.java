package it.unicam.dmr.doit.utenti;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Questa classe estende {@code Iscritto} e rappresenta una persona fisica che
 * presenta i campi {@code nome}, {@code cittadinanza}, {@code sesso} e
 * {@code telefono}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Persona extends Iscritto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String nome;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String cognome;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String cittadinanza;
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String sesso;
	@NotNull(message = Utils.nonNullo)
	private String telefono;

	public Persona() {
	}

	public Persona(@NotNull @NotBlank String identificativo, @NotNull @NotBlank String email,
			@NotNull @NotBlank String password, @NotNull @NotBlank String nome, @NotNull @NotBlank String cognome,
			@NotNull @NotBlank String cittadinanza, @NotNull @NotBlank String sesso, @NotNull String telefono) {
		super(identificativo, email, password);
		this.nome = nome;
		this.cognome = cognome;
		this.cittadinanza = cittadinanza;
		this.sesso = sesso;
		this.telefono = telefono;
	}

	// ================================================================================
	// Metodi
	// ================================================================================

	@Override
	public List<TipologiaRuolo> getTipoRuoliPossibili() {
		return List.of(TipologiaRuolo.ROLE_PROGETTISTA, TipologiaRuolo.ROLE_ESPERTO, TipologiaRuolo.ROLE_SPONSOR);
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

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
}
