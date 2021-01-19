package it.unicam.dmr.doit.service.iscritto.ruoli;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneDto;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.invito.TipologiaRisposta;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.progetto.exception.ProjectStatusException;
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.service.progetto.ValutazioneService;
import javassist.NotFoundException;

@Service
public class EspertoService {

	@Autowired
	private InvitoService invitoService;
	@Autowired
	private ValutazioneService valutazioneService;
	@Autowired
	private ProgettoService progettoService;

	public void valuta(String idInvito, ValutazioneDto valutazioneDto, String idIscrittoEsperto)
			throws IllegalStateException, IllegalArgumentException, NotFoundException, NoSuchElementException,
			ExistingElementException, ProjectStatusException {
		invitoService.gestisci(new RispostaInvitoDto(idInvito, TipologiaRisposta.ACCETTATA), idIscrittoEsperto);
		valutazioneService.valuta(idInvito, valutazioneDto, idIscrittoEsperto);

	}

	public void rifiuta(String idInvito, String idIscittoEsperto) throws IllegalStateException, IllegalArgumentException, NotFoundException {
		invitoService.gestisci(new RispostaInvitoDto(idInvito, TipologiaRisposta.RIFIUTATA), idIscittoEsperto);
		Invito invito = invitoService.getInvito(idInvito, idIscittoEsperto);
		if (invito.getTipologiaInvito().equals(TipologiaInvito.VALUTAZIONE)) {
			Progetto progetto = progettoService.findById(invito.getIdProgetto());
			if (progetto.getListaValutazioni().size() > 0) {
				progetto.setStato(Stato.VALUTATO);
			} else {
				progetto.setStato(Stato.NON_VALUTATO);
			}
			progettoService.salva(progetto);
		}
	}

}
