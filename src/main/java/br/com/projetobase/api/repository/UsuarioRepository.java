package br.com.projetobase.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projetobase.api.entity.Usuario;

/**
 * @author Felipe
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	Page<Usuario> findAllByLoginContaining(@Param("login") String login, Pageable pagina);
	
	Usuario findByLogin(@Param("login") String login);
		
	Usuario findByLoginAndAtivo(@Param("login") String login, @Param("ativo") boolean ativo);
	
	Usuario findByLoginAndSenhaAndAtivoAndAcessoIsNotNull(@Param("login") String login, @Param("senha") String senha, @Param("ativo") boolean ativo);
}
