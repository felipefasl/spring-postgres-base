package br.com.projetobase.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.projetobase.api.enums.AcessoEnum;

/**
 * @author Felipe
 */
@Entity
@Table(name="perfil")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Perfil {

	private String descricao;
	private List<AcessoEnum> acesso = new ArrayList<>();

	@Id
	public String getDescricao() {
		if (descricao != null)
			return descricao.toUpperCase();
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;	
	}

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.LAZY)
	public List<AcessoEnum> getAcesso() {
		return acesso;
	}

	public void setAcesso(List<AcessoEnum> acesso) {
		this.acesso = acesso;
	}

}
