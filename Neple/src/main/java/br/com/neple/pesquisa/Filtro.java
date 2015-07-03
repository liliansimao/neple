package br.com.neple.pesquisa;

public class Filtro {
	public static final Byte OPERADOR_IGUAL = 0;
	public static final Byte OPERADOR_DIFERENTE = 1;

	private String propriedade;
	private Byte operador;
	private Object valor;
	
	public Filtro() {
		super();
	}

	public Filtro(String propriedade, Byte operador, Object valor) {
		super();
		this.propriedade = propriedade;
		this.operador = operador;
		this.valor = valor;
	}

	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public Byte getOperador() {
		return operador;
	}

	public void setOperador(Byte operador) {
		this.operador = operador;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}
}
