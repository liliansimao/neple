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
			
			consulta.createAlias("fatec", "f");
			
			consulta.add(Restrictions.ne("tipoUsuario", TipoUsuario.ALUNO.getSigla()));
			consulta.add(Restrictions.ne("tipoUsuario", TipoUsuario.PROFESSOR.getSigla()));
			
			consulta.addOrder(Order.asc("nome"));
			consulta.addOrder(Order.asc("f.nome"));
			
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listarNaoAtivos() {
		List<Usuario> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			
			consulta.createAlias("fatec", "f");
			
			consulta.add(Restrictions.eq("ativo", Boolean.FALSE));
			
			consulta.addOrder(Order.asc("nome"));
			consulta.addOrder(Order.asc("f.nome"));
			
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
	public Usuario buscar(Long codigo) {
		Usuario resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("fatec", "f");
			consulta.add(Restrictions.idEq(codigo));
			resultado = (Usuario) consulta.uniqueResult();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
	public Usuario buscar(String email){
		Usuario resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.add(Restrictions.eq("email", email));
			resultado = (Usuario) consulta.uniqueResult();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
}
