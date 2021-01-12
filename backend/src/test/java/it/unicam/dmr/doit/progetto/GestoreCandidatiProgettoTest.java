/**
 * 
 */
package it.unicam.dmr.doit.progetto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.Persona;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;

class GestoreCandidatiProgettoTest {

	/**
	 * Test method for
	 * {@link GestoreCandidatiProgetto#aggiungiCandidato(it.unicam.dmr.doit.utenti.ruoli.Progettista)}.
	 * 
	 * @throws CandidacyStatusException
	 * @throws ExistingElementException
	 */
	@Test
	void testAggiungiCandidato() throws ExistingElementException, CandidacyStatusException {
		GestoreCandidatiProgetto gcp = new GestoreCandidatiProgetto();
		ArrayList<Iscritto> ll = proponentiTest();

		gcp.aggiungiCandidato((Progettista) ll.get(1).getRuoli().stream().findFirst().get());
		assertTrue(gcp.getCandidatiAlProgetto().contains(ll.get(1).getRuoli().stream().findFirst().get()));
		assertThrows(ExistingElementException.class,
				() -> gcp.aggiungiCandidato((Progettista) ll.get(1).getRuoli().stream().findFirst().get()));

		gcp.confermaCandidato(ll.get(1).getRuoli().stream().findFirst().get().getIscritto().getIdentificativo());
		assertTrue(gcp.getPartecipantiAlProgetto().contains(ll.get(1).getRuoli().stream().findFirst().get()));
		assertThrows(ExistingElementException.class,
				() -> gcp.aggiungiCandidato((Progettista) ll.get(1).getRuoli().stream().findFirst().get()));

	}

	/**
	 * Test method for
	 * {@link GestoreCandidatiProgetto#confermaCandidato(java.lang.String)}.
	 * @throws CandidacyStatusException 
	 * @throws ExistingElementException 
	 */
	@Test
	void testConfermaCandidato() throws ExistingElementException, CandidacyStatusException {
		GestoreCandidatiProgetto gcp = new GestoreCandidatiProgetto();
		ArrayList<Iscritto> ll = proponentiTest();
		
		gcp.aggiungiCandidato((Progettista) ll.get(1).getRuoli().stream().findFirst().get());
		gcp.confermaCandidato(ll.get(1).getRuoli().stream().findFirst().get().getIscritto().getIdentificativo());
		assertTrue(gcp.getPartecipantiAlProgetto().contains(ll.get(1).getRuoli().stream().findFirst().get()));
		assertFalse(gcp.getCandidatiAlProgetto().contains(ll.get(1).getRuoli().stream().findFirst().get()));

		assertThrows(NoSuchElementException.class, () -> gcp
				.confermaCandidato(ll.get(2).getRuoli().stream().findFirst().get().getIscritto().getIdentificativo()));
	}

	/**
	 * Test method for {@link GestoreCandidatiProgetto#chiudiCandidature()}.
	 */
	@Test
	void testChiudiCandidature() {
		GestoreCandidatiProgetto gcp = new GestoreCandidatiProgetto();
		ArrayList<Iscritto> ll = proponentiTest();
		
		assertTrue(gcp.isCandidatureAperte());
		gcp.chiudiCandidature();
		assertFalse(gcp.isCandidatureAperte());
		assertThrows(CandidacyStatusException.class,
				() -> gcp.aggiungiCandidato((Progettista) ll.get(1).getRuoli().stream().findFirst().get()));

	}

	private ArrayList<Iscritto> proponentiTest() {
		Persona p1 = new Persona("p1", "p1@p1.it", "p1", "p1", "p1", "p1", "p1", "p1");
		Persona p2 = new Persona("p2", "p2@p2.it", "p2", "p2", "p2", "p2", "p2", "p2");
		Persona p3 = new Persona("p3", "p3@p3.it", "p3", "p3", "p3", "p3", "p3", "p3");
		Progettista pr1 = new Progettista();
		Progettista pr2 = new Progettista();
		Progettista pr3 = new Progettista();
		pr1.setId(1);
		pr1.setIscritto(p1);
		pr2.setId(2);
		pr2.setIscritto(p2);
		pr3.setId(3);
		pr3.setIscritto(p3);
		p1.addRuolo(pr1);
		p2.addRuolo(pr2);
		p3.addRuolo(pr3);
		ArrayList<Iscritto> ll = new ArrayList<>();
		ll.add(p1);
		ll.add(p2);
		ll.add(p3);
		return ll;
	}

}
