package it.unicam.dmr.doit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Questa classe avvia il server spring boot tramite la funzione {@code SpringApplication.run}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@SpringBootApplication
public class Doit {

	public static void main(String[] args) {
		SpringApplication.run(Doit.class, args);
	}
}
