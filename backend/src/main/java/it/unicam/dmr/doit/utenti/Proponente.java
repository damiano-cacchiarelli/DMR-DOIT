package it.unicam.dmr.doit.utenti;

import java.util.Objects;

import it.unicam.dmr.doit.invito.GestoreInviti;
import it.unicam.dmr.doit.invito.GestoreMessaggi;
import it.unicam.dmr.doit.invito.Invito;

public class Proponente extends Utente implements Messaggiabile<Invito> {

	private String sede;
	private String settore;
	private String nomeAzienda;
	
	private final GestoreInviti gestoreInviti;

	public Proponente(String identificativo, String nome, String cognome, String sede, String settore,
			String nomeAzienda) {
		super(identificativo, nome, cognome);

		gestoreInviti = new GestoreInviti(identificativo);
		this.setSede(sede);
		this.setSettore(settore);
		this.setNomeAzienda(nomeAzienda);
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		verificaStringa(sede, "Sede");
		this.sede = sede;
	}

	public String getSettore() {
		return settore;
	}

	public void setSettore(String settore) {
		verificaStringa(settore, "Settore");
		this.settore = settore;
	}

	public String getNomeAzienda() {
		return nomeAzienda;
	}

	public void setNomeAzienda(String nomeAzienda) {
		verificaStringa(nomeAzienda, "Nome Azienda");
		this.nomeAzienda = nomeAzienda;
	}

	@Override
	protected String parametriFormattati() {
		return super.parametriFormattati() + ", sede=" + sede + ", settore=" + settore + ", nomeAzienda=" + nomeAzienda;
	}

	private void verificaStringa(String s, String campo) {
		Objects.requireNonNull(s, "Il campo '" + campo + "' inserito e' nullo");

		if (s.trim().length() == 0)
			throw new IllegalArgumentException("Il campo " + campo + " inserito non e' valido");
	}
	

	@Override
	public GestoreMessaggi<Invito> getGestoreMessaggi() {
		return gestoreInviti;
	}
}
