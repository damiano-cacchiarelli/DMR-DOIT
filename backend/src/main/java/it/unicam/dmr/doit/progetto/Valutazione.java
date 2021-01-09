package it.unicam.dmr.doit.progetto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.utenti.ruoli.Esperto;

@Entity
public class Valutazione implements IValutazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@NotBlank
	private String recensione;

	@JsonManagedReference
	@OneToMany(mappedBy = "valutazioneProgetto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ValutazioneProgettista> valutazioniCandidati = new HashSet<>();

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_progetto", nullable = false)
	private Progetto progetto;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_esperto", nullable = false)
	private Esperto esperto;
	
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date creatoIl;
	
	public Valutazione() {}
	
	public Valutazione(@NotNull @NotBlank String recensione, Esperto esperto, Progetto progetto) {
		super();
		this.recensione = recensione;
		this.esperto = esperto;
		this.progetto = progetto;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getRecensione() {
		return recensione;
	}

	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}

	public Set<ValutazioneProgettista> getValutazioniCandidati() {
		return valutazioniCandidati;
	}

	public void setValutazioniCandidati(Set<ValutazioneProgettista> valutazioniCandidati) {
		this.valutazioniCandidati = valutazioniCandidati;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

	public Esperto getEsperto() {
		return esperto;
	}

	public void setEsperto(Esperto esperto) {
		this.esperto = esperto;
	}
	
	public Date getCreatoIl() {
		return creatoIl;
	}

	public void setCreatoIl(Date creatoIl) {
		this.creatoIl = creatoIl;
	}

	public ValutazioneProgettista getValutazioneCandidato(String identificativoProgettista) {
		for(ValutazioneProgettista vp : valutazioniCandidati) {
			if(vp.getIdentificativoProgettista().equals(identificativoProgettista))
				return vp;
		}
		return null;
	}
	
	public void addValutazioneCandidato(ValutazioneProgettista valutazioneProgettista) throws ExistingElementException {
		if(!valutazioniCandidati.add(valutazioneProgettista))
			throw new ExistingElementException("Valutazione gia presente");
	}
	
	public String getIdentificativoEsperto() {
		return esperto.getIscritto().getIdentificativo();
	}

	
}
