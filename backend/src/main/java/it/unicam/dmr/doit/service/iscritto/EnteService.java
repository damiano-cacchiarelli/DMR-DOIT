package it.unicam.dmr.doit.service.iscritto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.EnteRepository;
import it.unicam.dmr.doit.utenti.Ente;
import javassist.NotFoundException;

/**
 * Questa classe estende {@code IscrittoService<Ente, EnteRepository>} ed ha la
 * responsabilita' di effettuare le operazioni di modifica, inserimento, ricerca
 * ed eliminazione di un {@code Ente}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class EnteService extends IscrittoService<Ente, EnteRepository> {

	public Ente registra(EnteDto enteDto) throws ExistingElementException {
		if(iscrittoRepository.esiste(enteDto.getIdentificativo(), enteDto.getEmail()) != null)
			throw new ExistingElementException("L'identificativo o l'email sono gia' in uso.");
		
		Ente ente = new Ente(enteDto.getIdentificativo(), enteDto.getEmail(),
				passwordEncoder.encode(enteDto.getPassword()), enteDto.getSede(), enteDto.getAnnoDiFondazione());
		iscrittoRepository.save(ente);
		return ente;
	}

	public Ente aggiorna(String identificativo, EnteDto enteDto) throws NotFoundException {
		Ente ente = iscrittoRepository.findById(identificativo)
				.orElseThrow(() -> new NotFoundException("L'identificativo non esiste"));
		ente.setSede(enteDto.getSede());
		ente.setAnnoDiFondazione(enteDto.getAnnoDiFondazione());
		iscrittoRepository.save(ente);
		return ente;
	}
}
