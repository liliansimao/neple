package br.com.neple.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Ignore;
import org.junit.Test;

import br.com.neple.domain.Fatec;

public class FatecDAOTest {
	@Test
	@Ignore
	public void salvar() {
		FatecDAO dao = new FatecDAO();
		for (int qtde = 0; qtde < 50; ++qtde) {
			Fatec fatec = new Fatec();
			fatec.setNome(RandomStringUtils.randomAlphabetic(50));
			fatec.setCidade(RandomStringUtils.randomAlphabetic(30));
			//fatec.setCodigoFatecSIGA(RandomStringUtils.randomNumeric(10));
			//fatec.setAtivo(Boolean.TRUE);
			
			dao.salvar(fatec);
		}
	}
}
