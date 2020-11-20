package it.unicam.dmr.doit;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Doit {

	private Set<Utente> inscritti;
	private VetrinaProgetti vetrina;
	
	public Doit() {
		inscritti = new HashSet<>();
		vetrina = new VetrinaProgetti();
	}
	
	public Utente getInscritto(String identificativo){
		return inscritti.stream().filter(i -> i.getIdentificativo().equals(identificativo)).findFirst().get();
	}
	/* caso generale ma brutto
	public <T> T getInscritto(String identificativo) {
		return (T) inscritti.stream().filter(i -> i.getIdentificativo().equals(identificativo)).findFirst().get();
	}
	*/
	
	/**
	 * @throws ClassCastException se l'identificativo non identifica un {@link Esperto}
	 */
	public Esperto getEsperto(String identificativo) {
		return (Esperto) getInscritto(identificativo);
	}
	
	/**
	 * @throws ClassCastException se l'identificativo non identifica un {@link Progettista}
	 */
	public Progettista getProgettista(String identificativo) {
		return (Progettista) getInscritto(identificativo);
	}
	
	public void aggiungiInscritto(Utente inscritto) {
		Objects.requireNonNull(inscritto, "Non è possibile aggiungere un utente nullo.");
		this.inscritti.add(inscritto);
	}
	
	public static void main(String[] args) {
		
		Doit doit = new Doit();
				
		Progetto progetto1 = new Progetto(10, "Casa", "Costruire una casa", "Muratori");
		Progetto progetto2 = new Progetto(9, "Macchina", "Costruire una Ferrari", "Meccanici");
		Progetto progetto3 = new Progetto(46, "Moto", "Costruire una Yamaha", "Valentino Rossi");
		doit.vetrina.salvaPropostaProgetto(progetto1, progetto2, progetto3);
		
		System.out.println(doit.vetrina.getProgetto(10));
		System.out.println(doit.vetrina.getProgetto(9));
		System.out.println(doit.vetrina.getProgetto(46)+"\n");
		
		Utente matteo = new Utente("Matteo099", "Matteo", "Romagnoli");
		Proponente roberto = new Proponente("roberto_cesetti_company", "Roberto", "Cesetti", "Montegiorgio", "Teconologia", "Sommo srl");
		Progettista damiano = new Progettista("damiano_cacchiarelli", "Damiano", "Cacchiarelli");
		damiano.getCurriculum().setCompetenze("Onnisciente.");
		damiano.getCurriculum().setDatiPersonali("Nato a Deneb (costellazione del cigno) il 33/0/666.");
		doit.aggiungiInscritto(matteo);
		doit.aggiungiInscritto(roberto);
		doit.aggiungiInscritto(damiano);

		doit.inscritti.forEach(System.out::println);
	}
}
