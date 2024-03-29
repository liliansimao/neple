package br.com.neple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.neple.domain.Aluno;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.HibernateUtil;

public class AlunoDAO extends GenericDAO<Aluno> {
	public void salvar(Aluno aluno) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.save(aluno.getUsuario());
			sessao.save(aluno);
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

	public void editar(Aluno aluno) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.update(aluno);
			sessao.update(aluno.getUsuario());
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

	public void excluir(Aluno aluno) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.delete(aluno);
			sessao.delete(aluno.getUsuario());
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
	public List<Aluno> listar() {
		List<Aluno> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Aluno.class, "aluno");
			consulta.createAlias("aluno.usuario", "usuario");
			consulta.createAlias("usuario.fatec", "fatec");
			consulta.add(Restrictions.eq("usuario.tipoUsuario",
					TipoUsuario.ALUNO.getSigla()));
			consulta.addOrder(Order.asc("usuario.nome"));
			consulta.addOrder(Order.asc("fatec.nome"));
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}

	@Override
	public Aluno buscarPorCodigo(Long codigo) {
		Aluno resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Aluno.class, "aluno");
			consulta.createAlias("aluno.usuario", "usuario");
			consulta.createAlias("aluno.curso", "curso");
			consulta.createAlias("usuario.fatec", "fatec");
			consulta.add(Restrictions.idEq(codigo));
			resultado = (Aluno) consulta.uniqueResult();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> buscarPorFatec(Long codigoFatec) {
		List<Aluno> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession(); 
		try {
			Criteria consulta = sessao.createCriteria(Aluno.class, "aluno");
			
			consulta.createAlias("aluno.usuario", "usuario");
			consulta.createAlias("usuario.fatec", "fatec");
			
			consulta.add(Restrictions.eq("usuario.tipoUsuario",
					TipoUsuario.ALUNO.getSigla()));
			consulta.add(Restrictions.eq("fatec.codigo", codigoFatec));
			
			consulta.addOrder(Order.asc("usuario.nome"));
			consulta.addOrder(Order.asc("fatec.nome"));
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
}
