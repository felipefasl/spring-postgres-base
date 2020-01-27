package br.com.projetobase.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetobase.api.entity.Usuario;
import br.com.projetobase.api.exceptions.ExceptionPadrao;
import br.com.projetobase.api.repository.UsuarioRepository;
import br.com.projetobase.api.utils.Response;

/**
 * @author Felipe
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("usuario")
public class UsuarioResource {

	@Autowired
	UsuarioRepository usuarioRepository;

	Usuario usuarioLogado;
	
	@PutMapping
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Response<Usuario>> put(@RequestBody @Valid Usuario usuario) throws ExceptionPadrao {
		if (usuario.getAcesso().size() == 0) {
			usuario.setAcesso(null);
		}

		Usuario usuarioAntesAlteracao = usuarioRepository.findByLogin(usuario.getLogin());

		if (!usuarioAntesAlteracao.getSenha().equals(usuario.getSenha())) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		}
		Response<Usuario> response = new Response<Usuario>();
		try {
			response.setData(usuarioRepository.save(usuario));
		} catch (Exception e) {
			throw new ExceptionPadrao("UsuarioResource.put.atualizarUsuario: " + e.getMessage());
		}
		
		return ResponseEntity.ok(response);
	}

	@GetMapping
	ResponseEntity<Response<Page<Usuario>>> get(Pageable pagina) {
		Response<Page<Usuario>> response = new Response<>();
		response.setData(usuarioRepository.findAll(pagina));
		return ResponseEntity.ok(response);
	}

	/**
	 * Pesquisa usuário a partir de parte do login
	 * 
	 * @param login
	 * @param pagina
	 * @return Usuário
	 */
	@GetMapping("parte-login")
	ResponseEntity<Response<Page<Usuario>>> get(String login, Pageable pagina) {
		Response<Page<Usuario>> response = new Response<>();
		response.setData(usuarioRepository.findAllByLoginContaining(login, pagina));
		return ResponseEntity.ok(response);
	}

	/**
	 * Pesquisa usuário(s) a partir do login
	 * 
	 * @param login
	 * @param pagina
	 * @return Usuário
	 */
	@GetMapping("login")
	public ResponseEntity<?> getByLogin(String login) throws ExceptionPadrao {
		Response<Usuario> response = new Response<Usuario>();
		Usuario usuario = new Usuario();
		try {			
			usuario = usuarioRepository.findByLogin(login);
		}catch (Exception e) {
			throw new ExceptionPadrao("UsuarioResource.getByLogin: " + e.getMessage());
		}
		
		response.setData(usuario);
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Validar usuario existe na base de dados
	 * @param login
	 * @return boolean
	 * 
    */
	 @GetMapping("/isUsuarioExistente/{login}")
	 public ResponseEntity<Response<Boolean>> isUsuarioExistente(@PathVariable("login") String login){
		 Response<Boolean> response = new Response<Boolean>();
		Usuario usuario = usuarioRepository.findByLogin(login);
		if(usuario == null) {
			response.setData(false);
		}
		else {
			response.setData(true);
		}

		 return ResponseEntity.ok(response);
	 }

}
