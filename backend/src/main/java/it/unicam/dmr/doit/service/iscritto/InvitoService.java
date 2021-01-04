package it.unicam.dmr.doit.service.iscritto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.InvitoId.RuoloSoggetto;
import it.unicam.dmr.doit.repository.InvitoRepository;

@Service
@Transactional
public class InvitoService {

	@Autowired
	private InvitoRepository invitoRepository;

	public List<Invito> listaInviti(String identificativoIscritto) {
		return invitoRepository.findAll().stream()
				.filter(i -> i.getDestinatario().getIdentificativo().equals(identificativoIscritto)
						|| i.getMittente().getIdentificativo().equals(identificativoIscritto))
				.collect(Collectors.toList());
	}

	public void salvaInvito(Invito invito) {
		invitoRepository.save(invito);
	}

	public void eliminaInvito(String invito, List<RuoloSoggetto> soggetti) {
		invitoRepository.deleteByIdAndSoggettoIn(invito, soggetti);
	}

	public boolean esisteInvito(String idInvito) {
		return invitoRepository.existsById(idInvito);
	}

	public Invito getInvito(String idInvito) {
		return invitoRepository.findById(idInvito).get(0);
	}
}
