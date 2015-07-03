package br.com.neple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.neple.domain.Curso;
import br.com.neple.util.HibernateUtil;

public class CursoDAO extends GenericDAO<Curso> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Curso.class);
			consulta.createAlias("fatec", "f");
			
			consulta.addOrder(Order.asc("f.nome"));
			consulta.addOrder(Order.asc("curso"));
			
			List<Curso> resultado = consulta.list();
			
			return resultado;
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Curso> buscarPorFatec(Long codigoFatec) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Curso.class);
			consulta.createAlias("fatec", "f");
			
			consulta.add(Restrictions.eq("f.codigo", codigoFatec));
			
			consulta.addOrder(Order.asc("curso"));
			
			List<Curso> resultado = consulta.list();
			
			return resultado;
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}
}
