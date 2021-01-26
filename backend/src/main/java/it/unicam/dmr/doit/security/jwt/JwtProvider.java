package it.unicam.dmr.doit.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
 * informazioni dal token jwt e verificare la validita' del token (a livello
 * sintattico).
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;

	public String generaToken(Authentication authentication) {
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

	public boolean verificaToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			System.out.println("Token malformato " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("Token non supportato " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("Token scaduto " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Token vuoto " + e.getMessage());
		} catch (SignatureException e) {
			System.out.println("Errore nella firma " + e.getMessage());
		}
		return false;
	}
}
