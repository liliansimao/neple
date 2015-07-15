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
		List<Curso> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Curso.class, "curso");
			consulta.createAlias("curso.fatec", "fatec");
			consulta.addOrder(Order.asc("fatec.nome"));
			consulta.addOrder(Order.asc("curso.curso"));
			consulta.addOrder(Order.asc("curso.periodo"));
			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
	
	@Override
	public Curso buscarPorCodigo(Long codigo) {
		Curso resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Curso.class, "curso");
			consulta.createAlias("curso.fatec", "fatec");
			consulta.add(Restrictions.idEq(codigo));
			resultado = (Curso) consulta.uniqueResult();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Curso> buscarPorFatec(Long codigoFatec) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Curso.class, "curso");
			consulta.createAlias("curso.fatec", "fatec");
			consulta.add(Restrictions.eq("fatec.codigo", codigoFatec));
			consulta.addOrder(Order.asc("curso.curso"));
			List<Curso> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
	}
}
