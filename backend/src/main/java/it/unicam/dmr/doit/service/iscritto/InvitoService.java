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
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.InvitoRepository;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.repository.ProgettoRepository;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
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
	private IscrittoRepository<Iscritto> iscrittoRepository;
	@Autowired
	private ProgettoRepository progettoRepository;

	public Invito getInvito(String idInvito, String identificativoIscritto) throws NotFoundException {
		if (!invitoRepository.existsById(idInvito))
			throw new NotFoundException("Invito inesistente");
		if (!iscrittoRepository.existsById(identificativoIscritto))
			throw new NotFoundException("Identificativo iscritto inesistente");

		List<Invito> listaInviti = listaInviti(identificativoIscritto);
		Optional<Invito> invito = listaInviti.stream().filter(i -> i.getId().equals(idInvito)).findFirst();
		if (invito.isEmpty())
			throw new NotFoundException("Invito inesistente");
		return invito.get();
	}

	public void invia(InvitoDto invitoDto, String identificativoIscritto)
			throws NotFoundException, IllegalArgumentException {
		for (String idDest : invitoDto.getIdDestinatario())
			if (!iscrittoRepository.existsById(idDest))
				throw new NotFoundException("Id destinatario inesistente");
		if (!progettoRepository.existsById(invitoDto.getIdProgetto()))
			throw new NotFoundException("Progetto inesistente");

		for (String idDest : invitoDto.getIdDestinatario()) {
			if (identificativoIscritto.equals(idDest))
				continue;
			Iscritto mittente = iscrittoRepository.findById(identificativoIscritto).get();
			Iscritto destinatario = iscrittoRepository.findById(idDest).get();
			Progetto progetto = progettoRepository.findById(invitoDto.getIdProgetto()).get();
			mittente.getGestoreMessaggi().inviaMessaggio(destinatario, invitoDto.getContenuto(), progetto,
					invitoDto.getTipologiaInvito());

			iscrittoRepository.save(mittente);
			iscrittoRepository.save(destinatario);
		}
	}

	public void elimina(EliminazioneInvitoDto eliminazioneInvitoDto, String identificativoIscritto)
			throws NotFoundException, IllegalStateException {
		if (!invitoRepository.existsById(eliminazioneInvitoDto.getIdInvito()))
			throw new NotFoundException("Invito inesistente");
		if (!iscrittoRepository.existsById(identificativoIscritto))
			throw new NotFoundException("Identificativo iscritto inesistente");

		Invito i = invitoRepository.findById(eliminazioneInvitoDto.getIdInvito()).get(0).get();
		Iscritto mittente = i.getMittente();
		Iscritto destinatario = i.getDestinatario();
		if (!mittente.getIdentificativo().equals(identificativoIscritto)
				&& !destinatario.getIdentificativo().equals(identificativoIscritto)) {
			throw new IllegalStateException("Impossibile eliminare l'invito (non sei autorizzato)");
		}
		Iscritto iscritto = iscrittoRepository.findById(identificativoIscritto).get();
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
		if (!iscrittoRepository.existsById(identificativoIscritto))
			throw new NotFoundException("Identificativo iscritto inesistente");

		Invito invito = invitoRepository.findById(rispostaInvitoDto.getIdInvito()).get(0).get();
		if (!invito.getIdDestinatario().equals(identificativoIscritto))
			throw new IllegalStateException(
					"Non sei autorizzato a rispondere a questo invito (non sei il destinatario)");

		List<Invito> inviti = invitoRepository.findById(rispostaInvitoDto.getIdInvito()).stream().map(Optional::get)
				.collect(Collectors.toList());

		//TODO: creare un metodo separato come per gestisciRichiestePartecipazione?
		if (invito.getTipologiaInvito().equals(TipologiaInvito.VALUTAZIONE)
				&& rispostaInvitoDto.getRisposta().equals(TipologiaRisposta.RIFIUTATA)) {
			Progetto progetto = progettoRepository.findById(invito.getIdProgetto())
					.orElseThrow(() -> new NotFoundException("Progetto non trovato"));
			if(progetto.getListaValutazioni().size()>0) {
				progetto.setStato(Stato.VALUTATO);
			}else {
				progetto.setStato(Stato.NON_VALUTATO);
			}
			progettoRepository.save(progetto);
		}
		inviti.forEach(i -> i.setTipologiaRisposta(rispostaInvitoDto.getRisposta()));
		inviti.forEach(i -> invitoRepository.save(i));
	}

	public void gestisciRichiestePartecipazione(RispostaInvitoDto rispostaInvitoDto, String identificativoIscritto)
			throws IllegalStateException, IllegalArgumentException, NotFoundException, ExistingElementException,
			CandidacyStatusException {
		gestisci(rispostaInvitoDto, identificativoIscritto);

		Invito invito = invitoRepository.findById(rispostaInvitoDto.getIdInvito()).get(0).get();
		Progetto p = progettoRepository.findById(invito.getIdProgetto()).get();
		if (invito.getTipologiaInvito() == TipologiaInvito.PROPOSTA
				|| invito.getTipologiaInvito() == TipologiaInvito.RICHIESTA) {
			if (invito.getTipologiaRisposta() == TipologiaRisposta.ACCETTATA) {
				Iscritto iscritto = iscrittoRepository.findById(identificativoIscritto).get();
				Progettista pr = (Progettista) iscritto.getRuolo(TipologiaRuolo.ROLE_PROGETTISTA);
				p.getGestoreCandidati().aggiungiCandidato(pr);
				if (invito.getTipologiaInvito() == TipologiaInvito.PROPOSTA)
					p.getGestoreCandidati().confermaCandidato(identificativoIscritto);
			}
		}
		progettoRepository.save(p);
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
