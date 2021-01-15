package it.unicam.dmr.doit.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.security.IscrittoPrincipale;
import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Questa classe implementa l'interfaccia {@code UserDetailsService} ed ha la
 * responsabilita' di caricare i dati relativi ad un {@code Iscritto};
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
public class IscrittoDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IscrittoRepository<Iscritto> iscrittoRepository;

	@Override
	public UserDetails loadUserByUsername(String identificativo) throws UsernameNotFoundException {
		Iscritto iscritto = iscrittoRepository.findById(identificativo).get();
		return IscrittoPrincipale.build(iscritto);
	}
}
