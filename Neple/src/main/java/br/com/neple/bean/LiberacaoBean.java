package br.com.neple.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omnifaces.util.Messages;

import br.com.neple.dao.UsuarioDAO;
import br.com.neple.domain.Usuario;
import br.com.neple.util.Mensagens;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class LiberacaoBean extends GenericBean {
	private Usuario usuario;
	
	private List<Usuario> usuarios;

	private UsuarioDAO usuarioDAO;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@PostConstruct
	public void iniciar() {
		this.usuarioDAO = new UsuarioDAO();
	}

	public void listar() {
		try {
			this.usuarios = this.usuarioDAO.listarNaoAtivos();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void liberar(ActionEvent event) {
		try {
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.usuario = this.usuarioDAO.buscar(codigo);
			this.usuario.setAtivo(Boolean.TRUE);
			this.usuarioDAO.excluir(this.usuario);

			this.listar();
			Messages.addGlobalInfo(Mensagens.USUARIO_LIBERADO);
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		} 
	}
}
