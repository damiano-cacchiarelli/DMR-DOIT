package it.unicam.dmr.doit.utenti.curriculum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Curriculum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String professione;
	private String lingue;
	private String sito;
	
	public Curriculum(String professione, String lingue, String sito) {
		this.professione = professione;
		this.lingue = lingue;
		this.sito = sito;
	}
	
	public String getProfessione() {
		return professione;
	}
	
	public void setProfessione(String professione) {
		this.professione = professione;
	}
	
	public String getLingue() {
		return lingue;
	}
	
	public void setLingue(String lingue) {
		this.lingue = lingue;
	}
	
	public String getSito() {
		return sito;
	}
	
	public void setSito(String sito) {
		this.sito = sito;
	}
}