package br.com.projetobase.api.repository;

/**
 * @author Felipe
 */
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetobase.api.entity.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	Iterable<Perfil> findAllByOrderByDescricaoAsc();
}
