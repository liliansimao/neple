package br.com.neple.enumeracao;

public enum Idioma {
	INGLES('I', "Inglês"), ESPANHOL('E', "Espanhol");

	private Character sigla;
	private String descricao;

	private Idioma(Character sigla, String descricao) {
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
