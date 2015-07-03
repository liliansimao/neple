package br.com.neple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.neple.domain.Fatec;
import br.com.neple.util.HibernateUtil;

public class FatecDAO extends GenericDAO<Fatec> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Fatec> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fatec.class);
			consulta.addOrder(Order.asc("nome"));

			List<Fatec> resultado = consulta.list();

			return resultado;
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}

	public Fatec buscar(String nome) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fatec.class);
			
			consulta.add(Restrictions.eq("nome", nome));
			
			Fatec resultado = (Fatec) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}
	
	public Fatec buscar(Long codigo, String nome) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fatec.class);
			
			consulta.add(Restrictions.ne("codigo", codigo));
			consulta.add(Restrictions.eq("nome", nome));
			
			Fatec resultado = (Fatec) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}
}
