package it.unicam.dmr.doit.invito;

/**
 * Enumerazione che rappresenta i possibili tipi di risposta di un invito:
 * <ul>
 * <li>ACCETTATA: indica che l'invito e' stato accettato</li>
 * <li>RIFIUTATA: indica che l'invito e' stato rifiutato</li>
 * <li>IN_ATTESA: indica che l'invito non e' ancora stato visualizzato (ne accettato ne rifiutato, e' in sospeso/decisione)</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public enum TipologiaRisposta {
	ACCETTATA,
	RIFIUTATA,
	IN_ATTESA
}
