package it.unicam.dmr.doit.service.iscritto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.PersonaRepository;
import it.unicam.dmr.doit.utenti.Persona;
import javassist.NotFoundException;

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

	public Persona registra(PersonaDto personaDto) throws ExistingElementException {
		if (iscrittoRepository.existsById(personaDto.getIdentificativo()))
			throw new ExistingElementException("L'identificativo esiste gia'");

		Persona persona = new Persona(personaDto.getIdentificativo(), personaDto.getEmail(),
				passwordEncoder.encode(personaDto.getPassword()), personaDto.getNome(), personaDto.getCognome(),
				personaDto.getCittadinanza(), personaDto.getSesso(), personaDto.getTelefono());
		iscrittoRepository.save(persona);
		return persona;
	}
	
	public Persona aggiorna(String identificativo, PersonaDto personaDto) throws NotFoundException {
		if (!iscrittoRepository.existsById(personaDto.getIdentificativo()))
			throw new NotFoundException("L'identificativo non esiste");
		
		Persona persona = iscrittoRepository.findById(identificativo).get();
		persona.setNome(personaDto.getNome());
		persona.setCognome(personaDto.getCognome());
		persona.setCittadinanza(personaDto.getCittadinanza());
		persona.setSesso(personaDto.getSesso());
		persona.setTelefono(personaDto.getTelefono());
		iscrittoRepository.save(persona);
		return persona;
	}
}