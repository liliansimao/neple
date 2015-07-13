package br.com.neple.util;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

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
import br.com.neple.enumeracao.Periodo;
import br.com.neple.enumeracao.TipoUsuario;

public class HibernateUtilTest {
	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Assert.assertNotNull(sessao);
	}
	
	@Test
	@Ignore
	public void popular(){
		FatecDAO fatecDAO = new FatecDAO();
		CursoDAO cursoDAO = new CursoDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ProfessorDAO professorDAO = new ProfessorDAO();
		AlunoDAO alunoDAO = new AlunoDAO();
		
		Fatec fatec = new Fatec();
		fatec.setNome("Faculdade de Tecnologia de Ourinhos");
		fatec.setCidade("Ourinhos");
		fatec.setCodigoFatecSIGA(1L);
		fatecDAO.salvar(fatec);
		
		Curso curso = new Curso();
		curso.setAtivo(Boolean.TRUE);
		curso.setCargaHorariaEspanhol(new Short("0"));
		curso.setCargaHorariaIngles(new Short("240"));
		curso.setCodigoCursoSIGA(1L);
		curso.setCodigoTurnoSIGA(1L);
		curso.setCurso("Tecnologia em Jogos Digitais");
		curso.setFatec(fatec);
		curso.setNumeroVagas(new Short("40"));
		curso.setPeriodo(Periodo.MATUTINO.getSigla());
		cursoDAO.salvar(curso);
		
		Usuario usuarioAdmin = new Usuario();
		usuarioAdmin.setAtivo(Boolean.TRUE);
		usuarioAdmin.setDataCriacao(new Date());
		usuarioAdmin.setEmail("admin@neple.com.br");
		usuarioAdmin.setFatec(fatec);
		usuarioAdmin.setNome(TipoUsuario.ADMINISTRADOR.getDescricao());
		usuarioAdmin.setSenha(Criptografia.cifrar("admin"));
		usuarioAdmin.setTipoUsuario(TipoUsuario.ADMINISTRADOR.getSigla());	
		usuarioDAO.salvar(usuarioAdmin);
		
		Usuario usuarioProfessor = new Usuario();
		usuarioProfessor.setAtivo(Boolean.TRUE);
		usuarioProfessor.setDataCriacao(new Date());
		usuarioProfessor.setEmail("professor@neple.com.br");
		usuarioProfessor.setFatec(fatec);
		usuarioProfessor.setNome(TipoUsuario.PROFESSOR.getDescricao());
		usuarioProfessor.setSenha(Criptografia.cifrar("professor"));
		usuarioProfessor.setTipoUsuario(TipoUsuario.PROFESSOR.getSigla());	
		
		Usuario usuarioAluno = new Usuario();
		usuarioAluno.setAtivo(Boolean.TRUE);
		usuarioAluno.setDataCriacao(new Date());
		usuarioAluno.setEmail("aluno@neple.com.br");
		usuarioAluno.setFatec(fatec);
		usuarioAluno.setNome(TipoUsuario.ALUNO.getDescricao());
		usuarioAluno.setSenha(Criptografia.cifrar("aluno"));
		usuarioAluno.setTipoUsuario(TipoUsuario.ALUNO.getSigla());	
		
		Professor professor = new Professor();
		professor.setApresentacao(TipoUsuario.PROFESSOR.getDescricao());
		professor.setIdioma(Idioma.INGLES.getSigla());
		professor.setTelefoneContato("99999999999");
		professor.setUsuario(usuarioProfessor);
		professorDAO.salvar(professor);
		
		Aluno aluno = new Aluno();
		aluno.setCpf("99999999999");
		aluno.setCurso(curso);
		aluno.setDataAlteracao(new Date());
		aluno.setRa("999999");
		aluno.setUsuario(usuarioAluno);
		aluno.setUsuarioAlteracao(usuarioAluno);
		alunoDAO.salvar(aluno);
	}
}
