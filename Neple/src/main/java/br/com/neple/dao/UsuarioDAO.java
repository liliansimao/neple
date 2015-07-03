package br.com.neple.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.neple.domain.Usuario;
import br.com.neple.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {
	public String buscarCredencial(String principal){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.add(Restrictions.eq("email", principal));
			Usuario usuario = (Usuario) consulta.uniqueResult();
			String credencial = usuario != null ? usuario.getSenha() : null;
			return credencial;
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}
}
