package it.unicam.dmr.doit.progetto;

/**
 * Enumerazione che rappresenta i possibili stati di un progetto:
 * <ul>
 * <li>VALUTATO: un esperto ha valutato il progetto</li>
 * <li>IN_VALUTAZIONE: un esperto sta valutando il progetto</li>
 * <li>NON_VALUTATO: il progetto non e' mai stato sottoposto a valutazione da
 * parte di un esperto</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public enum Stato {
	VALUTATO, IN_VALUTAZIONE, NON_VALUTATO
}
