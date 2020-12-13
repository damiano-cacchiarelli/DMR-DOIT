package it.unicam.dmr.doit.progetto;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;

public enum Fase {
		
	INIZO, 
	SVILUPPO, 
	PUBBLICAZIONE;
/*	
	private String operazioni;
	
	Fase(String o){
		operazioni = o;
	}
*/	
	public Fase nextFase(Fase fase) throws NextFaseException {
		if(this.equals(INIZO)) {
			return SVILUPPO;
		}
		if(this.equals(SVILUPPO)) {
			return PUBBLICAZIONE;
		}else {
			throw new NextFaseException("Nessuna fase successiva");
		}
		
	}
	
	public String operazioniFase(Fase fase) {
		
		//return fase.operazioni;
		
		if(this.equals(INIZO)) {
			return getOperazioniINIZIO();
		}
		if(this.equals(SVILUPPO)) {
			return getOperazioniSVILUPPO();
		}else {
			return getOperazioniPUBBLICAZIONE();
		}
		
	}

	private String getOperazioniINIZIO() {
		return "";
	}
	
	private String getOperazioniPUBBLICAZIONE() {
		return "";
	}

	private String getOperazioniSVILUPPO() {
		return "";
	}
	
}
