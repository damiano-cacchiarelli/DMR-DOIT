package it.unicam.dmr.doit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.service.ProgettoService;

@RestController
@RequestMapping("/progetto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProgetto {

	@Autowired
	ProgettoService progettoService;

	@GetMapping("/vetrina")
	public ResponseEntity<List<Progetto>> vetrinaProgetti() {
		return new ResponseEntity<>(progettoService.listaProgetti(), HttpStatus.OK);

	}
	
	@GetMapping("/progetto/{id}")
	public ResponseEntity<?> progetto(@PathVariable("id") int idProgetto){
		if(!progettoService.existsById(idProgetto)) {
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(progettoService.findById(idProgetto), HttpStatus.OK);
	}
	
	@PostMapping("/proponi")
	public ResponseEntity<Messaggio> proponiProgetto(@RequestBody ProgettoDto progetto){
		Progetto p = new Progetto();
		progettoService.salvaProgetto(p);
		return new ResponseEntity<>(new Messaggio("Progetto inserito"), HttpStatus.OK);
	}
	
	

}
