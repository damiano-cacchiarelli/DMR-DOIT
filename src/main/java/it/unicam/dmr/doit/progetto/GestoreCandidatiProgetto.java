package it.unicam.dmr.doit.progetto;

import java.util.Collection;
import java.util.HashMap;

import it.unicam.dmr.doit.utenti.Progettista;

public class GestoreCandidatiProgetto {
	
	private String candidato = "C";
	private String partecipante = "P";
	
	HashMap<Progettista, String> candidati = new HashMap<>();
	
	public void aggiungiCandidato(Progettista p){
		candidati.put(p, "C");
	}
	
	public Collection<Progettista> getCandidati() {
		return candidati.keySet();
	}
	
	public void confermaCandidato(String idProgettista) {
		byte check = 0;
		candidati.forEach((k,v) -> {
			if(k.getIdentificativo() == idProgettista && v.equals(candidato)) {
				v = partecipante;
			}
		});
		if(check == 0) {
			//TODO - Eccezione Nessun candidato trovato?
			//Oppure un boolean
		}
	}

}
