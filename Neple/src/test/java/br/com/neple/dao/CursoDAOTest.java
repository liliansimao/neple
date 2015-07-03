package br.com.neple.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Ignore;
import org.junit.Test;

import br.com.neple.domain.Curso;

public class CursoDAOTest {
	@Test
	@Ignore
	public void salvar() {
		CursoDAO dao = new CursoDAO();
		
		for (int qtde = 0; qtde < 50; ++qtde) {
			Curso curso = new Curso();
			curso.setAtivo(Boolean.TRUE);
			//curso.setCodigoCursoSIGA(RandomStringUtils.randomNumeric(10));
			//curso.setNome(RandomStringUtils.randomAlphabetic(50));
			curso.setPeriodo('M');
			//curso.setSigla(RandomStringUtils.randomNumeric(10));			
			dao.salvar(curso);
		}
	}
}
