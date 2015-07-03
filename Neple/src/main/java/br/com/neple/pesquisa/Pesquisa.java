package br.com.neple.pesquisa;

import java.util.ArrayList;
import java.util.List;

public class Pesquisa {
	private List<Filtro> filtros;
	private List<Ordem> ordens;
	
	public Pesquisa() {
		super();
		this.filtros = new ArrayList<Filtro>();
		this.ordens = new ArrayList<Ordem>();
	}

	public List<Filtro> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}

	public List<Ordem> getOrdens() {
		return ordens;
	}

	public void setOrdens(List<Ordem> ordens) {
		this.ordens = ordens;
	}
	
	public void adicionarFiltro(String propriedade, Byte operador, Object valor){
		Filtro filtro = new Filtro(propriedade, operador, valor);
		this.filtros.add(filtro);
	}
	
	public void adicionarOrdem(String propriedade, Boolean descendente){
		Ordem ordem = new Ordem(propriedade, descendente);
		this.ordens.add(ordem);
	}
}
