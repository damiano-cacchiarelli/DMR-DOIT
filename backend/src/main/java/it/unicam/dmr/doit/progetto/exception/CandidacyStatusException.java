package it.unicam.dmr.doit.progetto.exception;

/**
 * Utilizzata quando si tenta di candidare ad un {@code Progetto} ma non e'
 * consentito farlo.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 *
 */
public class CandidacyStatusException extends Exception {

	private static final long serialVersionUID = 8301932401603688735L;

	public CandidacyStatusException() {
		super();
	}

	public CandidacyStatusException(String s) {
		super(s);
	}

}
