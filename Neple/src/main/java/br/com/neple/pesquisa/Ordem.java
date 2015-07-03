package br.com.neple.pesquisa;

public class Ordem {
	private String propriedade;
	private Boolean descendente = Boolean.FALSE;
	
	public Ordem() {
		super();
	}

	public Ordem(String propriedade, Boolean descendente) {
		super();
		this.propriedade = propriedade;
		this.descendente = descendente;
	}

	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public Boolean getDescendente() {
		return descendente;
	}

	public void setDescendente(Boolean descendente) {
		this.descendente = descendente;
	}
}
