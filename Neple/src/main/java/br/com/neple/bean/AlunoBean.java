package br.com.neple.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.neple.dao.AlunoDAO;
import br.com.neple.dao.CursoDAO;
import br.com.neple.dao.FatecDAO;
import br.com.neple.domain.Aluno;
import br.com.neple.domain.Curso;
import br.com.neple.domain.Fatec;
import br.com.neple.domain.Usuario;
import br.com.neple.enumeracao.Acao;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.Criptografia;
import br.com.neple.util.Mensagens;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class AlunoBean extends GenericBean {
	private Aluno aluno;

	private List<Fatec> fatecs;
	private List<Curso> cursos;
	private List<Aluno> alunos;

	private FatecDAO fatecDAO;
	private CursoDAO cursoDAO;
	private AlunoDAO alunoDAO;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Fatec> getFatecs() {
		return fatecs;
	}

	public void setFatecs(List<Fatec> fatecs) {
		this.fatecs = fatecs;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	@PostConstruct
	public void iniciar() {
		this.fatecDAO = new FatecDAO();
		this.cursoDAO = new CursoDAO();
		this.alunoDAO = new AlunoDAO();
	}

	public void listar() {
		try {
			this.alunos = this.alunoDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void novo() {
		try {
			this.acao = Acao.NOVO;

			this.fatecs = this.fatecDAO.listar();
			this.cursos = new ArrayList<Curso>();

			this.aluno = new Aluno();

			this.aluno.setUsuario(new Usuario());
			this.aluno.getUsuario()
					.setTipoUsuario(TipoUsuario.ALUNO.getSigla());
			this.aluno.getUsuario().setAtivo(Boolean.TRUE);
			this.aluno.getUsuario().setDataCriacao(new Date());

			this.aluno.setDataAlteracao(new Date());
			this.aluno.setUsuarioAlteracao(this.aluno.getUsuario());
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void buscarCursos() {
		try {
			this.cursos = cursoDAO.buscarPorFatec(this.aluno.getUsuario()
					.getFatec().getCodigo());
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void salvar() {
		boolean salvou = false;

		try {
			this.aluno.getUsuario().setSenha(
					Criptografia.cifrar(this.aluno.getUsuario().getSenha()));

			if (this.acao == Acao.NOVO) {
				this.alunoDAO.salvar(this.aluno);
			} else {
				this.alunoDAO.editar(this.aluno);
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
			this.aluno.getUsuario().setSenha(
					Criptografia.decifrar(this.aluno.getUsuario().getSenha()));
			RequestContext.getCurrentInstance().addCallbackParam("salvou",
					salvou);
		}
	}

	public void excluir(ActionEvent event) {
		try {
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.aluno = this.alunoDAO.buscar(codigo);
			this.alunoDAO.excluir(this.aluno);

			this.listar();
			Messages.addGlobalInfo(Mensagens.REGISTRO_REMOVIDO);
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn(Mensagens.REGISTRO_DEPENDENTE);
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
			this.aluno = this.alunoDAO.buscar(codigo);

			this.aluno.getUsuario().setSenha(
					Criptografia.decifrar(this.aluno.getUsuario().getSenha()));
			this.aluno.getUsuario().setConfirmacaoSenha(
					this.aluno.getUsuario().getSenha());

			this.fatecs = this.fatecDAO.listar();
			this.cursos = this.cursoDAO.buscarPorFatec(this.aluno.getUsuario()
					.getFatec().getCodigo());
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
}
