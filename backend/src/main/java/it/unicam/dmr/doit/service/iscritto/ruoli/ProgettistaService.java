package it.unicam.dmr.doit.service.iscritto.ruoli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.TipologiaRisposta;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
import javassist.NotFoundException;

/**
 * Questa classe ha la responsabilita' di effettuare le operazioni relative alla
 * candidatura di un {@code Progettista}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 *
 */
@Service
@Transactional
public class ProgettistaService {

	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private InvitoService invitoService;

	public void candidatiAlProgetto(String idIscrittoProgettista, InvitoDto invitoDto)
			throws NotFoundException, ExistingElementException, CandidacyStatusException, IllegalArgumentException {
		Progettista progettista = (Progettista) iscrittoService.getByRuolo(TipologiaRuolo.ROLE_PROGETTISTA,
				idIscrittoProgettista);
		Progetto progetto = progettoService.findById(invitoDto.getIdProgetto());
		if (progetto.getIdProponente().equals(idIscrittoProgettista))
			throw new IllegalArgumentException("Non puoi candidarti ad un tuo progetto.");
		progetto.getGestoreCandidati().aggiungiCandidato(progettista);
		invitoService.invia(invitoDto, idIscrittoProgettista);

		progettoService.salva(progetto);

	}

	public void gestisciPropostaPartecipazione(RispostaInvitoDto rispostaInvitoDto, String idIscrittoProgettista)
			throws IllegalStateException, IllegalArgumentException, NotFoundException {
		Invito invito = invitoService.getInvito(rispostaInvitoDto.getIdInvito(), idIscrittoProgettista);
		Progetto progetto = progettoService.findById(invito.getIdProgetto());
		if (rispostaInvitoDto.getRisposta().equals(TipologiaRisposta.RIFIUTATA)) {
			progetto.getGestoreCandidati().rimuoviProgettista(idIscrittoProgettista);
		}
		/*
		 * Invito invito =
		 * invitoRepository.findById(rispostaInvitoDto.getIdInvito()).get(0).get();
		 * Progetto p = progettoRepository.findById(invito.getIdProgetto()).get(); if
		 * (invito.getTipologiaInvito() == TipologiaInvito.PROPOSTA ||
		 * invito.getTipologiaInvito() == TipologiaInvito.RICHIESTA) { if
		 * (invito.getTipologiaRisposta() == TipologiaRisposta.ACCETTATA) { Iscritto
		 * iscritto = iscrittoRepository.findById(identificativoIscritto).get();
		 * Progettista pr = (Progettista)
		 * iscritto.getRuolo(TipologiaRuolo.ROLE_PROGETTISTA);
		 * p.getGestoreCandidati().aggiungiCandidato(pr); if
		 * (invito.getTipologiaInvito() == TipologiaInvito.PROPOSTA)
		 * p.getGestoreCandidati().confermaCandidato(identificativoIscritto); } }
		 */
		invitoService.gestisci(rispostaInvitoDto, idIscrittoProgettista);
		progettoService.salva(progetto);

	}
}
