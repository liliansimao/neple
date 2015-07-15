package br.com.neple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import br.com.neple.domain.Alocacao;
import br.com.neple.util.HibernateUtil;

public class AlocacaoDAO extends GenericDAO<Alocacao> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Alocacao> listar() {
		List<Alocacao> resultado = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Alocacao.class);

			consulta.createAlias("professor", "p");
			consulta.createAlias("p.usuario", "u");
			consulta.createAlias("fatec", "f");

			consulta.setProjection(Projections.projectionList()
					.add(Projections.property("u.nome"))
					.add(Projections.property("u.email"))
					.add(Projections.property("f.nome")));

			consulta.addOrder(Order.asc("u.nome"));
			consulta.addOrder(Order.asc("u.email"));
			consulta.addOrder(Order.asc("f.nome"));

			resultado = consulta.list();
		} catch (RuntimeException runtimeException) {
			throw runtimeException;
		} finally {
			sessao.close();
		}
		return resultado;
	}
}
