package it.unicam.dmr.doit.progetto;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;

@Embeddable
public class GestoreCandidatiProgetto {

	/**
	 * Lista di progettisti che sono stati invitati o si sono candidati al progetto
	 * ma che la loro candidatura non e' stata ancora confermata.
	 */
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Progettista> candidatiAlProgetto = new HashSet<>();

	/**
	 * Lista di progettisti che sono stati invitati o si sono candidati al progetto
	 * e che la loro candidatura e' stata confermata.
	 */
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Progettista> partecipantiAlProgetto = new HashSet<>();

	private boolean candidatureAperte = true;

	public GestoreCandidatiProgetto() {

	}

	public Set<Progettista> getCandidatiAlProgetto() {
		return candidatiAlProgetto;
	}

	public void setCandidatiAlProgetto(Set<Progettista> candidatiAlProgetto) {
		this.candidatiAlProgetto = candidatiAlProgetto;
	}

	public Set<Progettista> getPartecipantiAlProgetto() {
		return partecipantiAlProgetto;
	}

	public void setPartecipantiAlProgetto(Set<Progettista> partecipantiAlProgetto) {
		this.partecipantiAlProgetto = partecipantiAlProgetto;
	}

	public boolean isCandidatureAperte() {
		return candidatureAperte;
	}

	public void setCandidatureAperte(boolean candidature) {
		this.candidatureAperte = candidature;
	}

	public void aggiungiCandidato(Progettista progettista) throws ExistingElementException, CandidacyStatusException {
		
		if(!candidatureAperte)
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
}
