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

import br.com.neple.dao.FatecDAO;
import br.com.neple.domain.Fatec;
import br.com.neple.enumeracao.Acao;
import br.com.neple.util.Funcoes;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FatecBean extends GenericBean {
	private Fatec fatec;
	private List<Fatec> fatecs;

	private FatecDAO fatecDAO;

	public Fatec getFatec() {
		return fatec;
	}

	public void setFatec(Fatec fatec) {
		this.fatec = fatec;
	}

	public List<Fatec> getFatecs() {
		return fatecs;
	}
	
	public void setFatecs(List<Fatec> fatecs) {
		this.fatecs = fatecs;
	}

	@PostConstruct
	public void iniciar() {
		this.fatecDAO = new FatecDAO();
	}

	public void novo(ActionEvent event) {
		this.acao = Acao.NOVO;
		this.fatec = new Fatec();
	}

	public void editar(ActionEvent event) {
		try {
			this.acao = Acao.EDITAR;
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.fatec = this.fatecDAO.buscarPorCodigo(codigo);
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void listar() {
		try {
			this.fatecs = this.fatecDAO.listar();
		} catch (RuntimeException runtimeException) {
			Messages.addGlobalError(ExceptionUtils
					.getRootCauseMessage(runtimeException));
		}
	}

	public void salvar() {
		boolean salvou = false;

		try {
			if (this.acao == Acao.NOVO) {
				this.fatecDAO.salvar(this.fatec);
			} else {
				this.fatecDAO.editar(this.fatec);
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
			RequestContext.getCurrentInstance().addCallbackParam("salvou",
					salvou);
		}
	}

	public void excluir(ActionEvent event) {
		boolean excluiu = Boolean.FALSE;
		try {
			Long codigo = (Long) event.getComponent().getAttributes()
					.get("codigo");
			this.fatec = this.fatecDAO.buscarPorCodigo(codigo);
			this.fatecDAO.excluir(this.fatec);

			this.listar();
			excluiu = Boolean.TRUE;
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
}
