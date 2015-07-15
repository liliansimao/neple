package br.com.neple.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.neple.domain.Usuario;
import br.com.neple.util.Criptografia;
import br.com.neple.util.Funcoes;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class AutenticacaoBean implements Serializable {
	private Usuario usuario;

	public Usuario getUsuario() {
		if (this.usuario == null) {
			this.usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void autenticar() {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(
					usuario.getEmail(),
					Criptografia.cifrar(usuario.getSenha()), Boolean.FALSE,
					Funcoes.getIP());

			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);

			Faces.redirect("principal");
		} catch (AuthenticationException authenticationException) {
			Messages.addGlobalWarn(authenticationException.getMessage());
		} catch (IOException ioException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(ioException));
		}
	}

	public void sair() {
		try {
			Subject usuario = SecurityUtils.getSubject();
			usuario.logout();

			Faces.invalidateSession();
			Faces.redirect("autenticacao");
		} catch (IOException ioException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(ioException));
		}
	}
}
