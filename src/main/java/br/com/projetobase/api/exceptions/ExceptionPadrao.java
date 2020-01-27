package br.com.projetobase.api.exceptions;

public class ExceptionPadrao extends Exception{

	private static final long serialVersionUID = 1L;

	public ExceptionPadrao(String mensagem) {
		super(mensagem);
	}
}
