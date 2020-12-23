package it.unicam.dmr.doit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import it.unicam.dmr.doit.utenti.Esperto;
import it.unicam.dmr.doit.utenti.Progettista;
import it.unicam.dmr.doit.utenti.Utente;

public class GestoreUtenti {

	private Set<Utente> inscritti;

	public GestoreUtenti() {
		inscritti = new HashSet<>();
	}

	public Utente getInscritto(String identificativo) {
		return inscritti.stream().filter(i -> i.getIdentificativo().equals(identificativo)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Identificativo iscritto inesistente."));
	}

	/**
	 * @throws ClassCastException se l'identificativo non identifica un
	 *                            {@link Esperto}
	 */
	public Esperto getEsperto(String identificativo) {
		return (Esperto) getInscritto(identificativo);
	}

	/**
	 * @throws ClassCastException se l'identificativo non identifica un
	 *                            {@link Progettista}
	 */
	public Progettista getProgettista(String identificativo) {
		return (Progettista) getInscritto(identificativo);
	}

	public void aggiungiInscritto(Utente inscritto) {
		Objects.requireNonNull(inscritto, "Non e' possibile aggiungere un utente nullo.");
		this.inscritti.add(inscritto);
	}

	public List<Esperto> getEspertiConsigliati(int idProgetto) {
		// TODO: algoritmo per ricercare gli esperti migliori per quel progetto
		List<Utente> esp = inscritti.stream().filter(u -> u instanceof Esperto).collect(Collectors.toList());
		List<Esperto> esperti = new ArrayList<>();
		for (Utente e : esp) {
			esperti.add((Esperto) e);
		}
		return esperti;
	}
}
