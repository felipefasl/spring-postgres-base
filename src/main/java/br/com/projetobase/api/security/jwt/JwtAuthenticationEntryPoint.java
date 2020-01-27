package br.com.projetobase.api.security.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author Felipe
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		
		switch (authException.getMessage()) {
		case "Bad credentials":
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuário e/ou senha inválidos, tente novamente.");
			break;
		case "Full authentication is required to access this resource":
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Autenticação necessária.");
			break;			
		default:
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"Acesso negado. Solicite acesso ao administrador de segurança.");
			break;
		}
	}

}
