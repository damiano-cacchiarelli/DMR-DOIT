/**
 * 
 */
package it.unicam.dmr.doit.invito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.utenti.Persona;


class GestoreInvitiTest {

	/**
	 * Test method for {@link GestoreInviti#riceviMessaggio(it.unicam.dmr.doit.invito.Invito)}.
	 * @throws ExistingElementException 
	 */
	@Test
	void testRiceviMessaggio() throws ExistingElementException {
		GestoreInviti gi1 = new GestoreInviti();
		Persona p1 = new Persona("p1", "p1@p1.it", "p1", "p1", "p1", "p1", "p1", "p1");
		gi1.setIscritto(p1);
		GestoreInviti gi2 = new GestoreInviti();
		Persona p2 = new Persona("p2", "p2@p2.it", "p2", "p2", "p2", "p2", "p2", "p2");
		gi2.setIscritto(p2);
		
		Progetto pTest = new Progetto();
		
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio", pTest, TipologiaInvito.PROPOSTA);
		
		assertEquals(1, p2.getGestoreMessaggi().getListaInvitiRicevuti().size());
	}

	/**
	 * Test method for {@link GestoreInviti#eliminaMessaggio(java.lang.String, boolean)}.
	 * @throws ExistingElementException 
	 */
	@Test
	void testEliminaMessaggio() throws ExistingElementException {
		GestoreInviti gi1 = new GestoreInviti();
		Persona p1 = new Persona("p1", "p1@p1.it", "p1", "p1", "p1", "p1", "p1", "p1");
		gi1.setIscritto(p1);
		GestoreInviti gi2 = new GestoreInviti();
		Persona p2 = new Persona("p2", "p2@p2.it", "p2", "p2", "p2", "p2", "p2", "p2");
		gi2.setIscritto(p2);
		
		Progetto pTest = new Progetto();
		
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio1", pTest, TipologiaInvito.PROPOSTA);
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio2", pTest, TipologiaInvito.PROPOSTA);
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio3", pTest, TipologiaInvito.PROPOSTA);
		
		assertEquals(3, p2.getGestoreMessaggi().getListaInvitiRicevuti().size());
		assertEquals(3, p1.getGestoreMessaggi().getListaInvitiInviati().size());
		
		p1.getGestoreMessaggi().eliminaMessaggio("p11", true);
		p1.getGestoreMessaggi().eliminaMessaggio("p12", true);
		p1.getGestoreMessaggi().eliminaMessaggio("p13", true);
		
		assertEquals(0, p2.getGestoreMessaggi().getListaInvitiRicevuti().size());
		assertEquals(0, p1.getGestoreMessaggi().getListaInvitiInviati().size());
		
		/*
		 * Caso in cui il destinatario vuele eliminare entrambi i messaggi.
		 * 
		 * NB: non e' possibile elimnare i messaggi di entrambi se non nel caso in cui lo faccia il mittente
		 */
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio1", pTest, TipologiaInvito.PROPOSTA);
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio2", pTest, TipologiaInvito.PROPOSTA);
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio3", pTest, TipologiaInvito.PROPOSTA);
		
		p2.getGestoreMessaggi().eliminaMessaggio("p14", true);
		p2.getGestoreMessaggi().eliminaMessaggio("p15", true);
		p2.getGestoreMessaggi().eliminaMessaggio("p16", true);
		
		assertThrows(NoSuchElementException.class,()-> p2.getGestoreMessaggi().eliminaMessaggio("p14", true));	
		assertEquals(0, p2.getGestoreMessaggi().getListaInvitiRicevuti().size());
		assertEquals(3, p1.getGestoreMessaggi().getListaInvitiInviati().size());
		
	}

	/**
	 * Test method for {@link GestoreInviti#inviaMessaggio(it.unicam.dmr.doit.utenti.Iscritto, it.unicam.dmr.doit.invito.Invito)}.
	 * @throws ExistingElementException 
	 */
	@Test
	void testInviaMessaggio() throws ExistingElementException {
		GestoreInviti gi1 = new GestoreInviti();
		Persona p1 = new Persona("p1", "p1@p1.it", "p1", "p1", "p1", "p1", "p1", "p1");
		gi1.setIscritto(p1);
		GestoreInviti gi2 = new GestoreInviti();
		Persona p2 = new Persona("p2", "p2@p2.it", "p2", "p2", "p2", "p2", "p2", "p2");
		gi2.setIscritto(p2);
		
		Progetto pTest = new Progetto();
		
		p1.getGestoreMessaggi().inviaMessaggio(p2, "Messaggio", pTest, TipologiaInvito.PROPOSTA);
		
		assertEquals(1, p2.getGestoreMessaggi().getListaInvitiRicevuti().size());
	}
	

}
