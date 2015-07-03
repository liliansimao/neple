package br.com.neple.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nome" }) })
@SequenceGenerator(name = "codigo_generator", sequenceName = "fatec_sequence", allocationSize = 1)
public class Fatec extends GenericDomain {
	@Column(length = 100)
	@Basic(optional = false)
	@NotBlank(message = "O campo NOME é obrigatório")
	private String nome;

	@Column(length = 100)
	@Basic(optional = false)
	@NotBlank(message = "O campo CIDADE é obrigatório")
	private String cidade;

	@Basic(optional = false)
	@NotNull(message = "O campo CÓDIGO DA FATEC NO SIGA é obrigatório")
	private Long codigoFatecSIGA;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Long getCodigoFatecSIGA() {
		return codigoFatecSIGA;
	}

	public void setCodigoFatecSIGA(Long codigoFatecSIGA) {
		this.codigoFatecSIGA = codigoFatecSIGA;
	}
}
