package it.unicam.dmr.doit.dataTransferObject.iscritto;

import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

public class RuoloDto {

	private TipologiaRuolo ruolo;

	public RuoloDto(){ }
	
	public RuoloDto(TipologiaRuolo ruolo){
		this.ruolo = ruolo;
	}
	
	public TipologiaRuolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(TipologiaRuolo ruolo) {
		this.ruolo = ruolo;
	}
	
	
}
