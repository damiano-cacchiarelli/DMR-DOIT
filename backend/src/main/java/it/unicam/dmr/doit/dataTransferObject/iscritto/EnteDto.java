package it.unicam.dmr.doit.dataTransferObject.iscritto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete, estende
 * la classe {@code IscrittoDto} e rappresenta un {@code Ente}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class EnteDto extends IscrittoDto {

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String sede;
	@NotNull(message = Utils.nonNullo)
	private Date annoDiFondazione;

	public EnteDto() {
		super();
	}

	public EnteDto(@NotNull @NotBlank String identificativo, @Email String email, @NotNull @NotBlank String password,
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
}