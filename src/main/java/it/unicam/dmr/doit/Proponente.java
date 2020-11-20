package it.unicam.dmr.doit;

import java.util.Objects;

public class Proponente extends Utente {

	private String sede;
	private String settore;
	private String nomeAzienda;

	public Proponente(String identificativo, String nome, String cognome, String sede, String settore,
			String nomeAzienda) {
		super(identificativo, nome, cognome);

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
		Objects.requireNonNull(s, "Il campo '" + campo + "' inserito è nullo");

		if (s.trim().length() == 0)
			throw new IllegalArgumentException("Il campo " + campo + " inserito non è valido");
	}
}
