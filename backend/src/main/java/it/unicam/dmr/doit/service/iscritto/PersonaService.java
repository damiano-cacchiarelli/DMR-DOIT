package it.unicam.dmr.doit.service.iscritto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.repository.PersonaRepository;
import it.unicam.dmr.doit.utenti.Persona;

/**
 * Questa classe estende {@code IscrittoService<Persona, PersonaRepository>} ed
 * ha la responsabilita' di effettuare le operazioni di modifica, inserimento,
 * ricerca ed eliminazione di un {@code Persona}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class PersonaService extends IscrittoService<Persona, PersonaRepository> {

}