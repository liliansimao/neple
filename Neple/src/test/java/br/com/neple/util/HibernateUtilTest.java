package br.com.neple.util;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

public class HibernateUtilTest {
	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Assert.assertNotNull(sessao);
	}
}
