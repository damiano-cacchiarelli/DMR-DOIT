package it.unicam.dmr.doit.service.progetto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagListDto;
import it.unicam.dmr.doit.progetto.InterfaceTag;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.repository.ProgettoRepository;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
import javassist.NotFoundException;

/**
 * Questa classe inietta ({@code @Autowired}) {@code ProgettoRepository} ed ha
 * la responsabilita' di effettuare le operazioni di modifica, inserimento,
 * ricerca ed eliminazione di un {@code Progetto}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class ProgettoService {

	@Autowired
	private ProgettoRepository progettoRepository;

	@Autowired
	private IscrittoRepository<Iscritto> iscrittoRepository;

	public List<Progetto> listaProgetti() {
		return progettoRepository.findAll();
	}

	public void salvaProgetto(Progetto p) {
		progettoRepository.save(p);
	}

	public boolean existsById(int idProgetto) {
		return progettoRepository.existsById(idProgetto);
	}

	public Progetto findById(int idProgetto) throws NotFoundException {
		return progettoRepository.findById(idProgetto)
				.orElseThrow(() -> new NotFoundException("Nessun progetto trovato"));
	}

	public List<Progetto> findByName(String nome, TagListDto tags) {
		List<Progetto> progetti = progettoRepository.findByNomeContainingIgnoreCase(nome);
		if (!progetti.isEmpty() && !tags.getTags().isEmpty()) {
			Set<String> tagRichiesti = getListName(tags.getTags());
			progetti = progetti.stream().filter(p -> getListName(p.getTags()).containsAll(tagRichiesti))
					.collect(Collectors.toList());
		}
		return progetti;
	}

	public Set<Progetto> findProgettiPersonali(String identificativo, List<RuoloDto> ruoli) throws NotFoundException {
		Set<Progetto> progetti = new HashSet<>();
		Iscritto iscritto = iscrittoRepository.findById(identificativo)
				.orElseThrow(() -> new NotFoundException("Iscritto inesistente"));
		List<Ruolo> ruoliIscritto = iscritto.getRuoli().stream().collect(Collectors.toList());
		if (!ruoliIscritto.isEmpty()) {
			if (ruoli.isEmpty())
				addAllRole(ruoli, iscritto.getTipoRuoliPossibili());
			for (RuoloDto ruoloDto : Set.copyOf(ruoli)) {
				for (Ruolo r : ruoliIscritto) {
					if (r.getRuolo().equals(ruoloDto.getRuolo())) {
						progetti.addAll(r.getProgettiPersonali());
					}
				}
			}
		}

		return progetti;
	}

	public Collection<Valutazione> getAllValutazioni(int idProgetto) throws NotFoundException {
		return findById(idProgetto).getListaValutazioni();
	}

	public void candidatiAlProgetto(String idIscritto, int idProgetto)
			throws NotFoundException, ExistingElementException, CandidacyStatusException{

		Progettista progettista = (Progettista) iscrittoRepository.findById(idIscritto)
				.orElseThrow(() -> new NotFoundException("Iscritto inesistente"))
				.getRuolo(TipologiaRuolo.ROLE_PROGETTISTA);
		Progetto progetto = progettoRepository.findById(idProgetto)
				.orElseThrow(() -> new NotFoundException("Progetto inesistente"));
		progetto.getGestoreCandidati().aggiungiCandidato(progettista);

	}

	private Set<String> getListName(Collection<? extends InterfaceTag> tags) {
		Set<String> listaNomiTag = new HashSet<>();
		tags.forEach(t -> listaNomiTag.add(t.getNome()));
		return listaNomiTag;
	}

	private void addAllRole(List<RuoloDto> ruoli, List<TipologiaRuolo> tipoRuoliPossibili) {
		for (TipologiaRuolo tr : tipoRuoliPossibili) {
			ruoli.add(new RuoloDto(tr));
		}
	}
}
