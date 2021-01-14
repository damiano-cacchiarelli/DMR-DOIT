package it.unicam.dmr.doit.controller.progetto;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagListDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.service.progetto.TagService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Ricercare un progetto tramite id;</li>
 * <li>Ricercare un progetto tramite nome;</li>
 * <li>Ottenere la lista di tutti i progetti;</li>
 * <li>Ottenere i progetti relativi ad un iscritto (o un ruolo);</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/progetto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProgetto {

	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private TagService tagService;
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;

	@GetMapping("/vetrina")
	public ResponseEntity<List<Progetto>> vetrinaProgetti() {
		return new ResponseEntity<>(progettoService.listaProgetti(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> progetto(@PathVariable("id") int idProgetto) {
		if (!progettoService.existsById(idProgetto)) {
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(progettoService.findById(idProgetto).get(), HttpStatus.OK);
	}

	@PostMapping("/ricerca/{nome}")
	public ResponseEntity<?> ricercaProgettoNome(@PathVariable("nome") String nome, @Valid @RequestBody TagListDto tags,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		List<Progetto> progetti = progettoService.findByName(nome);
		List<Tag> tagRichiesti = getTag(tags);
		progetti = progetti.stream().filter(p -> p.getTags().containsAll(tagRichiesti)).collect(Collectors.toList());

		return new ResponseEntity<>(progetti, HttpStatus.OK);
	}

	@GetMapping("/personali")
	public ResponseEntity<?> progettiPersonali(@Valid @RequestBody List<RuoloDto> ruoli, BindingResult bindingResult,
			Authentication authentication) {

		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);

		Set<Progetto> progetti = new HashSet<>();
		Iscritto iscritto = iscrittoService.findByIdentificativo(authentication.getName()).get();
		List<Ruolo> ruoliIscritto = iscritto.getRuoli().stream().collect(Collectors.toList());
		if (ruoli.isEmpty())
			addAllRole(ruoli, iscritto.getTipoRuoliPossibili());
		for (RuoloDto ruoloDto : Set.copyOf(ruoli)) {
			for (Ruolo r : ruoliIscritto) {
				if (r.getRuolo().equals(ruoloDto.getRuolo())) {
					progetti.addAll(r.getProgettiPersonali());
				}
			}
		}
		/*
		 * for (RuoloDto ruoloDto : ruoli) { if
		 * (!ruoliUsati.contains(ruoloDto.getRuolo()) && listContainRole(ruoliIscritto,
		 * ruoloDto.getRuolo())) { progetti.addAll(ruoliIscritto.stream().filter(r ->
		 * r.getRuolo().equals(ruoloDto.getRuolo())).findFirst()
		 * .get().getProgettiPersonali()); ruoliUsati.add(ruoloDto.getRuolo()); }
		 * 
		 * }
		 */
		return new ResponseEntity<>(progetti, HttpStatus.OK);
	}

	private List<Tag> getTag(TagListDto tags) {
		List<Tag> listaTag = new LinkedList<>();
		tags.getTags().forEach(t -> {
			if (tagService.existsByNome(t.getNome()))
				listaTag.add(tagService.findById(t.getNome()));
		});
		return listaTag;
	}

	private void addAllRole(List<RuoloDto> ruoli, List<TipologiaRuolo> tipoRuoliPossibili) {
		for (TipologiaRuolo tr : tipoRuoliPossibili) {
			ruoli.add(new RuoloDto(tr));
		}
	}

}
