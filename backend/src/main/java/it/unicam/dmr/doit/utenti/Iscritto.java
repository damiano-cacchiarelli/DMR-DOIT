package it.unicam.dmr.doit.utenti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.invito.GestoreInviti;
import it.unicam.dmr.doit.utenti.curriculum.Curriculum;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Questa classe implementa l'interfaccia {@code Utente} e rappresenta un
 * generico iscritto alla piattaforma. Ogni iscritto ha un
 * {@code identificativo} univo, una {@code email} unica, una {@code password},
 * un {@code curriculum} ed una lista di {@code Ruoli}. <br>
 * Inoltre, implementando {@code Utente}, viene implementata anche l'interfaccia
 * {@code Messaggiabile}, per la gestione dei {@code Messaggi}; in particolare,
 * i messaggi inviati sono solamente del tipo {@code Invito}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Iscritto implements Utente {

	@Id
	@Column(length = 20, unique = true)
	@Size(min = 3, max = 20)
	private String identificativo;

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	@Column(unique = true)
	private String email;

	@NotNull(message = Utils.nonNullo)
	@NotBlank(message = Utils.nonVuoto)
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "curriculum_id", referencedColumnName = "id")
	private Curriculum curriculum;

	@JsonManagedReference
	@OneToMany(mappedBy = "iscritto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Ruolo> ruoli = new HashSet<>();

	@Embedded
	private GestoreInviti gestoreInviti = new GestoreInviti();

	protected Iscritto() {
	}

	public Iscritto(String identificativo, @NotNull @NotBlank String email, @NotNull @NotBlank String password) {
		super();
		this.identificativo = identificativo;
		this.email = email;
		this.password = password;
	}

	// ================================================================================
	// Metodi
	// ================================================================================

	public abstract List<TipologiaRuolo> getTipoRuoliPossibili();

	public List<TipologiaRuolo> getTipologiaRuoli() {
		List<TipologiaRuolo> ruoli = new ArrayList<>();
		this.ruoli.forEach(r -> ruoli.add(r.getRuolo()));
		return ruoli;
	}

	public void addRuolo(Ruolo ruolo) {
		ruolo.setIscritto(this);
		this.ruoli.add(ruolo);
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	@Override
	public String getIdentificativo() {
		return identificativo;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	@JsonIgnore
	public Ruolo getRuolo(TipologiaRuolo ruolo) throws NoSuchElementException {
		return ruoli.stream().filter(r -> r.getRuolo().equals(ruolo)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("Ruolo " + ruolo + " non posseduto."));
	}

	@Override
	public GestoreInviti getGestoreMessaggi() {
		if (gestoreInviti.getIscritto() == null)
			gestoreInviti.setIscritto(this);
		return gestoreInviti;
	}

	// ================================================================================
	// Equals & HashCode & ToString
	// ================================================================================

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificativo == null) ? 0 : identificativo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Iscritto))
			return false;
		Iscritto other = (Iscritto) obj;
		if (identificativo == null) {
			if (other.identificativo != null)
				return false;
		} else if (!identificativo.equals(other.identificativo))
			return false;
		return true;
	}

	protected String parametriFormattati() {
		return "identificativo=" + identificativo + ", email=" + email + ", password=" + password + ", curriculum="
				+ curriculum + ", ruoli=" + ruoli + ", gestoreInviti=" + gestoreInviti;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + parametriFormattati() + "]";
	}
}
