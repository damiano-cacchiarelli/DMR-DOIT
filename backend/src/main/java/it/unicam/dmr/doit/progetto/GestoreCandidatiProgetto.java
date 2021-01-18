package it.unicam.dmr.doit.progetto;

import java.util.HashSet;
import java.util.Iterator;
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
import javassist.NotFoundException;

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
	 * Lista di progettisti (ruolo) che sono stati invitati o si sono candidati al progetto
	 * ma che la loro candidatura non e' stata ancora confermata.
	 */
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Progettista> candidatiAlProgetto = new HashSet<>();

	/**
	 * Lista di progettisti (ruolo) che sono stati invitati o si sono candidati al progetto
	 * e che la loro candidatura e' stata confermata.
	 */
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Progettista> partecipantiAlProgetto = new HashSet<>();

	private boolean candidature = true;

	public GestoreCandidatiProgetto() {

	}

	// ================================================================================
	// Metodi
	// ================================================================================

	public boolean isCandidatureAperte() {
		return candidature;
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

	public void chiudiCandidature(){
		this.candidature = false;
	}

	public void aggiungiCandidato(Progettista progettista) throws ExistingElementException, CandidacyStatusException {
		if (!candidature)
			throw new CandidacyStatusException("Le candidature sono chiuse");
		if (!candidatiAlProgetto.add(progettista) || partecipantiAlProgetto.contains(progettista))
			throw new ExistingElementException("Il progettista e' gia candidato");
	}

	public void confermaCandidato(String idProgettista) throws NoSuchElementException {
		Progettista progettista = candidatiAlProgetto.stream()
				.filter(p -> p.getIscritto().getIdentificativo().equals(idProgettista)).findFirst().get();
		partecipantiAlProgetto.add(progettista);
		candidatiAlProgetto.remove(progettista);
	}
	
	public void aggiungiPartecipante(Progettista progettista) throws ExistingElementException {
		if(candidatiAlProgetto.contains(progettista)) {
			confermaCandidato(progettista.getIscritto().getIdentificativo());
		}else if(!partecipantiAlProgetto.add(progettista))
			throw new ExistingElementException("Il progettista partecipa gia'");
	}
	
	public void rimuoviProgettista(String idProgettista) throws NotFoundException {
		if(!progettistaPresente(idProgettista))
			throw new NotFoundException("il progettista non e' presente");
		
		Iterator<Progettista> iteratorCandidati = candidatiAlProgetto.iterator();
		while(iteratorCandidati.hasNext()) {
			if(iteratorCandidati.next().getIscritto().getIdentificativo().equals(idProgettista)) {
				iteratorCandidati.remove();
				return;
			}
		}
		
		Iterator<Progettista> iteratorpartecipanti = partecipantiAlProgetto.iterator();
		while(iteratorpartecipanti.hasNext()) {
			if(iteratorpartecipanti.next().getIscritto().getIdentificativo().equals(idProgettista)) {
				iteratorpartecipanti.remove();
				return;
			}
		}
		
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
