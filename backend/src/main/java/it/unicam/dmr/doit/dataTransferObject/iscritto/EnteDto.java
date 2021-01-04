package it.unicam.dmr.doit.dataTransferObject.iscritto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EnteDto extends IscrittoDto {

	@NotNull
	private String sede;
	@NotNull
	private Date annoDiFondazione;

	public EnteDto() {super();}
	
	public EnteDto(String identificativo, @Email String email, @NotNull @NotBlank String password,
			@NotNull String sede, @NotNull Date annoDiFondazione) {
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