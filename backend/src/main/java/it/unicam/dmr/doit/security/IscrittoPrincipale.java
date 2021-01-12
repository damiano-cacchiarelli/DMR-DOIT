package it.unicam.dmr.doit.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Questa classe implementa {@code UserDetails} ed ha la responsabilita' di
 * rappresentare un {@code Iscritto} contenente la lista delle autorizzazioni.
 * La lista delle autorizzazioni derivano dalla conversione dei ruoli
 * dell'{@code Iscritto} nella classe {@code GrantedAuthority}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@SuppressWarnings("serial")
public class IscrittoPrincipale implements UserDetails {
	private String identificativo;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public IscrittoPrincipale(String identificativo, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.identificativo = identificativo;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static IscrittoPrincipale build(Iscritto iscritto) {
		List<GrantedAuthority> authorities = iscritto.getRuoli().stream()
				.map(ruolo -> new SimpleGrantedAuthority(ruolo.getRuolo().name())).collect(Collectors.toList());
		return new IscrittoPrincipale(iscritto.getIdentificativo(), iscritto.getEmail(), iscritto.getPassword(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return identificativo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getEmail() {
		return email;
	}
}
