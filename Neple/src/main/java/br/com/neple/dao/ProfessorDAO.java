package br.com.neple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.neple.domain.Professor;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.HibernateUtil;

public class ProfessorDAO extends GenericDAO<Professor> {
	public void salvar(Professor professor) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.save(professor.getUsuario());
			sessao.save(professor);
			transacao.commit();
		} catch (RuntimeException runtimeException) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}

	public void editar(Professor professor) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.update(professor);
			sessao.update(professor.getUsuario());
			transacao.commit();
		} catch (RuntimeException runtimeException) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}

	public void excluir(Professor professor) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.delete(professor);
			sessao.delete(professor.getUsuario());
			transacao.commit();
		} catch (RuntimeException runtimeException) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Professor> listar() {
		List<Professor> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Professor.class);
			consulta.createAlias("usuario", "u");
			consulta.createAlias("u.fatec", "f");
			consulta.add(Restrictions.eq("u.tipoUsuario",
					TipoUsuario.PROFESSOR.getSigla()));
			consulta.addOrder(Order.asc("u.nome"));
			consulta.addOrder(Order.asc("f.nome"));
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}

	@Override
	public Professor buscar(Long codigo) {
		Professor resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Professor.class);
			consulta.createAlias("usuario", "u");
			consulta.createAlias("u.fatec", "f");
			consulta.add(Restrictions.idEq(codigo));
			resultado = (Professor) consulta.uniqueResult();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
	public Professor buscar(String email) {
		Professor resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Professor.class);
			consulta.createAlias("usuario", "u");
			consulta.createAlias("u.fatec", "f");
			consulta.add(Restrictions.eq("u.email", email));
			resultado = (Professor) consulta.uniqueResult();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
}
