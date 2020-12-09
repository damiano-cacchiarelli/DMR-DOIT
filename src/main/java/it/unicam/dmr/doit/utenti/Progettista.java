package it.unicam.dmr.doit.utenti;

import it.unicam.doit.invito.GestoreInviti;
import it.unicam.doit.invito.GestoreMessaggi;
import it.unicam.doit.invito.Invito;

public class Progettista extends Utente implements Messaggiabile<Invito> {

	private final Curriculum curriculum;
	private final GestoreInviti gestoreInviti;

	public Progettista(String identificativo, String nome, String cognome) {
		super(identificativo, nome, cognome);

		this.curriculum = new Curriculum();
		this.gestoreInviti = new GestoreInviti(identificativo);
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	@Override
	protected String parametriFormattati() {
		return super.parametriFormattati() + ", curriculum=" + curriculum;
	}

	@Override
	public GestoreMessaggi<Invito> getGestoreMessaggi() {
		return gestoreInviti;
	}
}
