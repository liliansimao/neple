package br.com.neple.bean;

import java.io.Serializable;

import br.com.neple.enumeracao.Acao;

@SuppressWarnings("serial")
public class GenericBean implements
		Serializable {
	protected Acao acao;
	
	public Acao getAcao() {
		return acao;
	}
	
	public void setAcao(Acao acao) {
		this.acao = acao;
	}
}
