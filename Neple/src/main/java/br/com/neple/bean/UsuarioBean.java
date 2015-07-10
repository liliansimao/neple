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
import br.com.neple.dao.ProfessorDAO;
import br.com.neple.dao.UsuarioDAO;
import br.com.neple.domain.Aluno;
import br.com.neple.domain.Curso;
import br.com.neple.domain.Fatec;
import br.com.neple.domain.Professor;
import br.com.neple.domain.Usuario;
import br.com.neple.enumeracao.Acao;
import br.com.neple.enumeracao.Idioma;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.Criptografia;
import br.com.neple.util.Mensagens;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class UsuarioBean extends GenericBean {
	private Usuario usuario;
	private Aluno aluno;
	private Professor professor;

	private List<Fatec> fatecs;
	private List<Curso> cursos;
	private List<Usuario> usuarios;

	private FatecDAO fatecDAO;
	private CursoDAO cursoDAO;
	private UsuarioDAO usuarioDAO;
	private AlunoDAO alunoDAO;
	private ProfessorDAO professorDAO;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

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

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@PostConstruct
	public void iniciar() {
		this.fatecDAO = new FatecDAO();
		this.cursoDAO = new CursoDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.alunoDAO = new AlunoDAO();
		this.professorDAO = new ProfessorDAO();
	}

	public boolean ehUsuario() {
		if (this.usuario == null) {
			return Boolean.FALSE;
		}
		return this.usuario.getTipoUsuario() != TipoUsuario.ALUNO.getSigla()
				&& this.usuario.getTipoUsuario() != TipoUsuario.PROFESSOR
						.getSigla();
	}

	public boolean ehAluno() {
		if (this.usuario == null) {
			return Boolean.FALSE;
		}
		return this.usuario.getTipoUsuario() == TipoUsuario.ALUNO.getSigla();
	}

	public boolean ehProfessor() {
		if (this.usuario == null) {
			return Boolean.FALSE;
		}
		return this.usuario.getTipoUsuario() == TipoUsuario.PROFESSOR
				.getSigla();
	}

	public void listar() {
		try {
			this.usuarios = this.usuarioDAO.listar();
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

			this.usuario = new Usuario();
			this.usuario.setTipoUsuario(TipoUsuario.ALUNO.getSigla());
			this.usuario.setAtivo(Boolean.TRUE);
			this.usuario.setDataCriacao(new Date());

			this.professor = new Professor();
			this.professor.setIdioma(Idioma.INGLES.getSigla());

			this.aluno = new Aluno();
			this.aluno.setDataAlteracao(new Date());
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void buscarCursos() {
		try {
			if (this.usuario.getTipoUsuario() == TipoUsuario.ALUNO.getSigla()) {
				this.cursos = cursoDAO.buscarPorFatec(this.usuario.getFatec()
						.getCodigo());
			}
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void salvar() {
		boolean salvou = false;

		try {
			this.usuario.setSenha(Criptografia.cifrar(this.usuario.getSenha()));

			if (this.usuario.getTipoUsuario() == TipoUsuario.ALUNO.getSigla()) {
				this.aluno.setUsuario(this.usuario);
				this.aluno.setUsuarioAlteracao(this.usuario);
				if (this.acao == Acao.NOVO) {
					this.alunoDAO.salvar(this.aluno);
				} else {
					this.alunoDAO.editar(this.aluno);
				}
			} else if (this.usuario.getTipoUsuario() == TipoUsuario.PROFESSOR
					.getSigla()) {
				this.professor.setUsuario(this.usuario);
				if (this.acao == Acao.NOVO) {
					this.professorDAO.salvar(this.professor);
				} else {
					this.professorDAO.editar(this.professor);
				}
			} else {
				if (this.acao == Acao.NOVO) {
					this.usuarioDAO.salvar(this.usuario);
				} else {
					this.usuarioDAO.editar(this.usuario);
				}
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
			this.usuario
					.setSenha(Criptografia.decifrar(this.usuario.getSenha()));
			RequestContext.getCurrentInstance().addCallbackParam("salvou",
					salvou);
		}
	}

	public void excluir(ActionEvent event) {
		boolean excluiu = false;

		try {
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.usuario = this.usuarioDAO.buscar(codigo);
			this.usuarioDAO.excluir(this.usuario);

			this.listar();
			excluiu = true;
			Messages.addGlobalInfo(Mensagens.REGISTRO_REMOVIDO);
		} catch (ConstraintViolationException constraintViolationException) {
			Messages.addGlobalWarn(Mensagens.REGISTRO_DEPENDENTE);
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
			
			this.usuario = this.usuarioDAO.buscar(codigo);
			this.aluno = this.alunoDAO.buscar(codigo);
			this.professor = this.professorDAO.buscar(codigo);

			this.usuario
					.setSenha(Criptografia.decifrar(this.usuario.getSenha()));
			this.usuario.setConfirmacaoSenha(this.usuario.getSenha());

			if(this.aluno == null){
				this.aluno = new Aluno();
			}
			
			if(this.professor == null){
				this.professor = new Professor();
			}

			this.fatecs = this.fatecDAO.listar();
			this.cursos = this.cursoDAO.buscarPorFatec(this.usuario.getFatec()
					.getCodigo());
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
}
