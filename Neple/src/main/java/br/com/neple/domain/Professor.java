package br.com.neple.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
public class Professor extends GenericDomain {
	@Column(columnDefinition = "text")
	@Basic(optional = false)
	@NotEmpty(message = "O campo APRESENTAÇÃO é obrigatório")
	private String apresentacao;

	@Column(length = 20)
	@Basic(optional = false)
	@NotEmpty(message = "O campo TELEFONE DE CONTATO é obrigatório")
	private String telefoneContato;

	@Basic(optional = false)
	@NotNull(message = "O campo IDIOMA é obrigatório")
	private Character idioma;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@NotNull(message = "O campo USUÁRIO é obrigatório")
	private Usuario usuario;

	public String getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}

	public Character getIdioma() {
		return idioma;
	}

	public void setIdioma(Character idioma) {
		this.idioma = idioma;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
