package it.unicam.dmr.doit.utenti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.utenti.curriculum.Curriculum;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Iscritto implements Utente {

	@Id
	@Column(length = 20, unique = true)
	@Size(min = 3, max = 10)
	private String identificativo;

	@NotNull
	@NotBlank
	@Column(unique = true)
	private String email;

	@NotNull
	@NotBlank
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "curriculum_id", referencedColumnName = "id")
	private Curriculum curriculum;

	// 	@JsonManagedReference è usato per impedire il ciclo infinito con la classe ruolo
	@JsonManagedReference
	@OneToMany(mappedBy = "iscritto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ruolo> ruoli = new HashSet<>();

	protected Iscritto() { }

	public Iscritto(String identificativo, @NotNull @NotBlank String email, @NotNull @NotBlank String password) {
		super();
		this.identificativo = identificativo;
		this.email = email;
		this.password = password;
	}

	@Override
	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

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
		return "identificativo=" + identificativo + ", email=" + email + ", password=" + password + ", curriculum=" + curriculum + ", ruoli=" + ruoli;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + parametriFormattati() + "]";
	}
	
	public abstract List<TipologiaRuolo> getTipoRuoliPossibili();
	
	public List<TipologiaRuolo> getTipologiaRuoli(){
		List<TipologiaRuolo> ruoli = new ArrayList<>();
		this.ruoli.forEach(r -> ruoli.add(r.getRuolo()));
		return ruoli;
	}
	
	public void addRuolo(Ruolo ruolo) {
		ruolo.setIscritto(this);
		this.ruoli.add(ruolo);
	}
}
