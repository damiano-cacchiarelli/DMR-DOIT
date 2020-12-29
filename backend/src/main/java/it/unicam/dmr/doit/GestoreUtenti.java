package it.unicam.dmr.doit;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Esperto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;

public class GestoreUtenti {

	private Set<Iscritto> inscritti;

	public GestoreUtenti() {
		inscritti = new HashSet<>();
	}

	public Iscritto getInscritto(String identificativo) {
		return inscritti.stream().filter(i -> i.getIdentificativo().equals(identificativo)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Identificativo iscritto inesistente."));
	}

	/**
	 * @throws ClassCastException se l'identificativo non identifica un
	 *                            {@link Esperto}
	 */
	public Esperto getEsperto(String identificativo) {
		return null;//(Esperto) getInscritto(identificativo);
	}

	/**
	 * @throws ClassCastException se l'identificativo non identifica un
	 *                            {@link Progettista}
	 */
	public Progettista getProgettista(String identificativo) {
		return null;//(Progettista) getInscritto(identificativo);
	}

	public void aggiungiInscritto(Iscritto inscritto) {
		Objects.requireNonNull(inscritto, "Non e' possibile aggiungere un utente nullo.");
		this.inscritti.add(inscritto);
	}

	public List<Esperto> getEspertiConsigliati(int idProgetto) {
		// TODO: algoritmo per ricercare gli esperti migliori per quel progetto
		/*List<Iscritto> esp = inscritti.stream().filter(u -> u instanceof Esperto).collect(Collectors.toList());
		List<Esperto> esperti = new ArrayList<>();
		for (Iscritto e : esp) {
			esperti.add((Esperto) e);
		}*/
		return null;//esperti;
	}
}
