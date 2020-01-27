package br.com.projetobase.api.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetobase.api.entity.Perfil;
import br.com.projetobase.api.exceptions.ExceptionPadrao;
import br.com.projetobase.api.repository.PerfilRepository;
import br.com.projetobase.api.utils.Response;

/**
 * @author Felipe
 */
@RestController
@RequestMapping("perfil-acesso")
@CrossOrigin(origins = "*")
public class PerfilAcessoResource {
	
	@Autowired
	PerfilRepository perfilAcessoRepository;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('A01')")
	public ResponseEntity<?> inserir(@RequestBody @Valid Perfil perfilAcesso) throws ExceptionPadrao {
		Response<Perfil> response = new Response<Perfil>();
		response.setData(perfilAcessoRepository.save(perfilAcesso));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('A01')")
	public ResponseEntity<?> atualizar(@RequestBody @Valid Perfil perfilAcesso) throws ExceptionPadrao {
		Response<Perfil> response = new Response<Perfil>();
		response.setData(perfilAcessoRepository.save(perfilAcesso));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('A01')")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) throws ExceptionPadrao {
		Optional<Perfil> perfilAcesso = perfilAcessoRepository.findById(id);
		if (!perfilAcesso.isPresent()) {
			throw new ExceptionPadrao("Erro ao remover perfil. Registro não encontrado para o id " + id);
		}
		perfilAcessoRepository.deleteById(id);
		return ResponseEntity.ok(true);
	}
	
	@GetMapping()
	@PreAuthorize("hasAnyRole('A01')")
	public ResponseEntity<?> getAll() {
		Response<Iterable<Perfil>> response = new Response<>();
		response.setData(perfilAcessoRepository.findAllByOrderByDescricaoAsc());
		return ResponseEntity.ok(response);
	}
}
