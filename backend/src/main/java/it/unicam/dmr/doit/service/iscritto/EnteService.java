package it.unicam.dmr.doit.service.iscritto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.repository.EnteRepository;
import it.unicam.dmr.doit.utenti.Ente;

@Service
@Transactional
public class EnteService extends IscrittoService<Ente, EnteRepository>{

}

