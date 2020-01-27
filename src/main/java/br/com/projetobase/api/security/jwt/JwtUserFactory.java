package br.com.projetobase.api.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.projetobase.api.entity.Usuario;
import br.com.projetobase.api.enums.AcessoEnum;

/**
 * @author Felipe
 */
public class JwtUserFactory {
	
//	private static final Logger log = LoggerFactory.getLogger(JwtUserFactory.class);

	private JwtUserFactory() {
	}

	/**
	 * Converte e gera um JwtUser com base nos dados de um usuário.
	 * 
	 * @param usuario
	 * @return JwtUser
	 */
	public static JwtUser create(Usuario usuario) {
		return new JwtUser(usuario.getLogin(), usuario.getSenha(), mapToGrantedAuthorities(usuario.getAcesso()));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<AcessoEnum> acessos) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(AcessoEnum acesso : acessos) {
			authorities.add(new SimpleGrantedAuthority(acesso.toString()));
		}
		//log.info("Acessos: {}", authorities);
		return authorities;
	}

}
