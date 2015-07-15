package br.com.neple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.neple.domain.Fatec;
import br.com.neple.util.HibernateUtil;

public class FatecDAO extends GenericDAO<Fatec> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Fatec> listar() {
		List<Fatec> resultado = null;
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fatec.class);		
			consulta.addOrder(Order.asc("nome"));	
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
}
