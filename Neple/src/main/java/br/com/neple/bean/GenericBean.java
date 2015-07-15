package br.com.neple.bean;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import br.com.neple.enumeracao.Acao;
import br.com.neple.enumeracao.TipoUsuario;

@SuppressWarnings("serial")
public class GenericBean implements Serializable {
	protected Acao acao;

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}
	
	public boolean ehAdministrador() {
		Subject usuario = SecurityUtils.getSubject();
		boolean ehAdministrador = usuario.hasRole(TipoUsuario.ADMINISTRADOR
				.getDescricao());
		return ehAdministrador;
	}

	public boolean ehProfessor() {
		Subject usuario = SecurityUtils.getSubject();
		boolean ehProfessor = usuario.hasRole(TipoUsuario.PROFESSOR
				.getDescricao());
		return ehProfessor;
	}

	public boolean ehAluno() {
		Subject usuario = SecurityUtils.getSubject();
		boolean ehAluno = usuario.hasRole(TipoUsuario.ALUNO.getDescricao());
		return ehAluno;
	}
}
