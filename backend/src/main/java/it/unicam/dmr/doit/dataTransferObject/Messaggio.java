package it.unicam.dmr.doit.dataTransferObject;

/**
 * Questa classe rappresenta un messaggio che deve essere inviato in rete.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class Messaggio {

	private String messaggio;

	public Messaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
}
