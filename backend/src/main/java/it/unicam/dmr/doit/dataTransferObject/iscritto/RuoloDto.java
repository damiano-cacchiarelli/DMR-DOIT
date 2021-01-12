package it.unicam.dmr.doit.dataTransferObject.iscritto;

import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Questa classe fa parte degli oggetti che vengono trasfertiti in rete e
 * rappresenta un {@code Ruolo}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class RuoloDto {

	@NotNull(message = Utils.nonNullo)
	private TipologiaRuolo ruolo;

	public RuoloDto() {
	}

	public RuoloDto(@NotNull TipologiaRuolo ruolo) {
		this.ruolo = ruolo;
	}

	public TipologiaRuolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(TipologiaRuolo ruolo) {
		this.ruolo = ruolo;
	}

}
