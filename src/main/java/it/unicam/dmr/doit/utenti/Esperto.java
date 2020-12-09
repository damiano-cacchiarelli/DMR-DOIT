package it.unicam.dmr.doit.utenti;

import it.unicam.doit.progetto.Progetto;

public class Esperto extends Progettista {

	private int rango = 0;
//	private final GestoreValutazioni gv = new GestoreValutazioni();

	public Esperto(String identificativo, String nome, String cognome) {
		super(identificativo, nome, cognome);
		
	}
	
	public void setRango(int rango) {
		if(rango < 0) 
			throw new IllegalArgumentException("Il rango di un esperto non puo essere minore di 0.");
		
		this.rango = rango;
	}
	
	public int getRango() {
		return rango;
	}
	
	/*
	public void riceviRichiestaValutazione(int idP) {
		gv.addRichiesta(idP);
	}*/
	
	public void valutaProgetto(Progetto p) {
		
	}
	
	/*
	public ArrayList<Integer> getProgettiDaValutare(){
		return gv.getDaValutare();
	}*/
}
