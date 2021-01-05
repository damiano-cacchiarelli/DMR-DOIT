package it.unicam.dmr.doit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;

public final class Utils {

	public static final String nonNullo = "non puo' essere nullo";
	public static final String nonVuoto = "non puo' essere vuoto";
	public static final String nonValido = "non e' valido";
	
	public static String getErrore(BindingResult bindingResult) {
		if (!bindingResult.hasErrors())
			return "";
		StringBuilder messaggio = new StringBuilder();
		bindingResult.getFieldErrors()
				.forEach(e -> messaggio.append("Il campo " + e.getField().toUpperCase() + " " + e.getDefaultMessage() + "\n"));
		return messaggio.toString();
	}
	
	//TODO: da utilizzare??
	public static <T> ResponseEntity<T> creaRisposta(T entity, HttpStatus status){
		return new ResponseEntity<T>(entity, status);
	}
	//TODO: da utilizzare??
	public static ResponseEntity<Messaggio> creaMessaggio(String messaggio, HttpStatus status){
		return new ResponseEntity<>(new Messaggio(messaggio), status);
	}
}
