package it.unicam.dmr.doit.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Questa classe implementa {@code AuthenticationEntryPoint} ed ha la
 * responsabilita' di verificare se l'utente/iscritto ha un token jwt valido. In
 * caso contrario viene inviato l'errore 401 (Non autorizzato).
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		System.out.println("Errore nel metodo commence - Utente non autorizzato");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non sei autorizzato");
	}

}
