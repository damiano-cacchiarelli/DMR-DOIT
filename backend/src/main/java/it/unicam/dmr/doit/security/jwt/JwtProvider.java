package it.unicam.dmr.doit.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import it.unicam.dmr.doit.security.IscrittoPrincipale;

/**
 * Questa classe ha la responsabilita' di creare il token jwt, ottenere le
 * informazioni dal token jwt e verificare la validità del token (a livello
 * sintattico).
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Component
public class JwtProvider {

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		IscrittoPrincipale iscrittoPrincipale = (IscrittoPrincipale) authentication.getPrincipal();
		List<String> ruoli = iscrittoPrincipale.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return Jwts.builder().setSubject(iscrittoPrincipale.getUsername()).claim("ruoli", ruoli) // aggiunge il campo
																									// ruoli con la
																									// lista dei ruoli
				.setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public String getIdentificativoFromToken(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token malformato");
		} catch (UnsupportedJwtException e) {
			logger.error("Token non supportato");
		} catch (ExpiredJwtException e) {
			logger.error("Token scaduto");
		} catch (IllegalArgumentException e) {
			logger.error("Token vuoto");
		} catch (SignatureException e) {
			logger.error("Errore nella firma");
		}
		return false;
	}
}
