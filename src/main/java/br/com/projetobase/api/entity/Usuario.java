package br.com.projetobase.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.projetobase.api.enums.AcessoEnum;

/**
 * @author Felipe
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 306411570471828345L;

	private String  login;
	private String  senha;
	private boolean ativo;
	private boolean atualizarSenha;
	private List<AcessoEnum> acesso = new ArrayList<>();

	public Usuario() {
	}

	@Id
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login.toLowerCase();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	public List<AcessoEnum> getAcesso() {
		return acesso;
	}

	public void setAcesso(List<AcessoEnum> acesso) {
		this.acesso = acesso;
	}
	
	public boolean isAtualizarSenha() {
		return atualizarSenha;
	}

	public void setAtualizarSenha(boolean atualizarSenha) {
		this.atualizarSenha = atualizarSenha;
	}

}
