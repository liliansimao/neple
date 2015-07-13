package br.com.neple.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Embeddable
public class ProfessorFatecPK implements Serializable {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@NotNull(message = "O campo PROFESSOR é obrigatório")
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@NotNull(message = "O campo FATEC é obrigatório")
	private Fatec fatec;
	
	public Fatec getFatec() {
		return fatec;
	}
	
	public void setFatec(Fatec fatec) {
		this.fatec = fatec;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
}
