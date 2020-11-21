package it.unicam.dmr.doit;

public class PropostaDiPartecipazione {

	private final String idProgettista;
	private final int id;
	private final String contenuto;
	private final int idProgetto;
	//TODO Un porogetto o un IDProgetto associato alla richiesta
	//Oppure utilizzare contenuto per descrivere il progetto

	public PropostaDiPartecipazione(int id, String idProgettista, String contenuto, int idProgetto) {
		this.id = id;
		this.idProgettista = idProgettista;
		this.contenuto = contenuto;
		this.idProgetto = idProgetto;
	}

	public String getidProgettista() {
		return idProgettista;
	}

	public int getId() {
		return id;
	}

	public String getContenuto() {
		return contenuto;
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	@Override
	public String toString() {
		return "ID [" + id + "] Progettista : " + idProgettista + "; Progetto: "+idProgetto+";\n" + "Dettagli: " + contenuto;
	}

}
