package it.unicam.dmr.doit.utenti.ruoli;

/**
 * Enumerazione che rappresenta i possibili ruoli di un iscritto:
 * <ul>
 * <li>ROLE_PROPONENTE: Ente che ha la capacita' di proporre e gestire
 * {@code Progetti};</li>
 * <li>ROLE_ESPERTO: Persona che ha la capacita' di valutare {@code Progetti} e
 * {@code Progettisti} partecipanti al progetto;</li>
 * <li>ROLE_PROGETTISTA: Ente o Persona che ha la capacita' di partecipare alla realizzazione dei {@code Progetti};</li>
 * <li>ROLE_SPONSOR: Ente o Persona che ha la capacita' di finanziare {@code Progetti}.</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public enum TipologiaRuolo {
	ROLE_PROPONENTE, ROLE_ESPERTO, ROLE_PROGETTISTA, ROLE_SPONSOR
}
