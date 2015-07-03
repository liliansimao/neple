package br.com.neple.util;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.realm.Realm;

import br.com.neple.dao.UsuarioDAO;

public class NepleRealm implements Realm {
	private final String NAME = "NepleRealm";

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		String principal = (String) token.getPrincipal();

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		String credencial = usuarioDAO.buscarCredencial(principal);

		if (credencial != null) {
			AuthenticationInfo info = new SimpleAuthenticationInfo(principal,
					credencial, getName());

			SimpleCredentialsMatcher matcher = new SimpleCredentialsMatcher();
			boolean credentialsMatch = matcher.doCredentialsMatch(token, info);
			
			if (credentialsMatch) {
				return info;
			}
		}
		
		throw new AuthenticationException();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return Boolean.TRUE;
	}
}
