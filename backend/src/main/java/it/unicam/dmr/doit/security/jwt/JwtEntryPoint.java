package it.unicam.dmr.doit.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Verifica se l'utente/iscritto ha un token valido. In caso contrario invia un
 * errore 401 (Non autorizzato)
 */
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(JwtEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Errore nel metodo commence");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non sei autorizzato");
	}

}
