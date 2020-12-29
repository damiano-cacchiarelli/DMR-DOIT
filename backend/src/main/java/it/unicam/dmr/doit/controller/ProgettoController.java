package it.unicam.dmr.doit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.service.ProgettoService;

@RestController
@RequestMapping("/progetto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProgettoController {

	@Autowired
	ProgettoService ps;

	@GetMapping("/vetrina")
	public ResponseEntity<List<Progetto>> vetrinaProgetti() {

		return new ResponseEntity<>(ps.listaProgetti(), HttpStatus.OK);

	}

}
