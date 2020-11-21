package it.unicam.dmr.doit;

import java.util.Scanner;

public class IProgettista {
	
	private final Doit doit;
	private final Progettista progettista;
	
	public IProgettista(Doit doit, Progettista progettista) {
		this.doit = doit;
		this.progettista = progettista;
	}
	
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
}
