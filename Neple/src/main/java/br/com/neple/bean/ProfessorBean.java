package br.com.neple.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.neple.dao.FatecDAO;
import br.com.neple.dao.ProfessorDAO;
import br.com.neple.domain.Fatec;
import br.com.neple.domain.Professor;
import br.com.neple.domain.Usuario;
import br.com.neple.enumeracao.Acao;
import br.com.neple.enumeracao.Idioma;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.Criptografia;
import br.com.neple.util.Funcoes;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ProfessorBean extends GenericBean {
	private Professor professor;

	private List<Fatec> fatecs;
	private List<Professor> professores;

	private FatecDAO fatecDAO;
	private ProfessorDAO professorDAO;

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Fatec> getFatecs() {
		return fatecs;
	}

	public void setFatecs(List<Fatec> fatecs) {
		this.fatecs = fatecs;
	}

	public List<Professor> getProfessores() {
		return professores;
	}
	
	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	@PostConstruct
	public void iniciar() {
		this.fatecDAO = new FatecDAO();
		this.professorDAO = new ProfessorDAO();
	}

	public void listar() {
		try {
			if (this.ehAdministrador()) {
				this.professores = this.professorDAO.listar();
			} else if(this.ehProfessor()){
				Subject subject = SecurityUtils.getSubject();
				Usuario usuario = (Usuario) subject.getPrincipal();
				
				Professor professor = this.professorDAO.buscarPorCodigo(usuario.getCodigo());
				
				this.professores = new ArrayList<Professor>();
				this.professores.add(professor);
			}
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void novo() {
		try {
			this.acao = Acao.NOVO;

			this.fatecs = this.fatecDAO.listar();

			this.professor = new Professor();

			this.professor.setUsuario(new Usuario());
			this.professor.getUsuario().setTipoUsuario(
					TipoUsuario.PROFESSOR.getSigla());
			this.professor.getUsuario().setAtivo(Boolean.FALSE);
			this.professor.getUsuario().setDataCriacao(new Date());

			this.professor.setIdioma(Idioma.INGLES.getSigla());
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void salvar() {
		boolean salvou = false;

		try {
			this.professor.getUsuario()
					.setSenha(
							Criptografia.cifrar(this.professor.getUsuario()
									.getSenha()));

			if (this.acao == Acao.NOVO) {
				this.professorDAO.salvar(this.professor);
			} else {
				this.professorDAO.editar(this.professor);
			}

			this.listar();
			salvou = true;
			Messages.addGlobalInfo(Funcoes.getMessage("registro.salvo"));
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn(Funcoes.getMessage("registro.unico"));
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		} finally {
			this.professor.getUsuario().setSenha(
					Criptografia.decifrar(this.professor.getUsuario()
							.getSenha()));
			RequestContext.getCurrentInstance().addCallbackParam("salvou",
					salvou);
		}
	}

	public void excluir(ActionEvent event) {
		boolean excluiu = false;
		try {
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.professor = this.professorDAO.buscarPorCodigo(codigo);
			this.professorDAO.excluir(this.professor);

			this.listar();
			excluiu = true;
			Messages.addGlobalInfo(Funcoes.getMessage("registro.removido"));
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn(Funcoes.getMessage("registro.dependente"));
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		} finally {
			RequestContext.getCurrentInstance().addCallbackParam("excluiu",
					excluiu);
		}
	}

	public void editar(ActionEvent event) {
		try {
			this.acao = Acao.EDITAR;

			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.professor = this.professorDAO.buscarPorCodigo(codigo);

			this.professor.getUsuario().setSenha(
					Criptografia.decifrar(this.professor.getUsuario()
							.getSenha()));
			this.professor.getUsuario().setConfirmacaoSenha(
					this.professor.getUsuario().getSenha());

			this.fatecs = this.fatecDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
}
