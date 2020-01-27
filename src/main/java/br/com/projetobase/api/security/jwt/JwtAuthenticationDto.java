package br.com.projetobase.api.security.jwt;

import javax.validation.constraints.NotEmpty;

/**
 * @author Felipe
 */
public class JwtAuthenticationDto {

	private String login;
	private String senha;
	
	public JwtAuthenticationDto() {
	}

	@NotEmpty(message = "Login não pode ser vazio.")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotEmpty(message = "Senha não pode ser vazia.")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [login=" + login + ", senha=" + senha + "]";
	}

}
