package it.unicam.dmr.doit.progetto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ValutazioneProgettista implements IValutazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@NotBlank
	private String recensione;
	@NotNull
	@NotBlank
	private String identificativoProgettista;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "valutazione_progetto", nullable = false)
	private Valutazione valutazioneProgetto;
	
	public ValutazioneProgettista() {}

	public ValutazioneProgettista(@NotNull @NotBlank String recensione,
			@NotNull @NotBlank String identificativoProgettista) {
		super();
		this.recensione = recensione;
		this.identificativoProgettista = identificativoProgettista;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentificativoProgettista() {
		return identificativoProgettista;
	}

	public void setIdentificativoProgettista(String identificativoProgettista) {
		this.identificativoProgettista = identificativoProgettista;
	}

	public Valutazione getValutazioneProgetto() {
		return valutazioneProgetto;
	}

	public void setValutazioneProgetto(Valutazione valutazioneProgetto) {
		this.valutazioneProgetto = valutazioneProgetto;
	}

	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}

	@Override
	public String getRecensione() {
		return recensione;
	}

}
