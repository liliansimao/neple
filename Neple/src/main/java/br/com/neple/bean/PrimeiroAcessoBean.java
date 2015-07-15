package br.com.neple.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Messages;

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
import br.com.neple.enumeracao.Idioma;
import br.com.neple.enumeracao.TipoUsuario;
import br.com.neple.util.Criptografia;
import br.com.neple.util.Funcoes;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class PrimeiroAcessoBean extends GenericBean {
	private Usuario usuario;
	private Professor professor;
	private Aluno aluno;

	private List<Fatec> fatecs;
	private List<Curso> cursos;

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

	@PostConstruct
	public void iniciar() {
		this.fatecDAO = new FatecDAO();
		this.cursoDAO = new CursoDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.alunoDAO = new AlunoDAO();
		this.professorDAO = new ProfessorDAO();
	}

	public void novo() {
		try {
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

	@Override
	public boolean ehAluno() {
		return this.usuario.getTipoUsuario() == TipoUsuario.ALUNO.getSigla();
	}

	@Override
	public boolean ehProfessor() {
		return this.usuario.getTipoUsuario() == TipoUsuario.PROFESSOR
				.getSigla();
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
		try {
			this.usuario.setSenha(Criptografia.cifrar(this.usuario.getSenha()));
			this.usuario
					.setAtivo(this.usuario.getTipoUsuario() == TipoUsuario.ALUNO
							.getSigla() ? Boolean.TRUE : Boolean.FALSE);

			if (this.usuario.getTipoUsuario() == TipoUsuario.PROFESSOR
					.getSigla()) {
				this.professor.setUsuario(this.usuario);
				this.professorDAO.salvar(this.professor);
			} else if (this.usuario.getTipoUsuario() == TipoUsuario.ALUNO
					.getSigla()) {
				this.aluno.setUsuario(this.usuario);
				this.aluno.setUsuarioAlteracao(this.usuario);
				this.alunoDAO.salvar(this.aluno);
			} else {
				this.usuarioDAO.salvar(this.usuario);
			}

			this.novo();

			Messages.addGlobalInfo(Funcoes.getMessage("registro.salvo"));
		} catch (ConstraintViolationException constraintViolationException) {
			this.usuario
					.setSenha(Criptografia.decifrar(this.usuario.getSenha()));
			Messages.addGlobalWarn(Funcoes.getMessage("registro.unico"));
		} catch (RuntimeException runtimeException) {
			this.usuario
					.setSenha(Criptografia.decifrar(this.usuario.getSenha()));
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}
}
