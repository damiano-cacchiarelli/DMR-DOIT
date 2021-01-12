package it.unicam.dmr.doit.progetto;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;

/**
 * Questa classe implementa {@code IProgetto} e rappresenta un progetto con
 * obbiettivi e requisiti. Un progetto viene pubblicato da un {@code Iscritto}
 * con il ruolo di {@code Proponente}. Ogni progetto ha al suo interno un
 * {@code GestoreCandidatiProgetto} per gestire tutti i progettisti che
 * interagiscono con questo progetto.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
public class Progetto implements IProgetto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String nome;
	@Lob
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String obiettivi;
	@Lob
	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
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

	// ================================================================================
	// Metodi
	// ================================================================================

	public void aggiungiValutazione(Valutazione valutazione) {
		Objects.requireNonNull(valutazione, "La valutazione inserita e' nulla");
		if (!this.listaValutazioni.add(valutazione))
			throw new IllegalArgumentException("Valutazione gia inserita");
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public String getObiettivi() {
		return obiettivi;
	}

	public String getRequisiti() {
		return requisiti;
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
		return listaValutazioni.stream().sorted(Comparator.comparing(Valutazione::getCreatoIl))
				.collect(Collectors.toList());
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
	}

	public GestoreCandidatiProgetto getGestoreCandidati() {
		return gestoreCandidati;
	}

	public Proponente getProponente() {
		return proponente;
	}

	public Date getCreatoIl() {
		return creatoIl;
	}

	public String getIdProponente() {
		return proponente.getIscritto().getIdentificativo();
	}

	@Override
	public Set<Tag> getTags() {
		return tags;
	}

	// ================================================================================
	// ToString
	// ================================================================================

	@Override
	public String toString() {
		return "Progetto [id=" + id + ", nome=" + nome + ", obiettivi=" + obiettivi + ", requisiti=" + requisiti
				+ ", stato=" + stato + ", fase=" + fase + ", creatoIl=" + creatoIl + "]";
	}

}
