package it.unicam.dmr.doit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Questa classe avvia il server spring boot tramite la funzione
 * {@code SpringApplication.run}.
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

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200", "http://207.154.220.231",
						"http://207.154.220.231:80", "http://localhost:80");
			}
		};
	}
}
