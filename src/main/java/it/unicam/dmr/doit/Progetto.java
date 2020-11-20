package it.unicam.dmr.doit;

public class Progetto {

	private final int id;
	private final String nome;
	private String obiettivi;
	private String requisiti;
	private Valutazione valutazione;
	private Stato stato;

	public Progetto(int id, String nome) {

		verificaStringa(nome, "Nome");

		this.id = id;
		this.nome = nome;
		this.setStato(Stato.NON_VALUTATO);

	}

	public Progetto(int id, String nome, String obiettivi, String requisiti) {

		this(id, nome);
		this.setObiettivi(obiettivi);
		this.setRequisiti(requisiti);
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

	private void verificaStringa(String s, String campo) {

		if (s == null) {
			throw new NullPointerException("Il campo " + campo + " inserito è nullo");
		}

		if (s.trim().length() == 0) {
			throw new IllegalArgumentException("Il campo " + campo + " inserito non è valido");
		}

	}

	public Valutazione getValutazione() {
		return valutazione;
	}

	public void setValutazione(Valutazione valutazione) {

		if (valutazione == null) {

			throw new NullPointerException("La valutazione inserita è nulla");
		}

		this.valutazione = valutazione;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {

		if (stato == null) {

			throw new NullPointerException("Lo stato inserito è nullo");
		}

		this.stato = stato;
	}

	@Override
	public String toString() {
		return "Progetto [id=" + id + ", nome=" + nome + ", obiettivi=" + obiettivi + ", requisiti=" + requisiti
				+ ", valutazione=" + valutazione + ", stato=" + stato + "]";
	}

}
