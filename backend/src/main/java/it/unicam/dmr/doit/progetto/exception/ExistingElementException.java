package it.unicam.dmr.doit.progetto.exception;

/**
 * Utilizzata quando si tenta di inserire un elemento che e' presente in un
 * determinato contesto.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 *
 */
public class ExistingElementException extends Exception {

	private static final long serialVersionUID = 521832684514435587L;

	public ExistingElementException() {
		super();
	}

	public ExistingElementException(String s) {
		super(s);
	}

}
