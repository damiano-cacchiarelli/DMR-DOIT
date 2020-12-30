package it.unicam.dmr.doit.invito;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import it.unicam.dmr.doit.progetto.Progetto;

@Entity
public class Invito implements Messaggio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  int id;
	@NotNull
	private  String contenuto;
	@Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
	private  Date data;
	@NotNull
	@Enumerated(EnumType.STRING)
	private  TipologiaInvito tipologiaInvito;
	@NotNull
	@Enumerated(EnumType.STRING)
	private  TipologiaRisposta tipologiaRisposta = TipologiaRisposta.IN_ATTESA;
	//@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "identificativo_mittente", nullable = false)
	private  String idMittente;
	//@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "identificativo_destintario", nullable = false)
	private  String idDestinatario;
	@Transient
	private  Progetto progetto;
	
	
	public Invito() {
		
		}
	
	public Invito(@NotNull String contenuto, @NotNull TipologiaInvito tipologiaInvito, String idMittente,
			String idDestinatario, Progetto progetto) {
		
		this.contenuto = contenuto;
		this.tipologiaInvito = tipologiaInvito;
		this.idMittente = idMittente;
		this.idDestinatario = idDestinatario;
		this.progetto = progetto;
		
	}

	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public void setId(int id) {
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

	public String getIdMittente() {
		return idMittente;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public int getId() {
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

	@Override
	public String toString() {
		return "Invito [idMittente=" + idMittente + ", idDestinatario=" + idDestinatario + ", progetto=" + progetto
				+ ", contenuto=" + contenuto + ", id=" + id + ", data=" + data + ", tipologiaInvito=" + tipologiaInvito
				+ ", tipologiaRisposta=" + tipologiaRisposta + "]";
	}
}
