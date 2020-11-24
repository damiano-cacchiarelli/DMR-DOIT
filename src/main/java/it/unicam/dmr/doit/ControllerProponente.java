package it.unicam.dmr.doit;
import java.util.Objects;

public class ControllerProponente {
	
	private final Doit doit;
	private Proponente proponente;
	
	public ControllerProponente(Doit doit, Proponente proponente) {
		Objects.requireNonNull(doit, "Non e' possibile utilizzare un sistema nullo.");
		Objects.requireNonNull(proponente, "Non e' possibile utilizzare un progettista nullo.");
		this.doit = doit;
		this.proponente = proponente;
	}
	
	public void compilaProposta(int id, String nome, String obbiettivi, String requisiti) {
		Progetto p = new Progetto(id, nome, obbiettivi, requisiti);
		doit.getVetrina().salvaPropostaProgetto(p);
	}
	
	public void permetteValutazioneProgetto(String idEsperto, int idProgetto) {
		Esperto esperto = doit.getEsperto(idEsperto);
		Progetto p = doit.getVetrina().getProgetto(idProgetto);
		p.setStato(Stato.IN_VALUTAZIONE);
		esperto.riceviRichiestaValutazione(idProgetto);			
	}
	
	public void invitaProgettista(String idProgettista, int idProgetto, int idProposta, String contenuto) {
		PropostaDiPartecipazione pp = new PropostaDiPartecipazione(idProposta, idProgettista, contenuto, idProgetto);
		doit.getProgettista(idProgettista).riceviProposte(pp);
	}
	
	@Override
	public String toString() {
		return proponente.toString();
	}
}
