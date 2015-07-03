package br.com.neple.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@SequenceGenerator(name = "codigo_generator", sequenceName = "usuario_sequence", initialValue = 1)
public class Usuario extends GenericDomain {
	@Column(length = 80)
	@Basic(optional = false)
	@NotEmpty(message = "O campo NOME é obrigatório")
	private String nome;

	@Column(length = 130)
	@Basic(optional = false)
	@NotEmpty(message = "O campo E-MAIL é obrigatório")
	private String email;

	@Column(length = 24)
	@Basic(optional = false)
	@NotEmpty(message = "O campo SENHA é obrigatório")
	private String senha;

	@Transient
	private String confirmacaoSenha;

	@Basic(optional = false)
	@NotNull(message = "O campo TIPO DO USUÁRIO é obrigatório")
	private Character tipoUsuario = 'A';

	@Basic(optional = false)
	@NotNull(message = "O campo ATIVO é obrigatório")
	private Boolean ativo = Boolean.FALSE;

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "O campo DATA DE CRIAÇÃO é obrigatório")
	private Date dataCriacao = new Date();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	@NotNull(message = "O campo FATEC é obrigatório")
	private Fatec fatec;

	@OneToOne(mappedBy = "usuario")
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private Professor professor;
	
	@OneToOne(mappedBy = "usuario")
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private Aluno aluno;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public Character getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Character tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

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
	
	public Aluno getAluno() {
		return aluno;
	}
	
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
}
