package it.unicam.dmr.doit.progetto.exception;

/**
 * Utilizzata quando si tenta di eseguire delle operazioni su un
 * {@code Progetto} che sono pero' vientate dallo {@code Stato} in cui si trova.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 *
 */
public class ProjectStatusException extends Exception {

	private static final long serialVersionUID = -609094920291936640L;

	public ProjectStatusException() {
		super();
	}

	public ProjectStatusException(String s) {
		super(s);
	}

}
