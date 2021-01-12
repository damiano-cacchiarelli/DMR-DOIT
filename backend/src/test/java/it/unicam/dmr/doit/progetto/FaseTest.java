/**
 * 
 */
package it.unicam.dmr.doit.progetto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unicam.dmr.doit.progetto.exception.NextFaseException;


class FaseTest {

	/**
	 * Test method for {@link Fase#nextFase()}.
	 * @throws NextFaseException 
	 */
	@Test
	void testNextFase() throws NextFaseException {
		assertThrows(NextFaseException.class, ()->Fase.PUBBLICAZIONE.nextFase());
		
		assertEquals(Fase.SVILUPPO, Fase.INIZIO.nextFase());
		assertEquals(Fase.PUBBLICAZIONE, Fase.SVILUPPO.nextFase());
		
		Progetto p1 = new Progetto();
		p1.nextFase();
		assertFalse(p1.getGestoreCandidati().isCandidatureAperte());
		
	}

}
