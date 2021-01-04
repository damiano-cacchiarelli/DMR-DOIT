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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.unicam.dmr.doit.invito.InvitoId.RuoloSoggetto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Iscritto;

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
	
	@NotNull
	private String contenuto;
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date data;
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipologiaInvito tipologiaInvito;
	@NotNull
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

	@Transient
	private Progetto progetto;

	public Invito() {}

	public Invito(@NotNull String id, @NotNull String contenuto, @NotNull TipologiaInvito tipologiaInvito, Iscritto mittente,
			Iscritto destinatario, Progetto progetto) {
		this.id = id;
		this.contenuto = contenuto;
		this.tipologiaInvito = tipologiaInvito;
		this.mittente = mittente;
		this.destinatario = destinatario;
		this.progetto = progetto;
	}
	
	public void setSoggetto(RuoloSoggetto soggetto) {
		this.soggetto = soggetto;
	}
	
	public RuoloSoggetto getSoggetto() {
		return soggetto;
	}

	public void setMittente(Iscritto mittente) {
		this.mittente = mittente;
	}

	public void setDestinatario(Iscritto destinatario) {
		this.destinatario = destinatario;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setTipologiaInvito(TipologiaInvito tipologiaInvito) {
		this.tipologiaInvito = tipologiaInvito;
	}

	public TipologiaRisposta getTipologiaRisposta() {
		return tipologiaRisposta;
	}

	public void setTipologiaRisposta(TipologiaRisposta tipologiaRisposta) {
		this.tipologiaRisposta = tipologiaRisposta;
	}

	public Iscritto getMittente() {
		return mittente;
	}

	public Iscritto getDestinatario() {
		return destinatario;
	}

	public Progetto getProgetto() {
		return progetto;
	}

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

	@Override
	public String getInformazioni() {
		return toString();
	}

	public String getIdMittente() {
		return mittente.getIdentificativo();
	}
	
	public String getIdDestinatario() {
		return destinatario.getIdentificativo();
	}
	
	@Override
	public String toString() {
		return "Invito [mittente=" + mittente.getIdentificativo() + ", destinatario=" + destinatario.getIdentificativo() + ", progetto=" + progetto
				+ ", contenuto=" + contenuto + ", id=" + id + ", data=" + data + ", tipologiaInvito=" + tipologiaInvito
				+ ", tipologiaRisposta=" + tipologiaRisposta + "]";
	}

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
}
