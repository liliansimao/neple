package br.com.neple.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.neple.enumeracao.TipoUsuario;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@SequenceGenerator(name = "codigo_generator", sequenceName = "usuario_sequence", allocationSize = 1)
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
	private Character tipoUsuario;
	
	@Transient
	private String tipoUsuarioExtenso;

	@Basic(optional = false)
	@NotNull(message = "O campo ATIVO é obrigatório")
	private Boolean ativo;
	
	@Transient
	private String ativoExtenso;

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "O campo DATA DE CRIAÇÃO é obrigatório")
	private Date dataCriacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@NotNull(message = "O campo FATEC é obrigatório")
	private Fatec fatec;

	@OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Professor professor;
	
	@OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
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
	
	public void setTipoUsuarioExtenso(String tipoUsuarioExtenso) {
		this.tipoUsuarioExtenso = tipoUsuarioExtenso;
	}
	
	public String getTipoUsuarioExtenso() {
		return TipoUsuario.getValue(this.tipoUsuario).getDescricao();
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getAtivoExtenso() {
		return (this.ativo ? "Sim" : "Não");
	}
	
	public void setAtivoExtenso(String ativoExtenso) {
		this.ativoExtenso = ativoExtenso;
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
