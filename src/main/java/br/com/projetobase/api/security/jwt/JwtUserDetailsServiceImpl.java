package br.com.projetobase.api.security.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.projetobase.api.entity.Usuario;
import br.com.projetobase.api.security.service.UsuarioService;

/**
 * @author Felipe
 */
@Service
@CrossOrigin(origins = "*")
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	//private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = usuarioService.buscarPorLogin(username);
		
		//log.info("teste é {}", usuario.isPresent());
		if (usuario.isPresent()) {
			return JwtUserFactory.create(usuario.get());
		}

		throw new UsernameNotFoundException("Login não encontrado.");
	}

}
