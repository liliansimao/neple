package br.com.neple.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.neple.dao.FatecDAO;
import br.com.neple.dao.UsuarioDAO;
import br.com.neple.domain.Fatec;
import br.com.neple.domain.Usuario;
import br.com.neple.enumeracao.Idioma;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.Criptografia;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class UsuarioBean implements Serializable {
	private Usuario usuario;
	
	private List<Usuario> usuarios;
	private List<Fatec> fatecs;
	
	private TipoUsuario[] tiposUsuario;
	private Idioma[] idiomas;

	private UsuarioDAO usuarioDAO;
	private FatecDAO fatecDAO;

	@PostConstruct
	public void iniciar() {
		this.usuarioDAO = new UsuarioDAO();
		this.fatecDAO = new FatecDAO();
	}

	public void novo() {
		try {
			this.usuario = new Usuario();
			
			this.tiposUsuario = TipoUsuario.values();
			this.idiomas = Idioma.values();
			
			this.fatecs = fatecDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
	
	public void editar(ActionEvent event) { 
		try {
			this.usuario = (Usuario) event.getComponent().getAttributes()
					.get("usuario");
			this.usuario.setSenha(Criptografia.decifrar(this.usuario.getSenha()));
			this.usuario.setConfirmacaoSenha(this.usuario.getSenha());
			
			this.tiposUsuario = TipoUsuario.values();
			this.idiomas = Idioma.values();
			
			this.fatecs = fatecDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void listar() {
		try {
			this.usuarios = this.usuarioDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public String autenticar() {
		return "/pages/protected/principal.xhtml?faces-redirect=true";
	}

	public void salvar() {
		boolean salvou = false;

		try {
			this.usuario.setSenha(Criptografia.cifrar(this.usuario.getSenha()));
			//this.usuarioDAO.fundir(this.usuario);
			this.listar();

			salvou = true;

			Messages.addGlobalInfo("Usuário salvo com sucesso");
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		} finally {
			RequestContext.getCurrentInstance().addCallbackParam("salvou",
					salvou);
		}
	}
	
	public void excluir(ActionEvent event) {
		boolean excluiu = false;

		try {
			this.usuario = (Usuario) event.getComponent().getAttributes()
					.get("usuario");

			this.usuarioDAO.excluir(this.usuario);
			this.listar();

			excluiu = true;

			Messages.addGlobalInfo("Usuário removido com sucesso");
		} catch (RuntimeException exception) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(exception));
		} finally {
			RequestContext.getCurrentInstance().addCallbackParam("excluiu",
					excluiu);
		}
	}
}
