package br.com.projetobase.api.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetobase.api.entity.Usuario;
import br.com.projetobase.api.exceptions.ExceptionPadrao;
import br.com.projetobase.api.log.entity.LoginLOG;
import br.com.projetobase.api.log.repository.LoginLOGRepository;
import br.com.projetobase.api.repository.UsuarioRepository;
import br.com.projetobase.api.security.dto.TokenDto;
import br.com.projetobase.api.security.jwt.JwtAuthenticationDto;
import br.com.projetobase.api.security.jwt.JwtTokenUtil;
import br.com.projetobase.api.utils.Response;

/**
 * @author Felipe
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AutenticacaoResource {

	private static final Logger sysout = LoggerFactory.getLogger(AutenticacaoResource.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	LoginLOGRepository loginLOGRepository;;
	/**
	 * Gera e retorna um novo token JWT.
	 * 
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 * @author Felipe
	 */
	@PostMapping
	public ResponseEntity<Response<TokenDto>> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto authenticationDto,
			BindingResult result) throws AuthenticationException, ExceptionPadrao {
			
		Response<TokenDto> response = new Response<TokenDto>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDto.getLogin(), authenticationDto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Usuario usuario = this.usuarioRepository.findByLogin(authenticationDto.getLogin());
		
		if(usuario.getAcesso().size()==0) {
			List<String> erros = new ArrayList<>();
			erros.add("Acesso negado. Solicite acesso ao administrador de segurança.");
			response.setErrors(erros);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getLogin());
		String token = jwtTokenUtil.obterToken(userDetails);
		response.setData(new TokenDto(token));
		
		LoginLOG log = new LoginLOG();
		log.setLogin(authenticationDto.getLogin());
		log.setData(new Date());
		
		try {
			loginLOGRepository.save(log);
			sysout.info("Logon: " + log.getLogin() + " - " + log.getData());
			
		} catch (Exception e) {
			throw new ExceptionPadrao("AutenticacaoResource.gerarTokenJwt.gravarLOG: " + e.getMessage());
		}
		
		
		return ResponseEntity.ok(response);
	}

	/**
	 * Gera um novo token com uma nova data de expiração.
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
	@PostMapping(value = "/refresh")
	public ResponseEntity<?> gerarRefreshTokenJwt(HttpServletRequest request) {
		//log.info("Gerando refresh token JWT.");
		Response<TokenDto> response = new Response<TokenDto>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}

		if (!token.isPresent()) {
			response.getErrors().add("Token não informado.");
		} else if (!jwtTokenUtil.tokenValido(token.get())) {
			response.getErrors().add("Token inválido ou expirado.");
		}

		if (!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}

		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		response.setData(new TokenDto(refreshedToken));

		return ResponseEntity.ok(response);
	}
	
	public String getUsuarioLogado() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		if(!(a instanceof AnonymousAuthenticationToken)) {
			return a.getName();
		}
		return "usuário não identificado";
	}
	
	

}
