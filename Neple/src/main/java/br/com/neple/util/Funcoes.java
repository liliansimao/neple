package br.com.neple.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class Funcoes {
	public static String getIP() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String ip = request.getRemoteAddr();
		return ip;
	}
	
	public static String getMessage(String chave){
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = context.getViewRoot();
		Locale locale = viewRoot.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(Constantes.BASE_NAME, locale);
		String valor = bundle.getString(chave);
		return valor;
	}
}
