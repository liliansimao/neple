package br.com.neple.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.neple.domain.Usuario;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class AutenticacaoBean implements Serializable {
	private Usuario usuario;

	public Usuario getUsuario() {
		if(this.usuario == null){
			this.usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void autenticar() {
		//UsernamePasswordToken token = new UsernamePasswordToken(
		//		usuario.getEmail(), Criptografia.cifrar(usuario.getSenha()));

		// Subject currentUser = SecurityUtils.getSubject();

		try {
			// currentUser.login(token);
			Faces.redirect("principal");
		//} catch (AuthenticationException authenticationException) {
		//	Messages.addGlobalWarn("Usuário e/ou senha inválido(s)");
		} catch (IOException ioException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(ioException));
		}
	}
}
