package br.com.neple.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.neple.dao.CursoDAO;
import br.com.neple.dao.FatecDAO;
import br.com.neple.domain.Curso;
import br.com.neple.domain.Fatec;
import br.com.neple.enumeracao.Acao;
import br.com.neple.enumeracao.Periodo;
import br.com.neple.util.Mensagens;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class CursoBean extends GenericBean {
	private Curso curso;

	private List<Curso> cursos;
	private List<Fatec> fatecs;

	private CursoDAO cursoDAO;
	private FatecDAO fatecDAO;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Fatec> getFatecs() {
		return fatecs;
	}

	public void setFatecs(List<Fatec> fatecs) {
		this.fatecs = fatecs;
	}

	@PostConstruct
	public void iniciar() {
		this.cursoDAO = new CursoDAO();
		this.fatecDAO = new FatecDAO();
	}

	public void novo() {
		try {
			this.acao = Acao.NOVO;
			
			this.curso = new Curso();
			this.curso.setAtivo(Boolean.TRUE);
			this.curso.setPeriodo(Periodo.MATUTINO.getSigla());
			
			this.fatecs = fatecDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void editar(ActionEvent event) {
		try {
			this.acao = Acao.EDITAR;
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.curso = cursoDAO.buscar(codigo);
			this.fatecs = fatecDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void listar() {
		try {
			this.cursos = this.cursoDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void salvar() {
		boolean salvou = false;

		try {
			if (this.acao == Acao.NOVO) {
				this.cursoDAO.salvar(this.curso);
			} else {
				this.cursoDAO.editar(this.curso);
			}

			this.listar();
			salvou = true;
			Messages.addGlobalInfo(Mensagens.REGISTRO_SALVO);
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn(Mensagens.REGISTRO_UNICO);
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		} finally {
			RequestContext.getCurrentInstance().addCallbackParam("salvou",
					salvou);
		}
	}

	public void excluir(ActionEvent event) {
		try {
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.curso = this.cursoDAO.buscar(codigo);
			this.cursoDAO.excluir(this.curso);

			this.listar();
			Messages.addGlobalInfo(Mensagens.REGISTRO_REMOVIDO);
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn(Mensagens.REGISTRO_DEPENDENTE);
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		} 
	}
}
