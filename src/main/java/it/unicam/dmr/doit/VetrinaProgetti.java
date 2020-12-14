package it.unicam.dmr.doit;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unicam.dmr.doit.progetto.Progetto;

public class VetrinaProgetti {

	private Set<Progetto> progetti;

	public VetrinaProgetti() {
		this.progetti = new HashSet<>();
	}

	public void salvaPropostaProgetto(Progetto... progetto) {
		Objects.requireNonNull(progetto, "La lista di progetti e' nulla");

		for (Progetto p : progetto) {
			Objects.requireNonNull(p, "Il progetto inserito e' nullo");

			if (progetti.contains(p)) {
				throw new IllegalStateException("Progetto gia� contenuto");
			}
		}

		for (Progetto p : progetto)
			progetti.add(p);
	}

	public Progetto getProgetto(int idProgetto) {
		return progetti.stream()
				.filter(progetto -> progetto.getId() == idProgetto)
				.findFirst().get();
	}

	public List<Progetto> cercaProgetto(String nome) {
		return progetti.stream().filter(p -> p.getNome().contains(nome)).collect(Collectors.toList());
	}
	
	public List<Progetto> getProgetti(Predicate<? super Progetto> filtro){
		return progetti.stream().filter(filtro).collect(Collectors.toList());
	}
}
