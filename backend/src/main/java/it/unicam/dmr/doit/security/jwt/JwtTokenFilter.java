package it.unicam.dmr.doit.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import it.unicam.dmr.doit.service.security.IscrittoDetailsServiceImpl;

/**
 * Questa classe estende {@code OncePerRequestFilter} ed ha la responsabilita'
 * di verifica la validità del token tramite il provider; in caso affermativo
 * permette la visualizzazione della risorsa altrimenti lancia un eccezione.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public class JwtTokenFilter extends OncePerRequestFilter {
	
	public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
	public static final String TOKEN_BEARER_HEADER_NAME = "Bearer ";

	
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private IscrittoDetailsServiceImpl iscrittoDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(request);
			if (token != null && jwtProvider.verificaToken(token)) {
				String identificativoIscritto = jwtProvider.getIdentificativoFromToken(token);
				UserDetails userDetails = iscrittoDetailsServiceImpl.loadUserByUsername(identificativoIscritto);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			System.out.println("Errore nel metodo doFilterInternal: " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
		if (header != null && header.startsWith(TOKEN_BEARER_HEADER_NAME.trim()))
			return header.replace(TOKEN_BEARER_HEADER_NAME, "");
		return null;
	}
}
