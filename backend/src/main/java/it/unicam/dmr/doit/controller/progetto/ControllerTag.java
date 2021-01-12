package it.unicam.dmr.doit.controller.progetto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.service.progetto.TagService;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Restituire tutti i tag disposnibili;</li>
 * <li>Restituire un determinato tag;</li>
 * <li>Aggiungere tag (non implementato);</li>
 * <li>Rimuovere tag (non implementato);</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/tag")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerTag {
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/all")
	public List<Tag> getAllTags(){
		return tagService.findAllTag();
	}

}
