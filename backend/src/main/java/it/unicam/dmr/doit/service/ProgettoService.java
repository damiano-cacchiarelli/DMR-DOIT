package it.unicam.dmr.doit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.repository.ProgettoRepository;

@Service
@Transactional
public class ProgettoService {
	
	@Autowired
	ProgettoRepository pr;
	
	public List<Progetto> listaProgetti(){
		return pr.findAll();
	}
	
}
