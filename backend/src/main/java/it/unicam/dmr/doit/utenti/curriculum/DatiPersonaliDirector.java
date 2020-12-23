package it.unicam.dmr.doit.utenti.curriculum;

/*
 * DA ELIMINARE - Esempio di builder
 */
public class DatiPersonaliDirector {

	public void constructEmpty(DatiPersonaliBuilder builder) {
		
		builder.setCittadinanza("");
	}
	
	public static void main(String[] args) {
		DatiPersonaliDirector diretto = new DatiPersonaliDirector();
		DatiPersonaliBuilder builder = new DatiPersonaliBuilder();
		diretto.constructEmpty(builder);
		builder.setNome("ciao");
		System.out.println(builder.getResult());
		
	}
}
