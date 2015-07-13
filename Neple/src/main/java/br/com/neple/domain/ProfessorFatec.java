package br.com.neple.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class ProfessorFatec implements Serializable {
	@EmbeddedId
	private ProfessorFatecPK codigo;
	
	public ProfessorFatecPK getCodigo() {
		return codigo;
	}
	
	public void setCodigo(ProfessorFatecPK codigo) {
		this.codigo = codigo;
	}
}
