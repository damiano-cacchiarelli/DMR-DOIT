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
	
	PUBBLICAZIONE(" NESSUNA OPERAZIONE DISPONIBILE", null), 
	SVILUPPO("In questa fase e' possibile: \r\n" + 
			"E' possibile modificare i dettagli del progetto\r\n" + 
			"Non sono accettate candidature\r\n" + 
			"E' possibile invitare progettisti\r\n" + 
			"Il progetto non può esser sottoposto a valutazione" +
			"Nella prossima fase: NESSUNA OPERAZIONE DISPONIBILE", PUBBLICAZIONE), 
	INIZIO("In questa fase e' possibile: \r\n " +
			"vengono definiti i dettagli del progetto (NON VALUTATO)\r\n" + 
			"è possibile modificare i dettagli del progetto (NON VALUTATO / VALUTATO)\r\n" + 
			"vengono aggiunti progettisti (sia inviti che proposte di partecipazione). (NON VALUTATO / VALUTATO)\r\n" + 
			"è possibile far valutare il progetto più volte (IN VALUTAZIONE)\r\n" + 
			"è possibile chiudere le candidature\r\n" + 
			"Nella prossima fase: \r\n" +
			"E' possibile modificare i dettagli del progetto\r\n" + 
			"Non sono accettate candidature\r\n" + 
			"E' possibile invitare progettisti\r\n" + 
			"Il progetto non può esser sottoposto a valutazione", SVILUPPO);
	
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
