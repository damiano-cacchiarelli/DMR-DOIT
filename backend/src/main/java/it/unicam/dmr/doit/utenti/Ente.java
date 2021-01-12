package it.unicam.dmr.doit.utenti;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Questa classe estende {@code Iscritto} e rappresenta un ente (azienda) che
 * presenta i campi {@code annoDiFondazione} e {@code sede}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Ente extends Iscritto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String sede;
	@NotNull(message = Utils.nonNullo)
	private Date annoDiFondazione;

	public Ente() {
	}

	public Ente(@NotNull @NotBlank String identificativo, @Email String email, @NotNull @NotBlank String password,
			@NotNull @NotBlank String sede, @NotNull Date annoDiFondazione) {
		super(identificativo, email, password);
		this.sede = sede;
		this.annoDiFondazione = annoDiFondazione;
	}

	// ================================================================================
	// Metodi
	// ================================================================================

	@Override
	public List<TipologiaRuolo> getTipoRuoliPossibili() {
		return List.of(TipologiaRuolo.ROLE_PROPONENTE, TipologiaRuolo.ROLE_PROGETTISTA, TipologiaRuolo.ROLE_SPONSOR);
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public Date getAnnoDiFondazione() {
		return annoDiFondazione;
	}

	public void setAnnoDiFondazione(Date annoDiFondazione) {
		this.annoDiFondazione = annoDiFondazione;
	}
}