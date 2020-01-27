package br.com.projetobase.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.projetobase.api.entity.Usuario;
import br.com.projetobase.api.enums.AcessoEnum;
import br.com.projetobase.api.repository.UsuarioRepository;
import br.com.projetobase.api.security.utils.SenhaUtils;

@SpringBootApplication
public class ProjetoBaseApplication {

	@Autowired
	UsuarioRepository usuarioRepository;

	@PostConstruct
	public void init() throws ParseException {
		// Configuração de Fuso Horário
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		System.out.println("Spring boot application running in UTC timezone :" + new Date());
		
		if (!usuarioRepository.findById("admin@admin.com.br").isPresent()) {
			this.incluirAtualizarUsuario();
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(ProjetoBaseApplication.class, args);
	}

	private Usuario incluirAtualizarUsuario() {
		Usuario usuario = new Usuario();
		usuario.setLogin("admin");
		usuario.setSenha(SenhaUtils.gerarBCrypt("123456"));
		usuario.setAtivo(true);
		List<AcessoEnum> acesso = new ArrayList<>();
		acesso.add(AcessoEnum.ROLE_A01);
		usuario.setAcesso(acesso);
		return usuarioRepository.save(usuario);
	}

}