package it.unicam.dmr.doit;

public class Esperto extends Progettista {

	private int rango = 0;

	public Esperto(String identificativo, String nome, String cognome) {
		super(identificativo, nome, cognome);
		
	}
	
	public void setRango(int rango) {
		if(rango < 0) 
			throw new IllegalArgumentException("Il rango di un esperto non può essere minore di 0.");
		
		this.rango = rango;
	}
	
	public int getRango() {
		return rango;
	}
}
