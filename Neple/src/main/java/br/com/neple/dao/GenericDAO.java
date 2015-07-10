package br.com.neple.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.neple.util.HibernateUtil;

public class GenericDAO<Entidade> {
	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void salvar(Entidade entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.save(entidade);
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

	public void editar(Entidade entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.update(entidade);
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

	public void excluir(Entidade entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
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
	public Entidade buscar(Long codigo) {
		Entidade resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			resultado = (Entidade) sessao.get(classe, codigo);
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> listar() {
		List<Entidade> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}

	// @SuppressWarnings("unchecked")
	// public List<Entidade> buscar(Pesquisa pesquisa) {
	// Session sessao = HibernateUtil.getSessionFactory().openSession();
	//
	// try {
	// Criteria consulta = sessao.createCriteria(classe);
	//
	// if (pesquisa != null) {
	// for (Filtro filtro : pesquisa.getFiltros()) {
	// if (filtro.getOperador() == Filtro.OPERADOR_IGUAL) {
	// consulta.add(Restrictions.eq(filtro.getPropriedade(),
	// filtro.getValor()));
	// } else if (filtro.getOperador() == Filtro.OPERADOR_DIFERENTE) {
	// consulta.add(Restrictions.ne(filtro.getPropriedade(),
	// filtro.getValor()));
	// }
	// }
	//
	// for (Ordem ordem : pesquisa.getOrdens()){
	// if(ordem.getDescendente()){
	// consulta.addOrder(Order.desc(ordem.getPropriedade()));
	// }else{
	// consulta.addOrder(Order.asc(ordem.getPropriedade()));
	// }
	// }
	// }
	//
	// List<Entidade> resultado = consulta.list();
	// return resultado;
	// } catch (RuntimeException runtimeException) {
	// throw runtimeException;
	// } finally {
	// sessao.close();
	// }
	// }
	//
	// @SuppressWarnings("unchecked")
	// public Entidade buscarUnico(Pesquisa pesquisa) {
	// Session sessao = HibernateUtil.getSessionFactory().openSession();
	//
	// try {
	// Criteria consulta = sessao.createCriteria(classe);
	//
	// if (pesquisa != null) {
	// for (Filtro filtro : pesquisa.getFiltros()) {
	// if (filtro.getOperador() == Filtro.OPERADOR_IGUAL) {
	// consulta.add(Restrictions.eq(filtro.getPropriedade(),
	// filtro.getValor()));
	// } else if (filtro.getOperador() == Filtro.OPERADOR_DIFERENTE) {
	// consulta.add(Restrictions.ne(filtro.getPropriedade(),
	// filtro.getValor()));
	// }
	// }
	//
	// for (Ordem ordem : pesquisa.getOrdens()){
	// if(ordem.getDescendente()){
	// consulta.addOrder(Order.desc(ordem.getPropriedade()));
	// }else{
	// consulta.addOrder(Order.asc(ordem.getPropriedade()));
	// }
	// }
	// }
	//
	// Entidade resultado = (Entidade) consulta.uniqueResult();
	// return resultado;
	// } catch (RuntimeException runtimeException) {
	// throw runtimeException;
	// } finally {
	// sessao.close();
	// }
	// }
}
