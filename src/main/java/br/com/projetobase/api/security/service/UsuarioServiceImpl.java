package br.com.projetobase.api.security.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetobase.api.entity.Usuario;
import br.com.projetobase.api.repository.UsuarioRepository;

/**
 * @author Felipe
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> buscarPorLogin(String login) {
		return Optional.ofNullable(this.usuarioRepository.findByLoginAndAtivo(login, true));
	}
}