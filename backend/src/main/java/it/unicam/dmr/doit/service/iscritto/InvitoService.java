package it.unicam.dmr.doit.service.iscritto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.invito.EliminazioneInvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.RuoloSoggetto;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.invito.TipologiaRisposta;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.repository.InvitoRepository;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import javassist.NotFoundException;

/**
 * Questa classe inietta ({@code @Autowired}) {@code InvitoRepository} ed ha la
 * responsabilita' di effettuare le operazioni di modifica, inserimento, ricerca
 * ed eliminazione di un {@code Invito}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class InvitoService {

	@Autowired
	private InvitoRepository invitoRepository;
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;

	public Invito getInvito(String idInvito, String identificativoIscritto) throws NotFoundException {
		if (!invitoRepository.existsById(idInvito))
			throw new NotFoundException("Invito inesistente");
		iscrittoService.findById(identificativoIscritto);

		List<Invito> listaInviti = listaInviti(identificativoIscritto);
		Invito invito = listaInviti.stream().filter(i -> i.getId().equals(idInvito)).findFirst()
				.orElseThrow(() -> new NotFoundException("Invito inesistente"));

		return invito;
	}

	public void invia(InvitoDto invitoDto, String identificativoIscritto)
			throws NotFoundException, IllegalArgumentException {

		for (String idDest : invitoDto.getIdDestinatario())
			iscrittoService.findById(idDest);

		Progetto progetto = progettoService.findById(invitoDto.getIdProgetto());
		Iscritto mittente = iscrittoService.findById(identificativoIscritto);
		for (String idDest : invitoDto.getIdDestinatario()) {
			invia(idDest, invitoDto.getContenuto(), progetto, invitoDto.getTipologiaInvito(), mittente);
		}
	}

	public boolean invia(String idDest, String contenuto, Progetto progetto, TipologiaInvito tipologiaInvito,
			Iscritto mittente) throws NotFoundException, IllegalArgumentException {
		/*
		 * for (String idDest : invitoDto.getIdDestinatario()) if
		 * (!iscrittoRepository.existsById(idDest)) throw new
		 * NotFoundException("Id destinatario inesistente"); if
		 * (!progettoRepository.existsById(invitoDto.getIdProgetto())) throw new
		 * NotFoundException("Progetto inesistente");
		 */

		if (mittente.getIdentificativo().equals(idDest))
			return false;
		Iscritto destinatario = iscrittoService.findById(idDest);
		mittente.getGestoreMessaggi().inviaMessaggio(destinatario, contenuto, progetto, tipologiaInvito);
		iscrittoService.salva(mittente);
		iscrittoService.salva(destinatario);
		return true;

	}

	public void elimina(EliminazioneInvitoDto eliminazioneInvitoDto, String identificativoIscritto)
			throws NotFoundException, IllegalStateException {
		if (!invitoRepository.existsById(eliminazioneInvitoDto.getIdInvito()))
			throw new NotFoundException("Invito inesistente");

		Iscritto iscritto = iscrittoService.findById(identificativoIscritto);
		Invito i = invitoRepository.findById(eliminazioneInvitoDto.getIdInvito()).get(0).get();
		Iscritto mittente = i.getMittente();
		Iscritto destinatario = i.getDestinatario();
		if (!mittente.getIdentificativo().equals(identificativoIscritto)
				&& !destinatario.getIdentificativo().equals(identificativoIscritto)) {
			throw new IllegalStateException("Impossibile eliminare l'invito (non sei autorizzato)");
		}
		iscritto.getGestoreMessaggi().eliminaMessaggio(eliminazioneInvitoDto.getIdInvito(),
				eliminazioneInvitoDto.isOpzioni());
		if (mittente.getIdentificativo().equals(identificativoIscritto)) {
			if (eliminazioneInvitoDto.isOpzioni())
				invitoRepository.deleteByIdAndSoggettoIn(eliminazioneInvitoDto.getIdInvito(),
						List.of(RuoloSoggetto.MITTENTE, RuoloSoggetto.DESTINATARIO));
			else
				invitoRepository.deleteByIdAndSoggettoIn(eliminazioneInvitoDto.getIdInvito(),
						List.of(RuoloSoggetto.MITTENTE));
		} else
			invitoRepository.deleteByIdAndSoggettoIn(eliminazioneInvitoDto.getIdInvito(),
					List.of(RuoloSoggetto.DESTINATARIO));
	}

	public void gestisci(RispostaInvitoDto rispostaInvitoDto, String identificativoIscritto)
			throws NotFoundException, IllegalStateException, IllegalArgumentException {
		if (!invitoRepository.existsById(rispostaInvitoDto.getIdInvito()))
			throw new NotFoundException("Invito inesistente");
		if (!iscrittoService.existsById(identificativoIscritto))
			throw new NotFoundException("Identificativo iscritto inesistente.");

		Invito invito = invitoRepository.findById(rispostaInvitoDto.getIdInvito()).get(0).get();
		if (!invito.getIdDestinatario().equals(identificativoIscritto))
			throw new IllegalStateException(
					"Non sei autorizzato a rispondere a questo invito (non sei il destinatario)");
		if (!invito.getTipologiaRisposta().equals(TipologiaRisposta.IN_ATTESA))
			throw new IllegalStateException("L'invito ha gia' ricevuto una risposta.");

		List<Invito> inviti = invitoRepository.findById(rispostaInvitoDto.getIdInvito()).stream().map(Optional::get)
				.collect(Collectors.toList());

		inviti.forEach(i -> i.setTipologiaRisposta(rispostaInvitoDto.getRisposta()));
		inviti.forEach(i -> invitoRepository.save(i));
	}

	/**
	 * Ricerca tutti gli inviti che sono riferiti ad un iscritto (l'iscritto e' il
	 * mittente o il destinatario dell'invito).
	 * 
	 * @param identificativoIscritto
	 * @return la lista degli inviti relativi l'iscritto
	 */
	public List<Invito> listaInviti(String identificativoIscritto) {
		return invitoRepository.findAll().stream().filter(i -> {
			if (i.getMittente().getIdentificativo().equals(identificativoIscritto)
					&& i.getSoggetto().equals(RuoloSoggetto.MITTENTE))
				return true;
			else if (i.getDestinatario().getIdentificativo().equals(identificativoIscritto)
					&& i.getSoggetto().equals(RuoloSoggetto.DESTINATARIO))
				return true;
			else
				return false;
		}).collect(Collectors.toList());
	}
}
