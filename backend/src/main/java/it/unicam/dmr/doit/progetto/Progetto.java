package it.unicam.dmr.doit.progetto;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;

@Entity
public class Progetto implements IProgetto {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String obiettivi;
	@NotNull
	@NotBlank
	private String requisiti;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Stato stato = Stato.NON_VALUTATO;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Fase fase = Fase.INIZIO;
	
	@Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
	private Date creatoIl;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proponente_id", nullable = false)
	private Proponente proponente;
	
	
	/*DA MODIFICARE*/
	@ManyToMany(mappedBy = "candidature", fetch = FetchType.LAZY)
    private Set<Progettista> progettisti = new HashSet<>();
	@Transient
	private LinkedList<Valutazione> listaValutazioni = new LinkedList<Valutazione>();
	/*
	@Transient
	private GestoreCandidatiProgetto gcp = new GestoreCandidatiProgetto();
	@Transient
	 */
	public Progetto() {}
	
	public Progetto(@NotNull @NotBlank String nome, @NotNull @NotBlank String obiettivi,
			@NotNull @NotBlank String requisiti, Date creatoIl, Proponente proponente) {
		this.nome = nome;
		this.obiettivi = obiettivi;
		this.requisiti = requisiti;
		this.creatoIl = creatoIl;
		this.proponente = proponente;
	}
/*
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
	}
*/

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
