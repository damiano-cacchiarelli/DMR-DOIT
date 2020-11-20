package it.unicam.dmr.doit;

public class Progetto {

	private final int        id;
	private final String     nome;
	private String     obiettivi;
	private String     requisiti;
	
	public Progetto (int id, String nome) {
	
		verificaStringa(nome, "Nome");
		
		this.id   = id;
		this.nome = nome;
	}
	
	public Progetto(int id, String nome, String obiettivi, String requisiti) {
		
		this(id,nome);
		this.setObiettivi(obiettivi);
		this.setRequisiti(requisiti);
	}

	public String getObiettivi() {
		return obiettivi;
	}

	public void setObiettivi(String obiettivi) {
		
		verificaStringa(obiettivi, "Obiettivi");
		
		this.obiettivi = obiettivi;
		
	}

	public String getRequisiti() {
		return requisiti;
	}

	public void setRequisiti(String requisiti) {
		
		verificaStringa(requisiti, "Requisiti");
		
		this.requisiti = requisiti;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	private void verificaStringa (String s, String campo) {
		
		if(s == null) {
			throw new NullPointerException("Il campo " + campo + " inserito è nullo");
		}
		
		if(s.trim().length() == 0) {
			throw new IllegalArgumentException("Il campo " + campo + " inserito non è valido");
		}
		
	}
	

	
	
	
}
