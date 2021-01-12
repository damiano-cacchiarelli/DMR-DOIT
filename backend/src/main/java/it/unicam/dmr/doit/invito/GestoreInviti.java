package it.unicam.dmr.doit.invito;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Questa classe rappresenta il gestore degli {@code Inviti} di un Iscritto.
 * Implementa l'interfaccia {@code GestoreMessaggi<Invito>} ed ha la
 * responsabilità di inviare, ricevere, eliminare ed ottenere un {@code Invito}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Embeddable
public class GestoreInviti implements GestoreMessaggi<Invito> {

	@Transient
	private Iscritto iscritto;

	private int nextIdInvito = 0;

	@JsonManagedReference
	@OneToMany(mappedBy = "mittente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Invito> listaInvitiInviati = new HashSet<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "destinatario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Invito> listaInvitiRicevuti = new HashSet<>();

	public GestoreInviti() {
	}

	public GestoreInviti(Iscritto iscritto) {
		this.iscritto = iscritto;
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	@JsonIgnore
	@Override
	public Iscritto getIscritto() {
		return iscritto;
	}

	public void setIscritto(Iscritto iscritto) {
		this.iscritto = iscritto;
	}

	public Set<Invito> getListaInvitiInviati() {
		return listaInvitiInviati;
	}

	public Set<Invito> getListaInvitiRicevuti() {
		return listaInvitiRicevuti;
	}

	public Set<Invito> getMessaggi() {
		Set<Invito> inviti = new HashSet<>(listaInvitiInviati);
		inviti.addAll(listaInvitiRicevuti);
		return inviti;
	}

	// ================================================================================
	// Metodi
	// ================================================================================

	@Override
	public List<Invito> getMessaggi(Predicate<? super Invito> filtro) {
		List<Invito> li = getMessaggi().stream().filter(filtro).collect(Collectors.toList());
		return Collections.unmodifiableList(li);
	}

	@Override
	public void riceviMessaggio(Invito messaggio) {
		// inserisci il nuovo messaggio nella lista dei messaggi non letti?
		messaggio.setSoggetto(RuoloSoggetto.DESTINATARIO);
		if(!listaInvitiRicevuti.add(messaggio))
			throw new IllegalArgumentException("Messaggio gia' ricevuto");
	}

	@Override
	public void eliminaMessaggio(String idMessaggio){
		this.eliminaMessaggio(idMessaggio, false);
	}

	@Override
	public void eliminaMessaggio(String idMessaggio, boolean entrambi) {
		Invito invito = getMessaggio(idMessaggio);
		if (listaInvitiInviati.contains(invito)) {
			if (entrambi)
				invito.getDestinatario().getGestoreMessaggi().eliminaMessaggio(idMessaggio, false);
			listaInvitiInviati.remove(invito);
		} else {
			listaInvitiRicevuti.remove(invito);
		}
	}

	@Override
	public void inviaMessaggio(Iscritto destinatario, String contenuto, Progetto progetto,
			TipologiaInvito tipologiaInvito) {
		Invito invito = new Invito(getNextId(), contenuto, tipologiaInvito, iscritto, destinatario, progetto.getId(),
				progetto.getNome());
		inviaMessaggio(destinatario, invito);
	}

	@Override
	public void inviaMessaggio(Iscritto destinatario, Invito messaggio){
		messaggio.setSoggetto(RuoloSoggetto.MITTENTE);
		listaInvitiInviati.add(messaggio);
		if(!listaInvitiInviati.add(messaggio))
			throw new IllegalArgumentException("Messaggio gia' inviato");
		destinatario.getGestoreMessaggi()
				.riceviMessaggio(new Invito(messaggio.getId(), messaggio.getContenuto(), messaggio.getTipologiaInvito(),
						iscritto, destinatario, messaggio.getIdProgetto(), messaggio.getNomeProgetto()));
	}

	@Override
	public Invito getMessaggio(String idMessaggio) throws NoSuchElementException {
		return getMessaggi().stream().filter(i -> i.getId().equals(idMessaggio)).findFirst().get();
	}

	private String getNextId() {
		return iscritto.getIdentificativo() + getNextIdInvito();
	}

	public final int getNextIdInvito() {
		return ++nextIdInvito;
	}

	// ================================================================================
	// ToString
	// ================================================================================

	@Override
	public String toString() {
		return "GestoreInviti [listaInvitiInviati=" + listaInvitiInviati + ", listaInvitiRicevuti="
				+ listaInvitiRicevuti + "]";
	}
}
