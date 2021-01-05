package it.unicam.dmr.doit.dataTransferObject.iscritto;

import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

public class RuoloDto {

	@NotNull
	private TipologiaRuolo ruolo;

	public RuoloDto(){ }
	
	public RuoloDto(@NotNull TipologiaRuolo ruolo){
		this.ruolo = ruolo;
	}
	
	public TipologiaRuolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(TipologiaRuolo ruolo) {
		this.ruolo = ruolo;
	}
	
	
}
