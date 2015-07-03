package br.com.neple.enumeracao;

public enum TipoUsuario {
	ADMINISTRADOR('X', "Administrador"),
	ALUNO('A', "Aluno"),
	COORDENADOR('C', "Coordenador"),
	DIRETOR('D', "Diretor"),
	LABORATORIO_INFORMATICA('L', "Laboratório de Informática"),
	PROFESSOR('P', "Professor"),
	SECRETARIA('S', "Secretária");
	
	private Character sigla;
	private String descricao;
	
	private TipoUsuario(Character sigla, String descricao) {
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
