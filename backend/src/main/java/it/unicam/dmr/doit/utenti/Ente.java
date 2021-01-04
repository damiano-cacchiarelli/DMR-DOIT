package it.unicam.dmr.doit.utenti;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@Entity
public class Ente extends Iscritto {

	@NotNull
	@NotBlank
	private String sede;
	@NotNull
	private Date annoDiFondazione;

	public Ente() {
	}

	public Ente(String identificativo, @NotNull @NotBlank String email, @NotNull @NotBlank String password,
			@NotNull @NotBlank String sede, @NotNull Date annoDiFondazione) {
		super(identificativo, email, password);
		this.sede = sede;
		this.annoDiFondazione = annoDiFondazione;
	}

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
	
	@Override
	public List<TipologiaRuolo> getTipoRuoliPossibili() {
		return List.of(TipologiaRuolo.PROPONENTE, TipologiaRuolo.PROGETTISTA, TipologiaRuolo.SPONSOR);
	}
}