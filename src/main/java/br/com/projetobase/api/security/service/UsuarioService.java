package br.com.projetobase.api.security.service;

import java.util.Optional;

import br.com.projetobase.api.entity.Usuario;

/**
 * @author Felipe
 */
public interface UsuarioService {

	/**
	 * Busca e retorna um usuário dado um login.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorLogin(String login);

}
