package it.unicam.dmr.doit.progetto;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;

/**
 * Questa classe rappresenta il gestore dei {@code Progettisti} di un
 * {@code Progetto}, ed ha la responsabilita' di memorizzare tutti i progettisti
 * che interagiscono con il {@code Progetto} (i candidati e quelli
 * partecipanti).
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Embeddable
public class GestoreCandidatiProgetto {

	/**
	 * Lista di progettisti che sono stati invitati o si sono candidati al progetto
	 * ma che la loro candidatura non e' stata ancora confermata.
	 */
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Progettista> candidatiAlProgetto = new HashSet<>();

	/**
	 * Lista di progettisti che sono stati invitati o si sono candidati al progetto
	 * e che la loro candidatura e' stata confermata.
	 */
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Progettista> partecipantiAlProgetto = new HashSet<>();

	private boolean candidatureAperte = true;

	public GestoreCandidatiProgetto() {

	}

	// ================================================================================
	// Metodi
	// ================================================================================

	public boolean isCandidatureAperte() {
		return candidatureAperte;
	}

	public List<String> getIdentificativiCandidati() {
		List<String> ids = new LinkedList<>();
		candidatiAlProgetto.forEach(c -> ids.add(c.getIscritto().getIdentificativo()));
		return ids;
	}

	public List<String> getIdentificativiPartecipanti() {
		List<String> ids = new LinkedList<>();
		partecipantiAlProgetto.forEach(c -> ids.add(c.getIscritto().getIdentificativo()));
		return ids;
	}

	public void setCandidatureAperte(boolean candidature) {
		if (!candidatureAperte)
			throw new IllegalStateException("Le candidature sono gia' chiuse");
		this.candidatureAperte = candidature;
	}

	public void aggiungiCandidato(Progettista progettista) throws ExistingElementException, CandidacyStatusException {
		if (!candidatureAperte)
			throw new CandidacyStatusException("Le candidature sono chiuse");
		if (!candidatiAlProgetto.add(progettista) || partecipantiAlProgetto.contains(progettista))
			throw new ExistingElementException("Il progettista e' gia candidato");
	}

	public void confermaCandidato(String idProgettista) throws NoSuchElementException, NullPointerException {
		/*
		 * TODO - Eccezione Nessun candidato trovato: NoSuchElementException,
		 * NullPointerException
		 */
		Progettista progettista = candidatiAlProgetto.stream()
				.filter(p -> p.getIscritto().getIdentificativo().equals(idProgettista)).findFirst().get();
		partecipantiAlProgetto.add(progettista);
		candidatiAlProgetto.remove(progettista);
	}

	public boolean progettistaPresente(String idProgettista) {
		for (Progettista progettista : candidatiAlProgetto) {
			if (progettista.getIscritto().getIdentificativo().equals(idProgettista))
				return true;
		}
		for (Progettista progettista : partecipantiAlProgetto) {
			if (progettista.getIscritto().getIdentificativo().equals(idProgettista))
				return true;
		}
		return false;
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public Set<Progettista> getCandidatiAlProgetto() {
		return candidatiAlProgetto;
	}

	public Set<Progettista> getPartecipantiAlProgetto() {
		return partecipantiAlProgetto;
	}
}
