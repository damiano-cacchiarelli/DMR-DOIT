package it.unicam.dmr.doit.utenti;

import java.util.List;

import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.progetto.Progetto;

public class Esperto extends Progettista {

	private int rango = 0;

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
	
	public void valutaProgetto(Progetto p) {
		
	}
	
	public List<Invito> getRichiesteValutazione(){
		return getGestoreMessaggi().getMessaggi(i -> i.getTipologiaInvito() == TipologiaInvito.VALUTAZIONE);
	}
}


// List<String> unmodifiableList = Collections.unmodifiableList(ls);