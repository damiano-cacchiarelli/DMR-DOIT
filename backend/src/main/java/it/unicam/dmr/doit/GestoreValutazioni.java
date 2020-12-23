package it.unicam.dmr.doit;

import java.util.ArrayList;
import java.util.Objects;

public class GestoreValutazioni {

	private ArrayList<Integer> daValutare = new ArrayList<>();

	public void addRichiesta(int idP) {
		Objects.requireNonNull(idP, "Non e' possibile valutare un progetto null");
		daValutare.add(idP);
	}

	public void rimuoviRichiesta(int idP) {
		daValutare.remove(idP);
	}

	public ArrayList<Integer> getDaValutare() {
		return daValutare;
	}

}
