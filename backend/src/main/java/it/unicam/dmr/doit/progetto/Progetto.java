package it.unicam.dmr.doit.progetto;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;

@Entity
public class Progetto implements IProgetto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@NotBlank
	private String nome;
	@Lob
	@NotNull
	@NotBlank
	private String obiettivi;
	@Lob
	@NotNull
	@NotBlank
	private String requisiti;

	@JoinTable(name = "categorie_progetti", joinColumns = { @JoinColumn(name = "progetto") }, inverseJoinColumns = {
			@JoinColumn(name = "categoria") })
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Tag> tags = new HashSet<>();

	@Enumerated(EnumType.STRING)
	private Stato stato = Stato.NON_VALUTATO;

	@Enumerated(EnumType.STRING)
	private Fase fase = Fase.INIZIO;

	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date creatoIl;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "proponente_id", nullable = false)
	private Proponente proponente;

	@NotNull
	@Embedded
	private GestoreCandidatiProgetto gestoreCandidati = new GestoreCandidatiProgetto();

	@JsonManagedReference
	@OneToMany(mappedBy = "progetto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private SortedSet<Valutazione> listaValutazioni = new TreeSet<>();
	private Set<Valutazione> listaValutazioni = new HashSet<>();

	public Progetto() {
	}

	public Progetto(@NotNull @NotBlank String nome, @NotNull @NotBlank String obiettivi,
			@NotNull @NotBlank String requisiti, Proponente proponente, Set<Tag> tags) {
		this.nome = nome;
		this.obiettivi = obiettivi;
		this.requisiti = requisiti;
		this.proponente = proponente;
		this.tags = tags;
	}

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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public Collection<Valutazione> getListaValutazioni() {
		return listaValutazioni.stream().sorted(Comparator.comparing(Valutazione::getCreatoIl)).collect(Collectors.toList());
		//return listaValutazioni;
	}

	public void setListaValutazioni(SortedSet<Valutazione> listaValutazioni) {
		this.listaValutazioni = listaValutazioni;
	}

	public void aggiungiValutazione(Valutazione valutazione) {
		Objects.requireNonNull(valutazione, "La valutazione inserita e' nulla");
		if (!this.listaValutazioni.add(valutazione))
			throw new IllegalArgumentException("Valutazione gia inserita");
	}

	@JsonIgnore
	public Valutazione getLastValutazione() {
		if (listaValutazioni.isEmpty()) {
			return null;
		}
		return listaValutazioni.stream().max(Comparator.comparing(Valutazione::getCreatoIl)).get();
	}

	@Override
	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		Objects.requireNonNull(stato, "Lo stato inserito e' nullo");
		this.stato = stato;
	}

	private void setFase(Fase fase) {
		this.fase = fase;
	}

	@Override
	public Fase getFase() {
		return fase;
	}

	@Override
	public void nextFase() throws NextFaseException {
		this.setFase(fase.nextFase());
		if(gestoreCandidati.isCandidatureAperte()) {
			gestoreCandidati.chiudiCandidature();
		}
	}

	public GestoreCandidatiProgetto getGestoreCandidati() {
		return gestoreCandidati;
	}

	public void setGestoreCandidati(GestoreCandidatiProgetto gestoreCandidatoProgetto) {
		this.gestoreCandidati = gestoreCandidatoProgetto;
	}

	public Proponente getProponente() {
		return proponente;
	}

	public Date getCreatoIl() {
		return creatoIl;
	}

	public void setCreatoIl(Date creatoIl) {
		this.creatoIl = creatoIl;
	}

	public void setProponente(Proponente proponente) {
		this.proponente = proponente;
	}

	private void verificaStringa(String s, String campo) {
		Objects.requireNonNull(s, "Il campo " + campo + " inserito e' nullo");

		if (s.trim().length() == 0) {
			throw new IllegalArgumentException("Il campo " + campo + " inserito non e' valido");
		}
	}

	public String getIdProponente() {
		return proponente.getIscritto().getIdentificativo();
	}

	@Override
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Progetto [id=" + id + ", nome=" + nome + ", obiettivi=" + obiettivi + ", requisiti=" + requisiti
				+ ", stato=" + stato + ", fase=" + fase + ", creatoIl=" + creatoIl + "]";
	}

}
