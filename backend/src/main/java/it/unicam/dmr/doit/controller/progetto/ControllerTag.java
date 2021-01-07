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
 * Responabilita :
* - restituire tutti i tag disposnibili
* - restituire un determinato tag (?)
* - aggiungere tag (non implementare)
* - rimuovere tag (non implementare)
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
