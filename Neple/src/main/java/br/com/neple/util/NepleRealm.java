package br.com.neple.util;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import br.com.neple.dao.UsuarioDAO;
import br.com.neple.domain.Usuario;
import br.com.neple.enumeracao.TipoUsuario;

@Named
@RequestScoped
public class NepleRealm extends AuthorizingRealm {
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String email = (String) token.getPrincipal();

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.buscarPorEmail(email);
		
		if(usuario == null){
			throw new UnknownAccountException(Mensagens.USUARIO_SENHA_INVALIDOS);
		}

		if (usuario != null) {
			String senha = usuario.getSenha();
			AuthenticationInfo info = new SimpleAuthenticationInfo(email,
					senha, getName());

			SimpleCredentialsMatcher matcher = new SimpleCredentialsMatcher();
			boolean credentialsMatch = matcher.doCredentialsMatch(token, info);

			if (credentialsMatch) {
				return info;
			}
		}

		throw new AuthenticationException(Mensagens.USUARIO_SENHA_INVALIDOS);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.buscarPorEmail(collection
				.getPrimaryPrincipal().toString());
		
		Set<String> roles = new HashSet<String>();
		roles.add(TipoUsuario.getValue(usuario.getTipoUsuario()).getDescricao());
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		
		return info;
	}
}
