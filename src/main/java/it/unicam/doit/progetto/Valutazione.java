package it.unicam.doit.progetto;

import java.util.Objects;

public class Valutazione {

	private final String recensione;
	
	public Valutazione(String recensione) {
		Objects.requireNonNull(recensione, "La recensione inserita e' nulla");
		
		if(recensione.trim().length() == 0) {
			throw new IllegalArgumentException("La recensione inserita non e' valida");
		}
		
		this.recensione = recensione;
	}

	public String getRecensione() {
		return recensione;
	}
}
