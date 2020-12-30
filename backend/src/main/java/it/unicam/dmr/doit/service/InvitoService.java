package it.unicam.dmr.doit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.repository.InvitoRepository;

@Service
@Transactional
public class InvitoService {

	@Autowired
	private InvitoRepository ir;

	public List<Invito> listaInviti(String identificativoIscritto) {

		return ir.findAll().stream().filter(i -> i.getIdDestinatario().equals(identificativoIscritto))
				.collect(Collectors.toList());
	}
	
	public void salvaInvito(Invito invito) {
		ir.save(invito);
	}
	
	public void eliminaInvito(int invito) {
		ir.deleteById(invito);
	}
	
	public boolean esisteInvito(int idInvito) {
		return ir.existsById(idInvito);
	}
}
