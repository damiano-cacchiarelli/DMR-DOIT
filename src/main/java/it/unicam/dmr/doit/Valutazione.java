package it.unicam.dmr.doit;

import java.util.Objects;

public class Valutazione {

	private final String recensione;
	
	public Valutazione(String recensione) {
		Objects.requireNonNull(recensione, "La recensione inserita è nulla");
		
		if(recensione.trim().length() == 0) {
			throw new IllegalArgumentException("La recensione inserita non è valida");
		}
		
		this.recensione = recensione;
	}

	public String getRecensione() {
		return recensione;
	}
}