package it.unicam.dmr.doit.progetto.exception;

/**
 * Utilizzata quando si tenta di passare alla {@code Fase} successiva ma non e'
 * possibile farlo
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 *
 */
public class NextFaseException extends Exception {

	private static final long serialVersionUID = 8547875498048050422L;

	public NextFaseException() {
		super();
	}

	public NextFaseException(String s) {
		super(s);
	}

}
