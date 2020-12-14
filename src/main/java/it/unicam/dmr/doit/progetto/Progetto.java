package it.unicam.dmr.doit.progetto;

import java.util.LinkedList;
import java.util.Objects;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.utenti.Proponente;

public class Progetto implements IProgetto {

	private final Proponente proponente;
	private final int id;
	private final String nome;
	private String obiettivi;
	private String requisiti;
	private LinkedList<Valutazione> valutazione;
	private Stato stato;
	private Fase fase;
	private GestoreCandidatiProgetto gcp = new GestoreCandidatiProgetto();

	public Progetto(Proponente proponente, int id, String nome) {
		this(proponente, id, nome, "obiettivi", "requisiti");
	}

	public Progetto(Proponente proponente, int id, String nome, String obiettivi, String requisiti) {
		this.setObiettivi(obiettivi);
		this.setRequisiti(requisiti);
		verificaStringa(nome, "Nome");
		this.proponente = proponente;
		this.id = id;
		this.nome = nome;
		this.valutazione = new LinkedList<>();
		this.setStato(Stato.NON_VALUTATO);
		this.setFase(Fase.INIZIO);
	}


	public String getObiettivi() {
		return obiettivi;
	}

	public void setObiettivi(String obiettivi) {
		verificaStringa(obiettivi, "Obiettivi");

		this.obiettivi = obiettivi;
	}

	public String getRequisiti() {
		return requisiti;
	}

	public void setRequisiti(String requisiti) {
		verificaStringa(requisiti, "Requisiti");

		this.requisiti = requisiti;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Valutazione getValutazione() {
		return valutazione.peekLast();
	}

	public void aggiungiValutazione(Valutazione valutazione) {
		Objects.requireNonNull(valutazione, "La valutazione inserita e' nulla");

		this.valutazione.add(valutazione);
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		Objects.requireNonNull(stato, "Lo stato inserito e' nullo");

		this.stato = stato;
	}
	
	private void setFase(Fase fase) {
		this.fase = fase;
	}
	
	public Fase getFase() {
		return fase;
	}
	
	public void nextFase() throws NextFaseException {
		this.setFase(fase.nextFase());
	}
	
	public GestoreCandidatiProgetto getGestoreCandidati() {
		return gcp;
	}

	public Proponente getProponente() {
		return proponente;
	}
	
	private void verificaStringa(String s, String campo) {
		Objects.requireNonNull(s, "Il campo " + campo + " inserito e' nullo");

		if (s.trim().length() == 0) {
			throw new IllegalArgumentException("Il campo " + campo + " inserito non e' valido");
		}
	}
	

	@Override
	public String toString() {
		return "Progetto [id=" + id + ", nome=" + nome + ", obiettivi=" + obiettivi + ", requisiti=" + requisiti
				+ ", valutazione=" + valutazione + ", stato=" + stato + "]";
	}

	@Override
	public String getDettagli() {
		return "Progetto [id=" + id + ", nome=" + nome + ", obiettivi=" + obiettivi + ", requisiti=" + requisiti
				+ ", stato=" + stato + "]";
	}
}
