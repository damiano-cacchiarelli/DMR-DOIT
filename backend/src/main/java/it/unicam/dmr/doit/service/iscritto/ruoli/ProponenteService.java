package it.unicam.dmr.doit.service.iscritto.ruoli;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagDto;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.TipologiaRisposta;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.progetto.exception.ProjectStatusException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.service.progetto.TagService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
import javassist.NotFoundException;

@Service
@Transactional
public class ProponenteService {

	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private InvitoService invitoService;
	@Autowired
	private TagService tagService;

	public void invitaProgettisti(InvitoDto invitoDto, String idIscrittoProponente)
			throws NoSuchElementException, NotFoundException, ExistingElementException {
		Progetto progetto = progettoService.findById(invitoDto.getIdProgetto());
		for (String idIscrittoProgettista : invitoDto.getIdDestinatario()) {
			Progettista progettista = (Progettista) iscrittoService.getByRuolo(TipologiaRuolo.ROLE_PROGETTISTA,
					idIscrittoProgettista);
			progetto.getGestoreCandidati().aggiungiPartecipante(progettista);
		}

		progettoService.salva(progetto);
		invitoService.invia(invitoDto, idIscrittoProponente);
	}

	public Progetto proponi(ProgettoDto progettoDto, String idIscrittoProponente)
			throws NoSuchElementException, NotFoundException {

		Proponente proponente = (Proponente) iscrittoService.getByRuolo(TipologiaRuolo.ROLE_PROPONENTE,
				idIscrittoProponente);
		Set<Tag> tags = tagService
				.getTags(progettoDto.getTags().stream().map(TagDto::getNome).collect(Collectors.toList()));
		Progetto progetto = new Progetto(progettoDto.getNome(), progettoDto.getObiettivi(), progettoDto.getRequisiti(),
				proponente, tags);
		progettoService.salva(progetto);
		return progetto;
	}

	public void chiudiCandidature(int idProgetto) throws NotFoundException, CandidacyStatusException {

		Progetto progetto = progettoService.findById(idProgetto);
		if (!progetto.getGestoreCandidati().isCandidatureAperte())
			new CandidacyStatusException("Le candidature sono gia' chiuse");
		progetto.getGestoreCandidati().chiudiCandidature();
		progettoService.salva(progetto);
	}

	public Progetto faseSuccessiva(int idProgetto) throws NotFoundException, NextFaseException {
		Progetto progetto = progettoService.findById(idProgetto);
		progetto.nextFase();
		if (progetto.getGestoreCandidati().isCandidatureAperte())
			progetto.getGestoreCandidati().chiudiCandidature();
		progettoService.salva(progetto);
		return progetto;

	}

	public void permetteValutazione(@Valid InvitoDto invitoDto, String idIscrittoProponente)
			throws IllegalArgumentException, NotFoundException, ProjectStatusException {

		if (!progettoService.isValutabile(invitoDto.getIdProgetto()))
			throw new IllegalArgumentException("Richiesta di valutazione gia' effettuata.");

		invitoService.invia(invitoDto, idIscrittoProponente);
		progettoService.setInValutazione(invitoDto.getIdProgetto());

	}

	public void selezionaCandidato(@Valid RispostaInvitoDto rispostaInvitoDto, String idIscrittoProponente)
			throws IllegalStateException, IllegalArgumentException, NotFoundException {
		invitoService.gestisci(rispostaInvitoDto, idIscrittoProponente);
		Invito invito = invitoService.getInvito(rispostaInvitoDto.getIdInvito(), idIscrittoProponente);
		Progetto progetto = progettoService.findById(invito.getIdProgetto());
		if (rispostaInvitoDto.getRisposta() == TipologiaRisposta.ACCETTATA) {
			progetto.getGestoreCandidati().confermaCandidato(invito.getIdMittente());
		} else {
			progetto.getGestoreCandidati().rimuoviProgettista(invito.getIdMittente());
		}
		progettoService.salva(progetto);
	}

}
