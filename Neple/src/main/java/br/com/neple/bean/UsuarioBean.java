package br.com.neple.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.neple.dao.FatecDAO;
import br.com.neple.dao.UsuarioDAO;
import br.com.neple.domain.Fatec;
import br.com.neple.domain.Usuario;
import br.com.neple.enumeracao.Acao;
import br.com.neple.enumeracao.TipoUsuario;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class UsuarioBean extends GenericBean {
	private Usuario usuario;

	private List<Fatec> fatecs;
	private List<Usuario> usuarios;

	private FatecDAO fatecDAO;
	private UsuarioDAO usuarioDAO;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Fatec> getFatecs() {
		return fatecs;
	}
	
	public void setFatecs(List<Fatec> fatecs) {
		this.fatecs = fatecs;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@PostConstruct
	public void iniciar() {
		this.fatecDAO = new FatecDAO();
		this.usuarioDAO = new UsuarioDAO();
	}
	
	public void listar() {
		try {
			this.usuarios = this.usuarioDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
	
	public void novo() {
		try {
			this.acao = Acao.NOVO;
			
			this.fatecs = this.fatecDAO.listar();
			
			this.usuario = new Usuario();
			this.usuario.setTipoUsuario(TipoUsuario.COORDENADOR.getSigla());
			this.usuario.setAtivo(Boolean.TRUE);
			this.usuario.setDataCriacao(new Date());
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
	
	public void salvar() {
		boolean salvou = false;

		try {
			if (this.acao == Acao.NOVO) {
				this.usuarioDAO.salvar(this.usuario);
			} else {
				this.usuarioDAO.editar(this.usuario);
			}

			this.listar();
			salvou = true;
			Messages.addGlobalInfo("Usuário salvo com sucesso");
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn("Usuário já cadastrado anteriormente");
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
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.usuario = this.usuarioDAO.buscar(codigo);
			this.usuarioDAO.excluir(this.usuario);

			this.listar();
			excluiu = true;
			Messages.addGlobalInfo("Usuário removido com sucesso");
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn("O usuário não pode ser removido, pois existem registros dependentes");
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		} finally {
			RequestContext.getCurrentInstance().addCallbackParam("excluiu",
					excluiu);
		}
	}
	
	public void editar(ActionEvent event) {
		try {
			this.acao = Acao.EDITAR;
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.usuario = this.usuarioDAO.buscar(codigo);
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
}
