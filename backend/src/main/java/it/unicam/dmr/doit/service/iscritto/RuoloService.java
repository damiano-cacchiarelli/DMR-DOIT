package it.unicam.dmr.doit.service.iscritto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.repository.RuoloRepository;

@Service
@Transactional
public class RuoloService {

	@Autowired
	protected RuoloRepository ruoloRepository;
	
	
}
