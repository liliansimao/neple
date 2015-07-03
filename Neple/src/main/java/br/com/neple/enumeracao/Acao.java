package br.com.neple.enumeracao;

public enum Acao {
	NOVO('N', "Novo"), 
	EDITAR('U', "Editar"), 
	EXCLUIR('D', "Excluir");

	private Character sigla;
	private String descricao;

	private Acao(Character sigla, String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}
	
	public Character getSigla() {
		return sigla;
	}
	
	public void setSigla(Character sigla) {
		this.sigla = sigla;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
