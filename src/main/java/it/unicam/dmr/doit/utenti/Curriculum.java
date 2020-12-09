package it.unicam.dmr.doit.utenti;

import java.util.Objects;

public class Curriculum {

	private String competenze;
	private String datiPersonali;
	
	public Curriculum() {
		this("Nessuna competenza inserita.", "Nessun dato personale inserito.");
	}
	
	public Curriculum(String competenze, String datiPersonali) {
		this.setCompetenze(competenze);
		this.setDatiPersonali(datiPersonali);
	}

	public String getCompetenze() {
		return competenze;
	}

	public void setCompetenze(String competenze) {
		Objects.requireNonNull(competenze, "Le competenze non possono essere nulle.");
		
		this.competenze = competenze;
	}

	public String getDatiPersonali() {
		return datiPersonali;
	}

	public void setDatiPersonali(String datiPersonali) {
		Objects.requireNonNull(datiPersonali, "I dati personali non possono essere nulli.");
		
		this.datiPersonali = datiPersonali;
	}

	@Override
	public String toString() {
		return "Curriculum [competenze=" + competenze + ", datiPersonali=" + datiPersonali + "]";
	}
}
