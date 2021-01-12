package it.unicam.dmr.doit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;

/**
 * Questa classe e' dichiarata come final ed ha solo metodi statici. Questa
 * classe ha il solo compio di facilitare la gestione e creazione dei messaggi
 * da inviare al frontend.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public final class Utils {

	public static final String nonNullo = "non puo' essere nullo";
	public static final String nonVuoto = "non puo' essere vuoto";
	public static final String nonValido = "non e' valido";

	/**
	 * @param bindingResult
	 * @return un messaggio di errore contenente tutti i campi errati
	 */
	public static String getErrore(BindingResult bindingResult) {
		if (!bindingResult.hasErrors())
			return "";
		StringBuilder messaggio = new StringBuilder();
		bindingResult.getFieldErrors().forEach(
				e -> messaggio.append("Il campo " + e.getField().toUpperCase() + " " + e.getDefaultMessage() + "\n"));
		return messaggio.toString();
	}

	/**
	 * @param <T>
	 * @param entity
	 * @param status
	 * @return un {@code ResponseEntity<T>} con l'entity e lo status passati.
	 */
	public static <T> ResponseEntity<T> creaRisposta(T entity, HttpStatus status) {
		return new ResponseEntity<T>(entity, status);
	}

	/**
	 * @param messaggio
	 * @param status
	 * @return un {@code ResponseEntity<Messaggio>} con il messaggio e lo status passati.
	 */
	public static ResponseEntity<Messaggio> creaMessaggio(String messaggio, HttpStatus status) {
		return new ResponseEntity<>(new Messaggio(messaggio), status);
	}
}
