package br.com.projetobase.api.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetobase.api.log.entity.LoginLOG;

/**
 * @author Felipe
 */
@Repository
public interface LoginLOGRepository extends JpaRepository<LoginLOG, Long >{

}
