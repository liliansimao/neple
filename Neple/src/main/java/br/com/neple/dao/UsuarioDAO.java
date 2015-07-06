package br.com.neple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.neple.domain.Usuario;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listar() {
		List<Usuario> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.add(Restrictions.ne("tipoUsuario", TipoUsuario.ALUNO.getSigla()));
			consulta.add(Restrictions.ne("tipoUsuario", TipoUsuario.PROFESSOR.getSigla()));
			consulta.addOrder(Order.asc("nome"));
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
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
