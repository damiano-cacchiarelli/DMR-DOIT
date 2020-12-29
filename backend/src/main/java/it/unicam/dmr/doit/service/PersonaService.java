package it.unicam.dmr.doit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.repository.PersonaRepository;
import it.unicam.dmr.doit.utenti.Persona;

@Service
@Transactional
public class PersonaService extends IscrittoService<Persona, PersonaRepository>{

}