package br.com.neple.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.neple.domain.Usuario;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class AutenticacaoBean implements Serializable {
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void iniciar(){
		this.usuario = new Usuario();
	}
	
	public void autenticar() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		UsernamePasswordToken token = new UsernamePasswordToken(
//				usuario.getEmail(), usuario.getSenha());	
//		Subject currentUser = SecurityUtils.getSubject();
//		
//		try {
//			currentUser.login(token);
//			context.getExternalContext().redirect("pages/protected/principal.xhtml");
//		} catch (AuthenticationException authenticationException) {
//			Messages.addGlobalWarn("Usuário e/ou senha inválido(s)");
//		} catch (IOException ioException) {
//			ioException.printStackTrace();
//		}
	}
}
