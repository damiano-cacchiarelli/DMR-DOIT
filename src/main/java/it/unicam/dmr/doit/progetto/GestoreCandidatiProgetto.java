package it.unicam.dmr.doit.progetto;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import it.unicam.dmr.doit.utenti.Progettista;

public class GestoreCandidatiProgetto {
	
	private HashMap<Progettista, RuoloProgettista> progettisti;

	public GestoreCandidatiProgetto() {
		this.progettisti = new HashMap<>();
	}
	
	public void aggiungiCandidato(Progettista progettista) {
		//TODO: verifica che non ci sia tra i progettisti
		progettisti.put(progettista, RuoloProgettista.CANDIDATO);
	}
	
	public void confermaCandidato(String idProgettista) {
		progettisti.forEach((k,v) -> {
			if(k.getIdentificativo() == idProgettista && v == RuoloProgettista.CANDIDATO) {
				v = RuoloProgettista.PARTECIPANTE;
			}
		});
		//TODO - Eccezione Nessun candidato trovato?
	}
	
	public List<Progettista> getCandidati() {
		return List.copyOf(progettisti.entrySet()
				.stream()
				.filter(map -> map.getValue() == RuoloProgettista.CANDIDATO)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()))
				.keySet());
	}
	
	
	private enum RuoloProgettista{
		CANDIDATO, PARTECIPANTE;
	}


	public boolean progettistaPresente(String idProgettista) {
		return progettisti.entrySet().stream().filter(p -> p.getKey().getIdentificativo() == idProgettista).count() > 0;
	}
}
