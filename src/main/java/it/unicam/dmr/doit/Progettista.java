package it.unicam.dmr.doit;

public class Progettista extends Utente {

	private final Curriculum curriculum;

	public Progettista(String identificativo, String nome, String cognome) {
		super(identificativo, nome, cognome);

		this.curriculum = new Curriculum();
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	@Override
	protected String parametriFormattati() {
		return super.parametriFormattati() + ", curriculum=" + curriculum;
	}
}
