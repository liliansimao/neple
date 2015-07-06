package br.com.neple.enumeracao;

public enum Periodo {
	MATUTINO('M', "Matutino"), 
	VESPERTINO('V', "Vespertino"), 
	NOTURNO('N', "Noturno");

	private Character sigla;
	private String descricao;

	private Periodo(Character sigla, String descricao) {
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
	
	public static Periodo getValue(Character sigla){
		for(Periodo periodo : values()){
			if(periodo.getSigla() == sigla){
				return periodo;
			}
		}
		return null;	
	}
}
