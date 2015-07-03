package br.com.neple.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "fatec_codigo",
		"curso", "periodo" }) })
@SequenceGenerator(name = "codigo_generator", sequenceName = "curso_sequence", allocationSize = 1)
public class Curso extends GenericDomain {
	@Column(length = 100)
	@Basic(optional = false)
	@NotBlank(message = "O campo CURSO é obrigatório")
	private String curso;

	@Basic(optional = false)
	@NotNull(message = "O campo PERÍODO é obrigatório")
	private Character periodo = 'M';
	
	@Transient
	private String periodoPorExtenso;

	@Basic(optional = false)
	@NotNull(message = "O campo NÚMERO DE VAGAS é obrigatório")
	private Short numeroVagas;

	@Basic(optional = false)
	@NotNull(message = "O campo CARGA HORÁRIA DE ESPANHOL é obrigatório")
	private Short cargaHorariaEspanhol;

	@Basic(optional = false)
	@NotNull(message = "O campo CARGA HORÁRIA DE INGLÊS é obrigatório")
	private Short cargaHorariaIngles;

	@Basic(optional = false)
	@NotNull(message = "O campo CÓDIGO DO CURSO NO SIGA é obrigatório")
	private Long codigoCursoSIGA;

	@Basic(optional = false)
	@NotNull(message = "O campo TURNO DO CURSO NO SIGA é obrigatório")
	private Long codigoTurnoSIGA;

	@Basic(optional = false)
	@NotNull(message = "O campo ATIVO é obrigatório")
	private Boolean ativo = Boolean.TRUE;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	@NotNull(message = "O campo FATEC é obrigatório")
	private Fatec fatec;

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Character getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Character periodo) {
		this.periodo = periodo;
	}

	public Short getNumeroVagas() {
		return numeroVagas;
	}

	public void setNumeroVagas(Short numeroVagas) {
		this.numeroVagas = numeroVagas;
	}

	public Short getCargaHorariaEspanhol() {
		return cargaHorariaEspanhol;
	}

	public void setCargaHorariaEspanhol(Short cargaHorariaEspanhol) {
		this.cargaHorariaEspanhol = cargaHorariaEspanhol;
	}

	public Short getCargaHorariaIngles() {
		return cargaHorariaIngles;
	}

	public void setCargaHorariaIngles(Short cargaHorariaIngles) {
		this.cargaHorariaIngles = cargaHorariaIngles;
	}

	public Long getCodigoCursoSIGA() {
		return codigoCursoSIGA;
	}

	public void setCodigoCursoSIGA(Long codigoCursoSIGA) {
		this.codigoCursoSIGA = codigoCursoSIGA;
	}

	public Long getCodigoTurnoSIGA() {
		return codigoTurnoSIGA;
	}

	public void setCodigoTurnoSIGA(Long codigoTurnoSIGA) {
		this.codigoTurnoSIGA = codigoTurnoSIGA;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Fatec getFatec() {
		return fatec;
	}

	public void setFatec(Fatec fatec) {
		this.fatec = fatec;
	}
	
	public void setPeriodoPorExtenso(String periodoPorExtenso) {
		this.periodoPorExtenso = periodoPorExtenso;
	}
	
	public String getPeriodoPorExtenso(){
		switch (periodo) {
		case 'M':
			return "Matutino";
		case 'V':
			return "Vespertino";
		case 'N':
			return "Noturno";
		default:
			return null;
		}
	}
}
