package it.unicam.dmr.doit;

public class IEsperto {

	private final Doit doit;
	private final Esperto esperto;

	public IEsperto(Doit doit, Esperto esperto) {
		this.doit = doit;
		this.esperto = esperto;
	}

	public void valutaProgetto() {
		System.out.println(esperto.getProgettiDaValutare().toString());
	}

	public Progetto selezionaProgetto(int idProgetto) {
		return doit.getVetrina().getProgetto(idProgetto);
	}
	
	public void rilasciaValutazione(Valutazione valutazione, Progetto p) {
		p.setValutazione(valutazione);
	}

}
