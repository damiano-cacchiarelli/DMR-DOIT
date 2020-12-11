package it.unicam.dmr.doit.utenti.curriculum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import it.unicam.dmr.doit.progetto.Progetto;

public class Curriculum {

	private Competenze competenze;
	private DatiPersonali datiPersonali;
	private List<Progetto> progetti;
		
	public Curriculum() {		
	}
	
	public Curriculum(Competenze competenze, DatiPersonali datiPersonali) {
		this.setCompetenze(competenze);
		this.setDatiPersonali(datiPersonali);
		progetti = new ArrayList<>();
	}

	public Competenze getCompetenze() {
		return competenze;
	}

	public void setCompetenze(Competenze competenze) {
		Objects.requireNonNull(competenze, "Le competenze non possono essere nulle.");
		
		this.competenze = competenze;
	}

	public DatiPersonali getDatiPersonali() {
		return datiPersonali;
	}

	public void setDatiPersonali(DatiPersonali datiPersonali) {
		Objects.requireNonNull(datiPersonali, "I dati personali non possono essere nulli.");
		
		this.datiPersonali = datiPersonali;
	}
	
	public List<Progetto> getProgetti() {
		return progetti;
	}

	@Override
	public String toString() {
		//TODO metodo per progetto
		return "Curriculum [competenze=" + competenze + ";\ndatiPersonali=" + datiPersonali + ";\nProgetti = " + progetti + "]";
	}
	
	
	
	
}
