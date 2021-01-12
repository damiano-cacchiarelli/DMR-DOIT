package it.unicam.dmr.doit.service.iscritto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.repository.EnteRepository;
import it.unicam.dmr.doit.utenti.Ente;

/**
 * Questa classe estende {@code IscrittoService<Ente, EnteRepository>} ed ha
 * la responsabilita' di effettuare le operazioni di modifica, inserimento,
 * ricerca ed eliminazione di un {@code Ente}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class EnteService extends IscrittoService<Ente, EnteRepository> {

}
