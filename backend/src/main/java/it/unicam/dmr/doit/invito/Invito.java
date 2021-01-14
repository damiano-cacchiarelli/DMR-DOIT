package it.unicam.dmr.doit.invito;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Questa classe rappresenta un invito, cioè un messaggio inviato tra due
 * iscritti che ha una {@code TipologiaInvito}. Ogni invito fa riferimento ad un
 * {@code Progetto} ed ha un {@code Iscritto - Mittente} ed un
 * {@code Iscritto - Destinatario}. Inoltre ogni Invito ha una
 * {@code TipologiaRisposta}, che indica se l'invito e' accettato, rifiutato o
 * in attesa di visualizzazione.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
@IdClass(InvitoId.class)
public class Invito implements Messaggio {

	@Id
	@Column(length = 36)
	private String id;
	@Id
	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private RuoloSoggetto soggetto = RuoloSoggetto.MITTENTE;

	@NotNull(message = Utils.nonNullo)
	private String contenuto;
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date data;
	@NotNull(message = Utils.nonNullo)
	@Enumerated(EnumType.STRING)
	private TipologiaInvito tipologiaInvito;
	@NotNull(message = Utils.nonNullo)
	@Enumerated(EnumType.STRING)
	private TipologiaRisposta tipologiaRisposta = TipologiaRisposta.IN_ATTESA;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_iscritto_mittente", nullable = false)
	private Iscritto mittente;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_iscritto_destinatario", nullable = false)
	private Iscritto destinatario;

	@NotNull(message = Utils.nonNullo)
	private int idProgetto;

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String nomeProgetto;

	public Invito() {
	}

	public Invito(@NotNull String id, @NotNull String contenuto, @NotNull TipologiaInvito tipologiaInvito,
			Iscritto mittente, Iscritto destinatario, int idProgetto, String nomeProgetto) {
		this.id = id;
		this.contenuto = contenuto;
		this.tipologiaInvito = tipologiaInvito;
		this.mittente = mittente;
		this.destinatario = destinatario;
		this.idProgetto = idProgetto;
		this.nomeProgetto = nomeProgetto;
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public RuoloSoggetto getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(RuoloSoggetto soggetto) {
		this.soggetto = soggetto;
	}

	public TipologiaRisposta getTipologiaRisposta() {
		return tipologiaRisposta;
	}

	public void setTipologiaRisposta(TipologiaRisposta tipologiaRisposta) throws IllegalArgumentException {
		if (tipologiaRisposta.equals(TipologiaRisposta.IN_ATTESA))
			throw new IllegalArgumentException("La risposta inviata non e' valida");
		if (!this.tipologiaRisposta.equals(TipologiaRisposta.IN_ATTESA))
			throw new IllegalArgumentException(
					"L'invito e' gia' stato accettato/rifiutato. Non e' possibile modificare la risposta.");
		this.tipologiaRisposta = tipologiaRisposta;
	}

	@Override
	public Iscritto getMittente() {
		return mittente;
	}

	@Override
	public Iscritto getDestinatario() {
		return destinatario;
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	public String getNomeProgetto() {
		return nomeProgetto;
	}

	@Override
	public String getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public TipologiaInvito getTipologiaInvito() {
		return tipologiaInvito;
	}

	public String getContenuto() {
		return contenuto;
	}

	public String getIdMittente() {
		return mittente.getIdentificativo();
	}

	public String getIdDestinatario() {
		return destinatario.getIdentificativo();
	}

	// ================================================================================
	// Equals & HashCode & ToString
	// ================================================================================

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((soggetto == null) ? 0 : soggetto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Invito))
			return false;
		Invito other = (Invito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (soggetto != other.soggetto)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Invito [mittente=" + mittente.getIdentificativo() + ", destinatario=" + destinatario.getIdentificativo()
				+ ", progetto=" + idProgetto + " " + nomeProgetto + ", contenuto=" + contenuto + ", id=" + id
				+ ", data=" + data + ", tipologiaInvito=" + tipologiaInvito + ", tipologiaRisposta=" + tipologiaRisposta
				+ "]";
	}
}
