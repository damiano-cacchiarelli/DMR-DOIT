package it.unicam.dmr.doit;

import java.util.HashSet;
import java.util.Set;

public class VetrinaProgetti {

	private Set<Progetto> progetti;

	public VetrinaProgetti() {

		this.progetti = new HashSet<>();
	}

	public void salvaPropostaProgetto(Progetto... progetto) {

		if (progetto == null) {

			throw new NullPointerException("La lista di progetti è nulla");
		}

		for (Progetto p : progetto) {

			if (p == null) {

				throw new NullPointerException("Il progetto inserito è nullo");
			}

			if (progetti.contains(p)) {

				throw new IllegalStateException("Progetto già contenuto");
			}

		}

		for (Progetto p : progetto)
			progetti.add(p);
	}

	public Progetto getProgetto(int idProgetto) {

		// return progetti.stream().filter ( progetto -> progetto.getId() == idProgetto
		// ).findFirst().get();

		for (Progetto progetto : progetti) {

			if (progetto.getId() == idProgetto) {
				return progetto;

			}
		}

		return null;
	}
}
