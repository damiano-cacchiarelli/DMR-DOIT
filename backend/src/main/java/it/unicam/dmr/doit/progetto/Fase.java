package it.unicam.dmr.doit.progetto;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;

public enum Fase {
		
	PUBBLICAZIONE("", null),
	SVILUPPO("", PUBBLICAZIONE),
	INIZIO("", SVILUPPO);

	private String operazioni;
	private Fase successiva;
	
	Fase(String o, Fase successiva){
		operazioni = o;
		this.successiva = successiva;
	}

	public Fase nextFase() throws NextFaseException {
		if(this.successiva == null)
			throw new NextFaseException("Nessuna fase successiva");
		return this.successiva;
	}
	
	public String operazioniFase() {
		
		return this.operazioni;
		/*
		if(this.equals(INIZO)) {
			return getOperazioniINIZIO();
		}
		if(this.equals(SVILUPPO)) {
			return getOperazioniSVILUPPO();
		}else {
			return getOperazioniPUBBLICAZIONE();
		}*/
		
	}

	/*
	private String getOperazioniINIZIO() {
		return "";
	}
	
	private String getOperazioniPUBBLICAZIONE() {
		return "";
	}

	private String getOperazioniSVILUPPO() {
		return "";
	}
	*/
}
