package br.com.neple.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omnifaces.util.Messages;

import br.com.neple.dao.AlocacaoDAO;
import br.com.neple.dao.FatecDAO;
import br.com.neple.dao.ProfessorDAO;
import br.com.neple.domain.Alocacao;
import br.com.neple.domain.Fatec;
import br.com.neple.domain.Professor;
import br.com.neple.enumeracao.Acao;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class AlocacaoBean extends GenericBean {
	private Alocacao alocacao;
	
	private List<Professor> professores;
	private List<Fatec> fatecs;
	private List<Alocacao> alocacoes;
	
	private ProfessorDAO professorDAO;
	private FatecDAO fatecDAO;
	private AlocacaoDAO alocacaoDAO;
	
	public Alocacao getAlocacao() {
		return alocacao;
	}
	
	public void setAlocacao(Alocacao alocacao) {
		this.alocacao = alocacao;
	}
	
	public List<Professor> getProfessores() {
		return professores;
	}
	
	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}
	
	public List<Fatec> getFatecs() {
		return fatecs;
	}
	
	public void setFatecs(List<Fatec> fatecs) {
		this.fatecs = fatecs;
	}
	
	public List<Alocacao> getAlocacoes() {
		return alocacoes;
	}
	
	public void setAlocacoes(List<Alocacao> alocacoes) {
		this.alocacoes = alocacoes;
	}
	
	@PostConstruct
	public void iniciar() {
		this.fatecDAO = new FatecDAO();
		this.professorDAO = new ProfessorDAO();
		this.alocacaoDAO = new AlocacaoDAO();
	}
	
	public void listar() {
		try {
			this.alocacoes = this.alocacaoDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void novo() {
		try {
			this.acao = Acao.NOVO;
			
			this.alocacao = new Alocacao();
			
			this.fatecs = fatecDAO.listar();
			this.professores = professorDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
}
