package it.unicam.dmr.doit;

public class Valutazione {

	private final String recensione;
	
	

	public Valutazione(String recensione) {
		
		if(recensione == null) {
			throw new NullPointerException("La recensione inserita è nulla");
		}
		
		if(recensione.trim().length() == 0) {
			throw new IllegalArgumentException("La recensione inserita non è valida");
		}
		
		this.recensione = recensione;
	}



	public String getRecensione() {
		
		return recensione;
	}
	
}
