package br.com.neple.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "cpf" }) })
public class Aluno implements Serializable {
	@Id
	@OneToOne(fetch = FetchType.EAGER)
	@NotNull(message = "O campo USUÁRIO é obrigatório")
	private Usuario usuario;
	
	@Column(length = 14)
	@Basic(optional = false)
	@NotEmpty(message = "O campo RA é obrigatório")
	private String ra;
	
	@Column(length = 11)
	@Basic(optional = false)
	@NotEmpty(message = "O campo CPF é obrigatório")
	private String cpf;
	
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "O campo DATA DE ALTERAÇÃO é obrigatório")
	private Date dataAlteracao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	@NotNull(message = "O campo CURSO é obrigatório")
	private Curso curso;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	@NotNull(message = "O campo USUÁRIO ALTERAÇÃO é obrigatório")
	private Usuario usuarioAlteracao;

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
}
