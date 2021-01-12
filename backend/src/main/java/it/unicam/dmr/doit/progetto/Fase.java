package it.unicam.dmr.doit.progetto;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;

/**
 * Enumerazione che rappresenta le possibili fasi di un progetto:
 * <ul>
 * <li>INIZIO</li>
 * <li>SVILUPPO</li>
 * <li>PUBBLICAZIONE</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public enum Fase {

	PUBBLICAZIONE("", null), SVILUPPO("", PUBBLICAZIONE), INIZIO("", SVILUPPO);

	private String operazioni;
	private Fase successiva;

	Fase(String o, Fase successiva) {
		operazioni = o;
		this.successiva = successiva;
	}

	public Fase nextFase() throws NextFaseException {
		if (this.successiva == null)
			throw new NextFaseException("Nessuna fase successiva");
		return this.successiva;
	}

	public String operazioniFase() {
		return this.operazioni;
	}
}
