package it.unicam.dmr.doit.progetto;

import java.util.LinkedList;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.utenti.Proponente;

@Entity
public class Progetto implements IProgetto {
	
	@Id 
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	private String obiettivi;
	private String requisiti;

	@Transient
	private LinkedList<Valutazione> listaValutazioni = new LinkedList<Valutazione>();
	/*
	@Transient
	private Stato stato;
	@Transient
	private Fase fase;
	@Transient
	private GestoreCandidatiProgetto gcp = new GestoreCandidatiProgetto();
	@Transient
	private Proponente proponente;
*/
	public Progetto() {
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Progetto(Proponente proponente, int id, String nome) {
		this(proponente, id, nome, "obiettivi", "requisiti");
	}

	public Progetto(Proponente proponente, int id, String nome, String obiettivi, String requisiti) {
		this.setObiettivi(obiettivi);
		this.setRequisiti(requisiti);
		verificaStringa(nome, "Nome");
		//this.proponente = proponente;
		this.id = id;
		this.nome = nome;
		//this.valutazione = new LinkedList<>();
		this.setStato(Stato.NON_VALUTATO);
		this.setFase(Fase.INIZIO);
	}


	public String getObiettivi() {
		return obiettivi;
	}

	public void setObiettivi(String obiettivi) {
		verificaStringa(obiettivi, "Obiettivi");

		this.obiettivi = obiettivi;
	}

	public String getRequisiti() {
		return requisiti;
	}

	public void setRequisiti(String requisiti) {
		verificaStringa(requisiti, "Requisiti");

		this.requisiti = requisiti;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Valutazione getValutazione() {
		return null; //valutazione.peekLast();
	}
	
	
    
	public LinkedList<Valutazione> getListaValutazioni() {
		return listaValutazioni;
	}

	public void setListaValutazioni(LinkedList<Valutazione> listaValutazioni) {
		this.listaValutazioni = listaValutazioni;
	}

	public void aggiungiValutazione(Valutazione valutazione) {
		Objects.requireNonNull(valutazione, "La valutazione inserita e' nulla");

		//this.valutazione.add(valutazione);
	}

	public Stato getStato() {
		return null; //stato;
	}

	public void setStato(Stato stato) {
		Objects.requireNonNull(stato, "Lo stato inserito e' nullo");

		//this.stato = stato;
	}
	
	private void setFase(Fase fase) {
		//this.fase = fase;
	}
	
	public Fase getFase() {
		return null; //fase;
	}
	
	public void nextFase() throws NextFaseException {
		//this.setFase(fase.nextFase());
	}
	
	public GestoreCandidatiProgetto getGestoreCandidati() {
		return null; // gcp;
	}

	public Proponente getProponente() {
		return null; // proponente;
	}
	
	private void verificaStringa(String s, String campo) {
		Objects.requireNonNull(s, "Il campo " + campo + " inserito e' nullo");

		if (s.trim().length() == 0) {
			throw new IllegalArgumentException("Il campo " + campo + " inserito non e' valido");
		}
	}
	

	@Override
	public String toString() {
		return null; //"Progetto [id=" + id + ", nome=" + nome + ", obiettivi=" + obiettivi + ", requisiti=" + requisiti
				//+ ", valutazione=" + valutazione + ", stato=" + stato + "]";
	}

	@Override
	public String getDettagli() {
		return null; // "Progetto [id=" + id + ", nome=" + nome + ", obiettivi=" + obiettivi + ", requisiti=" + requisiti
				//+ ", stato=" + stato + "]";
	}
}
