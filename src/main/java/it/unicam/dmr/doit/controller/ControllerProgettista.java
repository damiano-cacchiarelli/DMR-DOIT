package it.unicam.dmr.doit.controller;

import it.unicam.dmr.doit.Doit;
import it.unicam.dmr.doit.utenti.Progettista;
import it.unicam.dmr.doit.utenti.curriculum.Curriculum;

public class ControllerProgettista implements IController {
	
	private final Doit doit;
	private final Progettista progettista;
	
	public ControllerProgettista(Doit doit, Progettista progettista) {
		this.doit = doit;
		this.progettista = progettista;
	}
	
	/*
	public void modificaCompetenze() {
		Scanner s = new Scanner(System.in);
		System.out.println(progettista.getCurriculum());
		System.out.println("Vuoi modificare le tue competenze?[Y/N]: ");
		if(s.nextLine() == "Y") {
			System.out.println("Inserisci nuove competenze:");
			String competenze = s.nextLine();
			competenzeAggiornate(competenze);
		}
		System.out.println("Vuoi modificare i tui dati personali?[Y/N]: ");
		if(s.nextLine() == "Y") {
			System.out.println("Inserisci nuovi dati personali:");
			String datiPersonali = s.nextLine();
			datiPersonaliAggiornati(datiPersonali);
		}
		s.close();
	}
	
	public void competenzeAggiornate(String competenze) {
		Scanner s = new Scanner(System.in);
		System.out.println("Vuoi salvare le modifiche?[Y/N]: ");
		if(s.nextLine() == "Y") {
			progettista.getCurriculum().setCompetenze(competenze);
			System.out.println("Modifiche salvate");
		}
		s.close();
	}
	
	public void datiPersonaliAggiornati(String datiPersonali) {
		Scanner s = new Scanner(System.in);
		System.out.println("Vuoi salvare le modifiche?[Y/N]: ");
		if(s.nextLine() == "Y") {
			progettista.getCurriculum().setDatiPersonali(datiPersonali);
			System.out.println("Modifiche salvate");
		}
		s.close();
	}
	*/
	
	public void competenzeAggiornate(String competenze) {
		// progettista.getCurriculum().setCompetenze(competenze);
	}
	
	public void datiPersonaliAggiornati(String datiPersonali) {
		// progettista.getCurriculum().setDatiPersonali(datiPersonali);
	}
	
	public Curriculum getCurriculum() {
		return progettista.getCurriculum();
	}
	
	@Override
	public String toString() {
		return progettista.toString();
	}
}
